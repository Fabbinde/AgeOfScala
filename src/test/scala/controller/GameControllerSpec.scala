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

  val startSettler = 20
  val startWood = 200
  val startGold = 201
  val startStone = 202
  val startFood = 203
  
  val gameName = "TestGame"
  
  val smallStock = ConfigLoader.erstelleDefaultGebauedeMitInfo(GebauedeEnum.KleinesLager).get

  val gebauede: GebauedeFactory = ConfigLoader.ladeGebauede

  val controller: GameController = new GameController(gameName, null, gebauede)

  val cont: RessourcenContainer = new RessourcenContainer
  val res = cont.addRessource(startWood, RessourcenEnum.Holz).
    addRessource(startSettler, RessourcenEnum.Siedler).
    addRessource(startGold, RessourcenEnum.Gold).
    addRessource(startStone, RessourcenEnum.Stein).
    addRessource(startFood, RessourcenEnum.Nahrung)

  controller.spielStarten(res)

  "The game " should {
    "have the name " + gameName  in {
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

  /*"The 'Hello world' string" should {
    "contain 11 characters" in {
      "Hello world" must haveSize(11)
    }
    "start with 'Hello'" in {
      "Hello world" must startWith("Hello")
    }
    "end with 'world'" in {
      "Hello world" must endWith("world")
    }
 */
}