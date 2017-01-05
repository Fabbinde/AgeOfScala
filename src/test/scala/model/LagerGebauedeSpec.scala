package test.scala.model

import main.scala.model.LagerGebauede
import main.scala.util.GebauedeEnum
import main.scala.util.ConfigLoader
import main.scala.util.GebauedeInformation

import org.specs2._

class LagerGebauedeSpec extends mutable.Specification {

  val stock = ConfigLoader.erstelleDefaultGebauedeMitInfo(GebauedeEnum.KleinesLager).get.asInstanceOf[LagerGebauede]
  val name = "Kleines Lager"
  val amount = 200
  val info = stock.information
  val ressNeeded = stock.benoetigteBauRessourcen

  "A new stock building " should {
    "be a building from type stock building" in {
      stock must haveClass[LagerGebauede]
    }
    "have the name Kleines Lager" in {
      stock._name must_== name
    }
    "have " + amount + " capacity" in {
      stock.getKapazitaet must_== amount
    }
    "have the right information" in {
      stock.information must_== info
    }

    "have the right ressources" in {
      stock.benoetigteBauRessourcen must_== ressNeeded
    }

  }
}
