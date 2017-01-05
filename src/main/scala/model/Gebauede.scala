package main.scala.model

import main.scala.util.GebauedeEnum
import java.awt.image.BufferedImage
import main.scala.util.KategorieScoreEnum
import main.scala.util.RessourcenContainer

abstract class Gebauede(val _name: String, val information: String, val benoetigtGebauede: Map[Gebauede, Int], val name: GebauedeEnum.Value, val bild: BufferedImage, val benoetigteBauRessourcen: RessourcenContainer, val kategorie: KategorieScoreEnum.Value) extends Serializable {
}
