package test.scala.persist

import main.scala.persist.PersistController
import main.scala.persist.FilePersistController
import main.scala.model.Spiel
import main.scala.util.GebauedeFactory
import main.scala.util.RessourcenContainer
import main.scala.util.RessourcenEnum

import org.specs2._
import org.specs2.matcher.ShouldMatchers
import org.specs2.specification.BeforeAll
import org.specs2.specification.After
import org.specs2.specification.AfterAll

class FilePersistControllerSpec extends Specification with ShouldMatchers with BeforeAll {

  def is = sequential ^ s2"""

  A game is
    saved correctly                                  $save
    deleted correctly                                $delete
                                                     """
  val gameName = "TestSpiel"

  val startSettler = 20
  val startWood = 200
  val startGold = 200
  val startStone = 200
  val startFood = 200

  val cont: RessourcenContainer = new RessourcenContainer
  val ress = cont.addRessource(startWood, RessourcenEnum.Holz).
    addRessource(startSettler, RessourcenEnum.Siedler).
    addRessource(startGold, RessourcenEnum.Gold).
    addRessource(startStone, RessourcenEnum.Stein).
    addRessource(startFood, RessourcenEnum.Nahrung)

  val game = new Spiel(gameName, ress, new GebauedeFactory)
  val persistController = new FilePersistController

  def save = {
    persistController.fileExist must beTrue
  }
  def delete = {
    persistController.delete(game)
    persistController.fileExist must beFalse
  }

  def beforeAll = {
    persistController.delete(game)
    persistController.save(game)
  }

}