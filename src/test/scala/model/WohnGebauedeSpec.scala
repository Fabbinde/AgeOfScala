package test.scala.model

import main.scala.model.Wohngebauede
import main.scala.util.GebauedeEnum
import main.scala.util.ConfigLoader
import main.scala.util.GebauedeInformation

import org.specs2._


class WohnGebauedeSpec extends mutable.Specification {

  val house = ConfigLoader.erstelleDefaultGebauedeMitInfo(GebauedeEnum.KleinesWohnhaus).get.asInstanceOf[Wohngebauede]
  val name = "Kleines Wohnhaus"
  val info = house.information
  val ressNeeded = house.benoetigteBauRessourcen
  val amountSettler = house.getPlatzFuerSiedler

  "A new residential building " should {
    "be a building from type residential building" in {
      house must haveClass[Wohngebauede]
    }
    "have the name Kleines Wohnhaus" in {
      house._name must_== name
    }
    "have the right information" in {
      house.information must_== info
    }
    "have the right ressources" in {
      house.benoetigteBauRessourcen must_== ressNeeded
    }
    "have the right input ressources" in {
      house.getPlatzFuerSiedler must_== amountSettler
    }

  }
}
