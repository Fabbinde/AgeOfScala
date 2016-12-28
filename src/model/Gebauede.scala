package model

import util.GebauedeEnum
import java.awt.image.BufferedImage
import util.KategorieScoreEnum
import util.RessourcenContainer

abstract class Gebauede(val _name: String, val information: String, val benoetigtGebauede: Map[Gebauede, Int], val name: GebauedeEnum.Value, val bild: BufferedImage, val benoetigteBauRessourcen: RessourcenContainer, val kategorie: KategorieScoreEnum.Value) extends Serializable {
}
