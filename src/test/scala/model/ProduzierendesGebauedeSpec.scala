package test.scala.model

import main.scala.model.ProduzierendesGebauede
import main.scala.util.GebauedeEnum
import main.scala.util.ConfigLoader
import main.scala.util.GebauedeInformation

import org.specs2._

class ProduzierendesGebauedeSpec extends mutable.Specification {

  val prod = ConfigLoader.erstelleDefaultGebauedeMitInfo(GebauedeEnum.Holzfaeller).get.asInstanceOf[ProduzierendesGebauede]
  val name = "Holzfäller"
  val info = prod.information
  val ressNeeded = prod.benoetigteBauRessourcen
  val output = prod.output
  val input = prod.input

  "A new production building " should {
    "be a building from type production building" in {
      prod must haveClass[ProduzierendesGebauede]
    }
    "have the name Holzfaeller" in {
      prod._name must_== name
    }
    "have the right information" in {
      prod.information must_== info
    }
    "have the right ressources" in {
      prod.benoetigteBauRessourcen must_== ressNeeded
    }
    "have the right input ressources" in {
      prod.input must_== input
    }

  }
}
