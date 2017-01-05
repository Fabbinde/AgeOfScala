package main.scala.model

import java.awt.image.BufferedImage
import main.scala.util.GebauedeEnum
import main.scala.util.KategorieScoreEnum
import main.scala.util.RessourcenContainer

case class ProduzierendesGebauede(override val _name: String, override val information: String, override val benoetigtGebauede: Map[Gebauede, Int], override val name: GebauedeEnum.Value, override val bild: BufferedImage, override val benoetigteBauRessourcen: RessourcenContainer, override val kategorie: KategorieScoreEnum.Value, val input: RessourcenContainer, val output: RessourcenContainer) extends Gebauede(_name, information, benoetigtGebauede, name, bild, benoetigteBauRessourcen, kategorie) {
    
}

