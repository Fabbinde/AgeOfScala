package test.scala.model

import main.scala.model.Spiel
import main.scala.model.Gebauede
import main.scala.util.RessourcenEnum
import main.scala.util.GebauedeEnum
import main.scala.util.RessourcenContainer
import main.scala.util.GebauedeFactory
import main.scala.util.ConfigLoader

import org.specs2._

class SpielSpec extends mutable.Specification { isolated

  val gameName = "TestSpiel"

  val startSettler = 20
  val startWood = 200
  val startGold = 201
  val startStone = 202
  val startFood = 203

  val cont: RessourcenContainer = new RessourcenContainer
  val ress = cont.addRessource(startWood, RessourcenEnum.Holz).
    addRessource(startSettler, RessourcenEnum.Siedler).
    addRessource(startGold, RessourcenEnum.Gold).
    addRessource(startStone, RessourcenEnum.Stein).
    addRessource(startFood, RessourcenEnum.Nahrung)

  val game = new Spiel(gameName, ress, new GebauedeFactory)
  val game_2 = game.gebauedeHinzufuegen(ConfigLoader.erstelleDefaultGebauedeMitInfo(GebauedeEnum.KleinesLager).get)

  "A new game" should {
    "have the name " + gameName in {
      game_2.name must_== gameName
    }
    "have a stock building" in {
      game_2.getAlleErrichteteGebauede.getGebauede(GebauedeEnum.KleinesLager) must beSome[Gebauede]
    }
  }

  val game_3 = game_2.ressourceHinzufuegen(RessourcenEnum.Holz, 200)

  "A game with only one stock building" should {
    "have now NOT more wood as before" in {
      game_3.getRessource(RessourcenEnum.Holz).getAnzahl must_== startWood
    }
  }

  val game_4 = game_3.gebauedeHinzufuegen(ConfigLoader.erstelleDefaultGebauedeMitInfo(GebauedeEnum.KleinesLager).get)
  val game_5 = game_4.ressourceHinzufuegen(RessourcenEnum.Holz, 200)

  "A game with two stock building" should {
    "have now 2 stock buildings" in {
      twoStocks(game_5) must_== true
    }
    "have now 200 more wood as before" in {
      game_5.getRessource(RessourcenEnum.Holz).getAnzahl must_== startWood + 200
    }
  }

  val game_6 = game_5.ressourceAbziehen(RessourcenEnum.Holz, 200)
  val game_7 = game_6.gebauedeHinzufuegen(ConfigLoader.erstelleDefaultGebauedeMitInfo(GebauedeEnum.Muehle).get)

  "A game without a mill" should {
    "now have a mill" in {
      game_7.getAlleErrichteteGebauede.getGebauede(GebauedeEnum.Muehle) must beSome[Gebauede]
    }
  }


  def twoStocks(g: Spiel): Boolean = { // Hat es zwei Lager?
    return g.getAlleErrichteteGebauede.getGebauede(GebauedeEnum.KleinesLager).get.isInstanceOf[Gebauede] &&
      g.getAlleErrichteteGebauede.getAnzahlGebauede(GebauedeEnum.KleinesLager) >= 2
  }

}
