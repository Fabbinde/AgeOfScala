package main.scala.view

import main.scala.util.ResultEnum
import main.scala.util.GebauedeEnum
import main.scala.controller.GameController

trait Ui {
  def run() // Startfunktion
  def resultMatcher(result: ResultEnum.Value) // Funktion die die Results vom Controller umwandelt bzw. darstellt
  def gebauedeBauen(typ: GebauedeEnum.Value) // Funktion um Gebaeude zu bausen
  def gebauedeAbreissen(typ: GebauedeEnum.Value) // Funktion um Gebaeude abzureissen
}
