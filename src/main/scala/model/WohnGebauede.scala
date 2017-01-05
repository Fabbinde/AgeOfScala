package model

import java.awt.image.BufferedImage
import util.GebauedeEnum
import util.KategorieScoreEnum
import util.RessourcenEnum
import util.RessourcenContainer

case class Wohngebauede(override val _name : String, override val information: String, override val benoetigtGebauede: Map[Gebauede, Int], override val name: GebauedeEnum.Value, override val bild: BufferedImage, override val benoetigteBauRessourcen: RessourcenContainer, override val kategorie: KategorieScoreEnum.Value, val input: RessourcenContainer, private val platzFuerSiedler : Int) extends Gebauede(_name, information, benoetigtGebauede, name, bild, benoetigteBauRessourcen, kategorie) {
  def getPlatzFuerSiedler: Int = platzFuerSiedler
}
