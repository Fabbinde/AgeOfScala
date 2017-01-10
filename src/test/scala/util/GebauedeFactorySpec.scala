package test.scala.util

import main.scala.util.GebauedeFactory
import main.scala.util.ConfigLoader
import main.scala.util.GebauedeEnum
import main.scala.model.Gebauede

import org.specs2._

class GebauedeFactorySpec extends mutable.Specification {
  isolated

  val gebauede: GebauedeFactory = ConfigLoader.ladeGebauede
  "The building factory " should {
    "load all 9 buildings by the Config Loader" in {
      gebauede.getAlle.size must_== 9
    }
    "have a stock building" in {
      gebauede.getAnzahlGebauede(GebauedeEnum.KleinesLager) must_== 1
    }
  }

  val gebauede_2: GebauedeFactory = new GebauedeFactory
  val gebauede_3: GebauedeFactory = gebauede_2.gebauedeHinzufuegen(ConfigLoader.erstelleDefaultGebauedeMitInfo(GebauedeEnum.Holzfaeller).get)
  val gebauede_4: GebauedeFactory = gebauede_3.gebauedeHinzufuegen(ConfigLoader.erstelleDefaultGebauedeMitInfo(GebauedeEnum.Steinmetz).get)
  "A new building factory " should {
    "have no stock buildings" in {
      gebauede_4.getAnzahlGebauede(GebauedeEnum.KleinesLager) must_== 0
    }
    "but have a lumberjack" in {
      gebauede_4.getAnzahlGebauede(GebauedeEnum.Holzfaeller) must_== 1
    }
    "and have a stone miner" in {
      gebauede_4.getGebauede(GebauedeEnum.Steinmetz) must beSome[Gebauede]
    }
  }

  val gebauede_5: GebauedeFactory = gebauede_4.gebauedeEntfernen(gebauede_4.getGebauede(GebauedeEnum.Holzfaeller).get)
  //println("DAVOR: size: " + gebauede_5.getAnzahlGebauede(GebauedeEnum.Holzfaeller))
  val gebauede_6: GebauedeFactory = gebauede_5.gebauedeEntfernen(gebauede_5.getGebauede(GebauedeEnum.Steinmetz).get)
  //println("DANACH size: " + gebauede_6.getAnzahlGebauede(GebauedeEnum.Holzfaeller))
  "The building factory" should {
    "now have NO lumberjack by size" in {
      gebauede_6.getAnzahlGebauede(GebauedeEnum.Holzfaeller) must_== 0
    }
    "and NO stone miner by size" in {
      gebauede_6.getAnzahlGebauede(GebauedeEnum.Steinmetz) must_== 0
    }
    "have NO lumberjack by Option" in {
      gebauede_6.getGebauede(GebauedeEnum.Steinmetz) must beNone
    }
    "and NO stone miner by Option" in {
      gebauede_6.getGebauede(GebauedeEnum.Holzfaeller) must beNone
    }
  }

}
