package persist
import java.io.File

import scala.io.Source

import main.scala.model.Spiel
import java.io.PrintWriter
object test {

  val highscoreFile = "highscore.aos"             //> highscoreFile  : String = highscore.aos

  val tp = "1. ::: 600 ::: Dieter ::: Tage: 0 | Stunden: 0 | Minuten: 39 | Sekunden: 54"
                                                  //> tp  : String = 1. ::: 600 ::: Dieter ::: Tage: 0 | Stunden: 0 | Minuten: 39 
                                                  //| | Sekunden: 54
  val tp2 = "600 ::: Dieter ::: Tage: 0 | Stunden: 0 | Minuten: 39 | Sekunden: 54"
                                                  //> tp2  : String = 600 ::: Dieter ::: Tage: 0 | Stunden: 0 | Minuten: 39 | Seku
                                                  //| nden: 54
  tp.split(":::").tail                            //> res0: Array[String] = Array(" 600 ", " Dieter ", " Tage: 0 | Stunden: 0 | Mi
                                                  //| nuten: 39 | Sekunden: 54")
  tp2.split(":::")                                //> res1: Array[String] = Array("600 ", " Dieter ", " Tage: 0 | Stunden: 0 | Min
                                                  //| uten: 39 | Sekunden: 54")
  
  
  def ltrim(s: String) = s.replaceAll("^\\s+", "")//> ltrim: (s: String)String
  def removeLeadingSpaces(strings: Seq[String]) = strings.map(ltrim(_))
                                                  //> removeLeadingSpaces: (strings: Seq[String])Seq[String]

  val str = "   600   :::   Dieter    :::    Tage: 0 | Stunden: 1 | Minuten: 5 | Sekunden: 58"
                                                  //> str  : String = "   600   :::   Dieter    :::    Tage: 0 | Stunden: 1 | Minu
                                                  //| ten: 5 | Sekunden: 58"

	removeLeadingSpaces(Seq(str))             //> res2: Seq[String] = List(600   :::   Dieter    :::    Tage: 0 | Stunden: 1 |
                                                  //|  Minuten: 5 | Sekunden: 58)
}