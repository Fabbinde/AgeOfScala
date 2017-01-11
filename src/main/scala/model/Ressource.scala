package main.scala.model

import main.scala.util.RessourcenEnum
import scala.math.BigDecimal


class Ressource(typ: RessourcenEnum.Value, anzahl: BigDecimal) extends Serializable {
  val _name = typ.toString()
  def getTyp = typ
  def getAnzahl = anzahl.setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
}
