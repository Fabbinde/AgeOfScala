package test.scala.util

import main.scala.util.GebauedeFactory
import main.scala.util.ConfigLoader
import main.scala.util.GebauedeEnum
import main.scala.model.Gebauede
import main.scala.util.RessourcenContainer
import main.scala.util.RessourcenEnum

import org.specs2._

class RessourcenContainerSpec extends mutable.Specification {
  isolated

  val resCon: RessourcenContainer = new RessourcenContainer
  val resCon_2 = resCon.addRessource(200, RessourcenEnum.Holz).
    addRessource(20, RessourcenEnum.Siedler).
    addRessource(200, RessourcenEnum.Gold).
    addRessource(200, RessourcenEnum.Stein).
    addRessource(200, RessourcenEnum.Nahrung)

  "The ressourcen container" should {
    "have 200 wood" in {
      resCon_2.getRessource(RessourcenEnum.Holz).getAnzahl must_== 200
    }
    "have 200 stone" in {
      resCon_2.getRessource(RessourcenEnum.Stein).getAnzahl must_== 200
    }
    "have 200 gold" in {
      resCon_2.getRessource(RessourcenEnum.Gold).getAnzahl must_== 200
    }
    "have 200 food" in {
      resCon_2.getRessource(RessourcenEnum.Nahrung).getAnzahl must_== 200
    }
    "have 20 settler" in {
      resCon_2.getRessource(RessourcenEnum.Siedler).getAnzahl must_== 20
    }
  }

  val resCon_3 = resCon_2.minusRessource(200, RessourcenEnum.Holz).
    minusRessource(20, RessourcenEnum.Siedler).
    minusRessource(200, RessourcenEnum.Gold).
    minusRessource(200, RessourcenEnum.Stein).
    minusRessource(200, RessourcenEnum.Nahrung)

  "The ressourcen container" should {
    "now have 0 wood (minus by minusRessource)" in {
      resCon_3.getRessource(RessourcenEnum.Holz).getAnzahl must_== 0
    }
    "now have 0 stone (minus by minusRessource)" in {
      resCon_3.getRessource(RessourcenEnum.Stein).getAnzahl must_== 0
    }
    "now have 0 gold (minus by minusRessource)" in {
      resCon_3.getRessource(RessourcenEnum.Gold).getAnzahl must_== 0
    }
    "now have 0 food (minus by minusRessource)" in {
      resCon_3.getRessource(RessourcenEnum.Nahrung).getAnzahl must_== 0
    }
    "now have 0 settler (minus by minusRessource)" in {
      resCon_3.getRessource(RessourcenEnum.Siedler).getAnzahl must_== 0
    }
  }

  val resCon_4 = resCon_2.minusRessourcenByContainer(resCon_2)
  "The ressourcen container" should {
    "now have 0 wood (minus by minusRessourceByContainer)" in {
      resCon_4.getRessource(RessourcenEnum.Holz).getAnzahl must_== 0
    }
    "now have 0 stone (minus by minusRessourceByContainer)" in {
      resCon_4.getRessource(RessourcenEnum.Stein).getAnzahl must_== 0
    }
    "now have 0 gold (minus by minusRessourceByContainer)" in {
      resCon_4.getRessource(RessourcenEnum.Gold).getAnzahl must_== 0
    }
    "now have 0 food (minus by minusRessourceByContainer)" in {
      resCon_4.getRessource(RessourcenEnum.Nahrung).getAnzahl must_== 0
    }
    "now have 0 settler (minus by minusRessourceByContainer)" in {
      resCon_4.getRessource(RessourcenEnum.Siedler).getAnzahl must_== 0
    }
  }
}