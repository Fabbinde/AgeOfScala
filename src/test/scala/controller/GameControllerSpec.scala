package test.scala.controller

import main.scala.controller.GameController
import main.scala.util.GebauedeFactory
import main.scala.util.ConfigLoader
import main.scala.util.RessourcenContainer
import main.scala.util.RessourcenEnum
import main.scala.util.GebauedeEnum
import main.scala.model.LagerGebauede
import main.scala.model.Gebauede

import org.specs2._

class GameControllerSpec extends mutable.Specification {
  isolated

  val startSettler = 20
  val startWood = 200
  val startGold = 201
  val startStone = 202
  val startFood = 203

  val gameName = "TestSpiel"

  val smallStock = ConfigLoader.erstelleDefaultGebauedeMitInfo(GebauedeEnum.KleinesLager).get

  val gebauede: GebauedeFactory = ConfigLoader.ladeGebauede

  val cont: RessourcenContainer = new RessourcenContainer
  val res = cont.addRessource(startWood, RessourcenEnum.Holz).
    addRessource(startSettler, RessourcenEnum.Siedler).
    addRessource(startGold, RessourcenEnum.Gold).
    addRessource(startStone, RessourcenEnum.Stein).
    addRessource(startFood, RessourcenEnum.Nahrung)
  val controller: GameController = new GameController(null, gebauede, res)
  controller.spielStarten(false)

  "The game " should {
    "have the name " + gameName in {
      controller.spielName must_== gameName
    }
    "start with " + startSettler + " settler" in {
      controller.getMeineRessourcen.getRessource(RessourcenEnum.Siedler).getAnzahl must_== startSettler
    }
    "start with " + startWood + " wood" in {
      controller.getMeineRessourcen.getRessource(RessourcenEnum.Holz).getAnzahl must_== startWood
    }
    "start with " + startGold + " gold" in {
      controller.getMeineRessourcen.getRessource(RessourcenEnum.Gold).getAnzahl must_== startGold
    }
    "start with " + startStone + " stone" in {
      controller.getMeineRessourcen.getRessource(RessourcenEnum.Stein).getAnzahl must_== startStone
    }
    "start with " + startFood + " food" in {
      controller.getMeineRessourcen.getRessource(RessourcenEnum.Nahrung).getAnzahl must_== startFood
    }
    "start with " + gebauede.getAlle.size + " available buildings" in {
      controller.getAlleVerfuegbarenGebauede.getAlle.size must_== gebauede.getAlle.size
    }
    "start with a small stock" in {
      controller.getAlleGebautenGebauede.getGebauede(GebauedeEnum.KleinesLager) must beSome[Gebauede]
    }
    "start with max 200 ressources" in {
      controller.getRessourcenKapazitaet must_== 200
    }
    "start with a score of 200 points" in {
      controller.getGameScore must_== 200
    }
  }

  "A game without a mill" should {

    "now still have NOT a mill" in {
      controller.gebauedeBauen(GebauedeEnum.KleinesLager)
      controller.gebauedeBauen(GebauedeEnum.Muehle)
      controller.getAlleGebautenGebauede.getGebauede(GebauedeEnum.Muehle) must beNone
    }
    "but should have 2 stock buildings" in {
      controller.gebauedeBauen(GebauedeEnum.KleinesLager)
      controller.gebauedeBauen(GebauedeEnum.Muehle)
      controller.getAlleGebautenGebauede.getAnzahlGebauede(GebauedeEnum.KleinesLager) must_== 2
    }
  }
  /*controller.aktuallisiereSpielRessourcen

  val game_7 = game_6.ressourceHinzufuegen(RessourcenEnum.Holz, 200)
  val game_8 = game_7.gebauedeHinzufuegen(ConfigLoader.erstelleDefaultGebauedeMitInfo(GebauedeEnum.Muehle).get)

  "A game without a mill" should {
    "now have a mill" in {
      game_2.getAlleErrichteteGebauede.getGebauede(GebauedeEnum.Muehle) must beSome[Gebauede]
    }
  }*/
}