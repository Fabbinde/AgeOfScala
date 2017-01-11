package main.scala.view

import main.scala.controller.GameController
import main.scala.model.Gebauede
import main.scala.util.GebauedeEnum
import main.scala.util.ResultEnum
import main.scala.util.RessourcenEnum
import main.scala.util.RessourcenContainer

class Tui(controller: GameController) extends Ui {

  def showIntro {
    println("     ||+ Age of Scala +||")
    println("           By F. B.")
    println("             2016")
    Thread sleep (2500)
  }

  def showMenu {
    println("'G' - Baubare Gebaeude anzeigen")
    println("'E' - Errichtete Gebaeude anzeigen")
    println("'R' - Vorhandene Ressourcen anzeigen")
    println("'A' - Produktion und Betriebskosten einsehen")
    println("'P' - Aktuellen Punktestand")
    println("'Z' - Aktuellen Spielzeit")
    println("'H' - Highscore")
    println("'B NAME' - Gebaeude mit NAME errichten")
    println("'D NAME' - Gebaeude mit NAME abreissen")
    println("'Save' - Spiel speichern")
    println("'Load' - Spiel laden")
    println("'Stop' - Spiel beenden")
    print("Eingabe: ")
  }

  def readCommand(wholeCommand: String, param: String) {
    val command = wholeCommand.split(" ")
    if (command.size > 1) {
      try {
        command(0) match {
          case "B" => gebauedeBauen(GebauedeEnum.withName(param))
          case "D" => gebauedeAbreissen(GebauedeEnum.withName(param))
          case _   => println("Falscher Parameter")
        }
        //gebauedeBauen(GebauedeEnum.withName(param))
      } catch {
        case t: java.util.NoSuchElementException => println("Parameter wurde nicht erkannt")
      }
    } else println("Kein Parameter angegeben")
  }

  def run {
    showIntro
    showMenu

    //controller.spielStarten(startRessourcen)

    for (ln <- io.Source.stdin.getLines) {
      println("---------------------")
      val PatternB = "(B )(.*)".r
      val PatternD = "(D )(.*)".r

      ln match {
        case "G" => printAlleBaubarenGebauede
        case "E" => printAlleErrichtetenGebauede
        case "R" => printMeineRessourcen
        case "A" => printProduktionUndBetriebskosten
        case PatternB(first, param) => // In First steht der erste Befehl B und in param das Gebaeude
          readCommand(ln, param)
        case PatternD(first, param) => // In First steht der erste Befehl D und in param das Gebaeude
          readCommand(ln, param)
        case "Save" =>
          if (controller.spielSpeichern) println("Spiel gespeichert!")
          else println("Fehler beim Speichern")
        case "Load" =>
          if (controller.spielLaden) println("Spiel geladen!")
          else println("Fehler beim Laden")
        case "P" => println("Aktuelle Punktzahl: " + controller.getGameScore)
        case "Z" => println("Aktuelle Spielzeit: " + controller.aktuelleSpielzeitAlsString)
        case "H" => controller.getHighscore match {
          case Some(list) => {
            println("Highscore:")
            list.foreach(f => println(f))
            println
          }
          case None => println("")
        }
        case "Stop" => {
          controller.saveHighscore
          controller.spielBeenden
          return
        }
        case "Update" => refresh // Updated die Werte, ist nur fuer Testzwecke hier ...
        //case "Stop"      => controller.stoppeAsyncRessourcenActor()
        case _        => println("Falsche Eingabe")
      }
      showMenu
    }
  }

  // Wird jetzt vom Controller jede 10 Sekunden ausgefuehrt, hier nur noch fuer Testzwecke drinn
  def refresh() = controller.aktuallisiereSpielRessourcen

  def resultMatcher(result: ResultEnum.Value) {
    result match {
      case ResultEnum.gebauedeEntfernt       => println("Gebaeude erfolgreich abgerissen")
      case ResultEnum.gebauedeErstellt       => println("Gebaeude wurde errichtet")
      case ResultEnum.gebauedeNichtVorhanden => println("Gebaeude ist nicht vorhanden")
      case ResultEnum.zuWenigRessourcen      => println("Zu wenig Ressourcen")
    }
  }

  def gebauedeBauen(typ: GebauedeEnum.Value) = resultMatcher(controller.gebauedeBauen(typ))

  def gebauedeAbreissen(typ: GebauedeEnum.Value) = resultMatcher(controller.gebauedeAbreissen(typ))

  def printAlleBaubarenGebauede {
    println("Baubare Gebaeude:")
    controller.getAlleVerfuegbarenGebauede.getAlle.foreach { g =>
      println(g.name + ": " + g.information)
      println("Benoetigte Ressourcen:")
      g.benoetigteBauRessourcen.getAlleRessourcen.foreach(f =>
        println(f._2.getTyp + ": " + f._2.getAnzahl))
      println()
    }
  }

  def printAlleErrichtetenGebauede {
    println("Errichtete Gebaeude:")
    // Durch groupBy und mapValues werden doppelte Eintraege ausgelassen
    controller.getAlleGebautenGebauede.getAlle.groupBy(_.name).mapValues(_.head).foreach(g => println(g._2.name + ": " + g._2.information + ", Anzahl: " + controller.getAlleGebautenGebauede().getAlle.count { gc => gc.name == g._2.name }))
    println()
  }

  def printMeineRessourcen {
    println("Vorhandene Ressourcen:")
    // Siedler auslassen, diese Extra da sie nicht vom Lager abhaengig sind
    controller.getMeineRessourcen.getAlleRessourcen.filter(f => f._2.getTyp != RessourcenEnum.Siedler).foreach(r => println(r._1 + ": " + r._2.getAnzahl + "/" + controller.getRessourcenKapazitaet))
    println("Siedler: " + controller.getMeineSiedler)
    println()
  }

  def printProduktionUndBetriebskosten {
    println("Produktion:")
    controller.getAktuelleProduktion.getAlleRessourcen.foreach(r => println(r._2.getTyp + ": " + r._2.getAnzahl))
    println("Betriebskosten:")
    controller.getAktuelleBetriebskosten.getAlleRessourcen.foreach(r => println(r._2.getTyp + ": " + r._2.getAnzahl))
  }

}
