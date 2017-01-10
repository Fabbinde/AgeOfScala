package main.scala.model

import org.joda.time.DateTime
import org.joda.time.Period

import main.scala.util.GebauedeEnum
import main.scala.util.RessourcenEnum
import main.scala.util.RessourcenContainer
import main.scala.util.GebauedeFactory

case class Spiel(name: String, ressourcen: RessourcenContainer, private val errichteteGebauede: GebauedeFactory = new GebauedeFactory, private val startZeit: DateTime = DateTime.now) extends Serializable {

  def starteNeuesSpiel = {

    //aktuelleZeit = Option(new Timer)
    //aktuelleZeit.get.starteTimer
    //copy(name, ressourcen, errichteteGebauede, aktuelleZeit)
  }

  def getRessource(typ: RessourcenEnum.Value) = ressourcen.getRessource(typ)

  def gebauedeHinzufuegen(gebauede: Gebauede): Spiel = new Spiel(name, ressourcen, errichteteGebauede.gebauedeHinzufuegen(gebauede), startZeit)

  def gebauedeEntfernen(gebauede: GebauedeEnum.Value): Spiel = {
    val gebObj = errichteteGebauede.getGebauede(gebauede)

    if (gebObj.isDefined) {
      return new Spiel(name, ressourcen, errichteteGebauede.gebauedeEntfernen(gebObj.get), startZeit)
    }
    this
  }

  def ressourceHinzufuegen(ressource: RessourcenEnum.Value, anzahl: Integer): Spiel = {
    val sollGroesse: Int = anzahl + ressourcen.getRessource(ressource).getAnzahl
    if (getLagerKapazitaet == ressourcen.getRessource(ressource).getAnzahl) return new Spiel(name, ressourcen, errichteteGebauede, startZeit) // Wenn Ressource == Lagerkapazitaet
    if (getLagerKapazitaet < sollGroesse) return new Spiel(name, ressourcen.addRessource(getLagerKapazitaet - ressourcen.getRessource(ressource).getAnzahl, ressource), errichteteGebauede, startZeit)
    return new Spiel(name, ressourcen.addRessource(anzahl, ressource), errichteteGebauede, startZeit)
  }

  def ressourceAbziehen(ressource: RessourcenEnum.Value, anzahl: Integer): Spiel = {
    val sollGroesse: Int = ressourcen.getRessource(ressource).getAnzahl - anzahl
    //if (sollGroesse == 0) return new Spiel(name, ressourcen, errichteteGebauede, startZeit)
    //if (sollGroesse < 0) return new Spiel(name, ressourcen.minusRessource(ressourcen.getRessource(ressource).getAnzahl, ressource), errichteteGebauede, startZeit)
    return new Spiel(name, ressourcen.minusRessource(anzahl, ressource), errichteteGebauede, startZeit)
  }

  def getAlleErrichteteGebauede: GebauedeFactory = errichteteGebauede

  def getLagerKapazitaet: Int = {
    return getAlleErrichteteGebauede.getAlle.foldLeft(0) { (sum, item) =>
      item match {
        case l: LagerGebauede => sum + l.getKapazitaet
        case _: Gebauede      => sum
      }
    }
  }

  def aktuelleSpielZeit: Period = {
    new Period(startZeit, DateTime.now)

  }

}
