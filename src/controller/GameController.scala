package controller

import akka.actor._
import model.Gebauede
import util.GebauedeFactory
import model.LagerGebauede
import model.ProduzierendesGebauede
import model.Ressource
import model.Spiel
import model.Wohngebauede
import persist.MySqlPersistController
import persist.PersistController
import util.ConfigLoader
import util.GebauedeEnum
import util.RessourcenEnum
import util.ResultEnum
import scala.concurrent.duration._
import persist.FilePersistController
import util.KategorieScoreEnum
import org.joda.time.Period
import util.RessourcenContainer

// Also das Spiel wird immer neu erstellt und dem Controller zugewiesen. Das ist das einzige Attribut das var haben darf 
// (ein Objekt muss man immer aendern, sonst muesste man das Spiel immer wieder neu kompilieren)

class GameController(spielName: String, private var spiel: Spiel, private val alleVerfuegbarenGebauede: GebauedeFactory) {

  private val persistController: PersistController = new FilePersistController

  // Alle wichtigen Objekte laden um den Scheduler auszufuehren
  private val actorSystem = ActorSystem()
  private val scheduler = actorSystem.scheduler
  // Hier wird die Funktion gesetzt welche ausgefuehrt werden soll 
  private val taskAktuallisieren = new Runnable { def run() { aktuallisiereSpielRessourcen } }
  // Um scheduler.schedulel auszufuehren benoetigt man den executor
  private implicit val executor = actorSystem.dispatcher

  // Erst 5 Sekunden warten bis alles geladen wurde, sonst sind die Objekte noch nicht erstellt. Dann jede 10 Sekunden die Ressourcen aktuallisieren
  private val cancellable = scheduler.schedule(
    initialDelay = 5 seconds,
    interval = 1 seconds,
    runnable = taskAktuallisieren)

  def spielStarten(startRessourcen: RessourcenContainer) {

    // Spiel soll immutable sein, daher muss das Objekt immer neu gesetzt werden
    spiel = new Spiel(spielName, startRessourcen, new GebauedeFactory)
    spiel = spiel.gebauedeHinzufuegen(ConfigLoader.erstelleDefaultGebauedeMitInfo(GebauedeEnum.KleinesLager).get)

  }

  def spielBeenden {

  }

  def spielSpeichern: Boolean = {
    persistController.save(spiel)
  }

  def spielLaden: Boolean = {
    val spielLoaded = persistController.load
    if (spielLoaded.isInstanceOf[Spiel]) spiel = spielLoaded; return true
    false
  }

  def gebauedeBauen(gebauede: GebauedeEnum.Value): ResultEnum.Value = {

    kannGebautWerden(gebauede) match {
      case ResultEnum.zuWenigRessourcen => return ResultEnum.zuWenigRessourcen
      case ResultEnum.gebauedeFehlt     => return ResultEnum.gebauedeFehlt
      case ResultEnum.ok => {
        val default = ConfigLoader.erstelleDefaultGebauedeMitInfo(gebauede).get
        spiel = spiel.gebauedeHinzufuegen(default)
        default.benoetigteBauRessourcen.getAlleRessourcen.foreach { r =>
          spiel = spiel.ressourceAbziehen(r._1, r._2.getAnzahl)
        }
        default match {
          case p: ProduzierendesGebauede =>
          case w: Wohngebauede           => spiel = spiel.ressourceHinzufuegen(RessourcenEnum.Siedler, w.getPlatzFuerSiedler)
          case l: LagerGebauede          =>
        }
        return ResultEnum.gebauedeErstellt
      }
      case _ => return ResultEnum.fehler
    }

  }

  def gebauedeAbreissen(gebauede: GebauedeEnum.Value): ResultEnum.Value = {
    val anzahlGebauede = spiel.getAlleErrichteteGebauede.getAlle.size
    spiel = spiel.gebauedeEntfernen(gebauede)
    if (spiel.getAlleErrichteteGebauede.getAlle.size == anzahlGebauede) return ResultEnum.gebauedeNichtVorhanden
    val default = ConfigLoader.erstelleDefaultGebauedeMitInfo(gebauede).get
    default.benoetigteBauRessourcen.getAlleRessourcen.foreach { r =>
      spiel = spiel.ressourceHinzufuegen(r._1, r._2.getAnzahl / 2)
    }
    // Ressourcen hinzufuegen, aber nur 50%
    ResultEnum.gebauedeEntfernt
  }

  def getAlleGebautenGebauede(): GebauedeFactory = spiel.getAlleErrichteteGebauede

  def getAlleVerfuegbarenGebauede: GebauedeFactory = alleVerfuegbarenGebauede

  def kannGebautWerden(gebauede: GebauedeEnum.Value): ResultEnum.Value = {
    val zuBauendesGebauedeOp = alleVerfuegbarenGebauede.getGebauede(gebauede)
    if (zuBauendesGebauedeOp.isDefined) {
      val zuBauendesGebauede = zuBauendesGebauedeOp.get
      zuBauendesGebauede.benoetigteBauRessourcen.getAlleRessourcen.map {
        case (typ: RessourcenEnum.Value, benoetigteRessource: Ressource) =>
          val meineRessource = spiel.ressourcen.getRessource(typ)
          if (meineRessource.getAnzahl < benoetigteRessource.getAnzahl) {
            return ResultEnum.zuWenigRessourcen
          }
      }
    }
    return ResultEnum.ok
  }

  // Aus der Config Datei die Werte fuer das Gebaeude auslesen
  def berechneGebauedeKosten(gebauede: GebauedeEnum.Value): RessourcenContainer = {
    alleVerfuegbarenGebauede.getGebauede(gebauede).get.benoetigteBauRessourcen
  }

  def getGameScore: Integer = {
    berechneAktuellePunktzahl
  }

  def getRessourcenAnzahl(ressource: RessourcenEnum.Value): Integer = {
    spiel.getRessource(ressource).getAnzahl
  }

  def getMeineRessourcen: RessourcenContainer = {
    spiel.ressourcen
  }

  def getMeineSiedler: Int = {
    spiel.ressourcen.getRessource(RessourcenEnum.Siedler).getAnzahl
  }

  def getAktuelleProduktion: RessourcenContainer = {
    // TODO Das hier sollte mit val geloest werden irgendwie...
    var prod: RessourcenContainer = new RessourcenContainer
    //spiel.getAlleErrichteteGebauede.getAlle.foreach { case x: ProduzierendesGebauede => x.output.getAlleRessourcen.foreach(f => println("HOUIO: " + f._2.getTyp + ": " + f._2.getAnzahl)) }
    spiel.getAlleErrichteteGebauede.getAlle.map {
      case p: ProduzierendesGebauede => prod = prod.addRessourcenByContainer(p.output)
      case w: Wohngebauede           =>
      case _                         =>
    }
    prod
  }

  def getAktuelleBetriebskosten: RessourcenContainer = {
    // TODO Das hier sollte mit val geloest werden irgendwie...
    var prod: RessourcenContainer = new RessourcenContainer
    spiel.getAlleErrichteteGebauede.getAlle.map {
      case p: ProduzierendesGebauede => prod = prod.addRessourcenByContainer(p.input)
      case w: Wohngebauede           => prod = prod.addRessourcenByContainer(w.input)
      case _                         =>
    }
    prod
  }

  def getRessourcenKapazitaet: Int = {
    spiel.getLagerKapazitaet
  }

  def aktuallisiereSpielRessourcen {
    // TODO Die Betriebskosten auch noch aktuallisieren, aber noch ueberlegen was tun wenn es keine Ressourcen mehr gibt -> Produktivitaet sinkt?
    spiel.getAlleErrichteteGebauede.getAlle.foreach {
      case p: ProduzierendesGebauede => p.output.getAlleRessourcen.foreach(r => spiel = spiel.ressourceHinzufuegen(r._2.getTyp, if ((r._2.getAnzahl / 60) < 1 && r._2.getAnzahl != 0) 1 else (r._2.getAnzahl / 60)))
      case w: Wohngebauede           => w.input.getAlleRessourcen.foreach(r => spiel = spiel.ressourceAbziehen(r._2.getTyp, if ((r._2.getAnzahl / 60) < 1 && r._2.getAnzahl != 0) 1 else (r._2.getAnzahl / 60)))
      case _                         =>
    }
  }

  def stoppeAsyncRessourcenActor() = cancellable.cancel()

  private def berechneAktuellePunktzahl(): Int = {
    val a: Int = spiel.getAlleErrichteteGebauede.getAlle.count { g => g.kategorie == KategorieScoreEnum.A }
    val b: Int = spiel.getAlleErrichteteGebauede.getAlle.count { g => g.kategorie == KategorieScoreEnum.B }
    val c: Int = spiel.getAlleErrichteteGebauede.getAlle.count { g => g.kategorie == KategorieScoreEnum.C }

    a * 100 + (150 * b) + (200 * c)
  }

  def aktuelleSpielzeitAlsPeriod: Period = {
    spiel.aktuelleSpielZeit
  }

  def aktuelleSpielzeitAlsString: String = {
    val p: Period = spiel.aktuelleSpielZeit
    "Tage: " + p.getDays + " | Stunden: " + p.getHours + " | Minuten: " + p.getMinutes + " | Sekunden: " + p.getSeconds
  }

}
