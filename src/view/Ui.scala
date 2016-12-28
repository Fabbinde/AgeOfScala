package view

import util.ResultEnum
import util.GebauedeEnum
import controller.GameController

trait Ui {
  def run() // Startfunktion
  def resultMatcher(result: ResultEnum.Value) // Funktion die die Results vom Controller umwandelt bzw. darstellt
  def gebauedeBauen(typ: GebauedeEnum.Value) // Funktion um Gebaeude zu bausen
  def gebauedeAbreissen(typ: GebauedeEnum.Value) // Funktion um Gebaeude abzureissen
}
