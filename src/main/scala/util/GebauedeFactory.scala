package main.scala.util

import main.scala.model.Gebauede

class GebauedeFactory(private val errichteteGebauede: List[Gebauede] = List()) extends Serializable {

  def getAnzahlGebauede(typ: GebauedeEnum.Value) : Int = errichteteGebauede.count { g => g.name == typ }
  def getGebauede(typ: GebauedeEnum.Value) : Option[Gebauede] = errichteteGebauede.find { g => g.name == typ }
  def getAlle : List[Gebauede] = errichteteGebauede.sortBy(_.kategorie).sortBy(_.name)
  def gebauedeHinzufuegen(gebauede: Gebauede): GebauedeFactory = new GebauedeFactory(List(gebauede) ::: errichteteGebauede)
  def gebauedeEntfernen(gebauede: Gebauede): GebauedeFactory = new GebauedeFactory(errichteteGebauede diff List(gebauede))
  
}
