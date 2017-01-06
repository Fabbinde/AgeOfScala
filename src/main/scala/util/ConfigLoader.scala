package main.scala.util

import java.io.File

import scala.Left
import scala.Right
import scala.collection.JavaConversions.mapAsScalaMap

import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigObject

import main.scala.model.Gebauede
import main.scala.model.LagerGebauede
import main.scala.model.ProduzierendesGebauede
import main.scala.model.Wohngebauede

import scala.collection.mutable.{ Map => MutableMap }

object ConfigLoader {
  
  val filepath = "resources/game.conf"
  
  def ladeGebauede: GebauedeFactory = {
    val file = ConfigFactory.parseFile(new File(filepath))
    val conf = ConfigFactory.load(file).getConfig("aos.Gebauede")
    var factory: GebauedeFactory = new GebauedeFactory

    conf.root.map {
      case (name: String, configObject: ConfigObject) =>
        val config = configObject.toConfig
        val _name = config.getString("Name")
        val typ = config.getString("Typ")
        val info = config.getString("Information")

        val kategorie = KategorieScoreEnum.withName(config.getString("Kategorie"))
        val gebauedeE = GebauedeEnum.withName(name)

        val kostetContainer = ladeGebauedeContainer(GebauedeInformation.Kostet, GebauedeEnum.withName(name))
        val produziertContainer = ladeGebauedeContainer(GebauedeInformation.Produziert, GebauedeEnum.withName(name))
        val betriebskostenContainer = ladeGebauedeContainer(GebauedeInformation.Betriebskosten, GebauedeEnum.withName(name))
        val benoetigtGebauede: Map[Gebauede, Int] = ladeBenoetigteGebauede(gebauedeE)

        
        typ match {
          case "Produzierendes" => factory = factory.gebauedeHinzufuegen(new ProduzierendesGebauede(_name, info, benoetigtGebauede, gebauedeE, null, kostetContainer, kategorie, betriebskostenContainer, produziertContainer))
          case "Lager"          => factory = factory.gebauedeHinzufuegen(new LagerGebauede(_name, info, benoetigtGebauede, gebauedeE, null, kostetContainer, kategorie, config.getString("Kapazitaet").toInt))
          case "Wohnhaus"       => factory = factory.gebauedeHinzufuegen(new Wohngebauede(_name, info, benoetigtGebauede, gebauedeE, null, kostetContainer, kategorie, betriebskostenContainer, config.getString("PlatzFuerSieder").toInt))
          case _ =>
        }

    }
    factory

  }

  def erstelleDefaultGebauedeMitInfo(name: GebauedeEnum.Value): Option[Gebauede] = {
    val file = ConfigFactory.parseFile(new File(filepath))
    val config = ConfigFactory.load(file).getConfig("aos.Gebauede." + name.toString())
    val _name = config.getString("Name")
    val typ = config.getString("Typ")
    val info = config.getString("Information")
    val kategorie = KategorieScoreEnum.withName(config.getString("Kategorie"))
    val gebauedeE = GebauedeEnum.withName(name.toString())

    val kostetContainer = ladeGebauedeContainer(GebauedeInformation.Kostet, GebauedeEnum.withName(name.toString()))
    val produziertContainer = ladeGebauedeContainer(GebauedeInformation.Produziert, GebauedeEnum.withName(name.toString()))
    val betriebskostenContainer = ladeGebauedeContainer(GebauedeInformation.Betriebskosten, GebauedeEnum.withName(name.toString()))
    val benoetigtGebauede = ladeBenoetigteGebauede(gebauedeE)

    typ match {
      case "Produzierendes" => Some(new ProduzierendesGebauede(_name, info, benoetigtGebauede, gebauedeE, null, kostetContainer, kategorie, betriebskostenContainer, produziertContainer))
      case "Lager"          => Some(new LagerGebauede(_name, info, benoetigtGebauede, gebauedeE, null, kostetContainer, kategorie, config.getString("Kapazitaet").toInt))
      case "Wohnhaus"       => Some(new Wohngebauede(_name, info, benoetigtGebauede, gebauedeE, null, kostetContainer, kategorie, betriebskostenContainer, config.getString("PlatzFuerSieder").toInt))
      case _                => None
    }
  }

  def ladeBenoetigteGebauede(gebauedeTyp: GebauedeEnum.Value): Map[Gebauede, Int] = {
    val file = ConfigFactory.parseFile(new File(filepath))
    val benoetigteGebauede = ConfigFactory.load(file).getObject("aos.Gebauede." + gebauedeTyp + ".BenoetigtGebauede")
    //println(benoetigteGebauede.values())
    val map = MutableMap[Gebauede, Int]()

    benoetigteGebauede.iterator map (t =>
      erstelleDefaultGebauedeMitInfo(GebauedeEnum.withName(t._1)).get -> t._2.render().toInt) toMap

  }

  def ladeGebauedeInfo(info: GebauedeInformation.Value, gebauedeTyp: GebauedeEnum.Value, ressourcenTyp: Option[RessourcenEnum.Value] = None): Either[Int, String] = {
    // Either heist das es entweder String oder Int sein kann, da in der Config entweder Int oder String drinn steht
    val file = ConfigFactory.parseFile(new File(filepath))
    val benoetigteRessourcen = ConfigFactory.load(file).getObject("aos.Gebauede." + gebauedeTyp + "." + info)
    if (ressourcenTyp.isDefined) {
      if (benoetigteRessourcen.containsKey(ressourcenTyp.get.toString())) {
        Left(benoetigteRessourcen.withOnlyKey(ressourcenTyp.get.toString()).values().iterator().next().unwrapped().asInstanceOf[Int])
      } else Left(0) // Left fuer Integer
    } else {
      Right(benoetigteRessourcen.values().iterator().next().unwrapped().asInstanceOf[String]) //Right bei String
    }
  }

  def ladeGebauedeContainer(info: GebauedeInformation.Value, gebauede: GebauedeEnum.Value): RessourcenContainer = {
    val cont: RessourcenContainer = new RessourcenContainer

    val holz = cont.addRessource(ladeGebauedeInfo(info, gebauede, Some(RessourcenEnum.Holz)).left.get, RessourcenEnum.Holz)
    val stein = holz.addRessource(ladeGebauedeInfo(info, gebauede, Some(RessourcenEnum.Stein)).left.get, RessourcenEnum.Stein)
    val gold = stein.addRessource(ladeGebauedeInfo(info, gebauede, Some(RessourcenEnum.Gold)).left.get, RessourcenEnum.Gold)
    val nahrung = gold.addRessource(ladeGebauedeInfo(info, gebauede, Some(RessourcenEnum.Nahrung)).left.get, RessourcenEnum.Nahrung)
    nahrung.addRessource(ladeGebauedeInfo(info, gebauede, Some(RessourcenEnum.Siedler)).left.get, RessourcenEnum.Siedler)
  }

}
