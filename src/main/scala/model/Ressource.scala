package main.scala.model

import main.scala.util.RessourcenEnum

class Ressource(typ: RessourcenEnum.Value, anzahl: Integer) extends Serializable {
  val _name = typ.toString()
  def getTyp = typ
  def getAnzahl = anzahl
}
