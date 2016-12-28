package model

import java.awt.image.BufferedImage
import util.GebauedeEnum
import util.KategorieScoreEnum
import util.RessourcenContainer

case class LagerGebauede(override val _name : String, override val information: String, override val benoetigtGebauede: Map[Gebauede, Int], override val name: GebauedeEnum.Value, override val bild: BufferedImage, override val benoetigteBauRessourcen: RessourcenContainer, override val kategorie: KategorieScoreEnum.Value, private val kapazitaet: Int) extends Gebauede(_name, information, benoetigtGebauede, name, bild, benoetigteBauRessourcen, kategorie) {
  def getKapazitaet: Int = kapazitaet
}

