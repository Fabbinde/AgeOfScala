package test.scala.model

import main.scala.model.Ressource
import main.scala.util.RessourcenEnum

import org.specs2._

class RessourceSpec extends mutable.Specification {

  val ressourceType = RessourcenEnum.Holz
  val ressourceAmount = 200

  val ressource = new Ressource(ressourceType, ressourceAmount)

  "A wood ressource" should {
    "have the name " + ressourceType in {
      ressource._name must_== ressourceType.toString()
    }
    "have the type " + RessourcenEnum.Holz in {
      ressource.getTyp must_== ressourceType
    }
    "have an amount of " + ressourceAmount in {
      ressource.getAnzahl must_== ressourceAmount
    }

  }
}
