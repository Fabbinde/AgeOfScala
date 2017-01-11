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

  val list: List[String] = Source.fromFile(highscoreFile).getLines.toList.map(x => if (x.size == 4) { x.split(":::").drop(1) + "\n" } else { x + "\n" })
                                                  //> java.io.FileNotFoundException: highscore.aos (Das System kann die angegebene
                                                  //|  Datei nicht finden)
                                                  //| 	at java.io.FileInputStream.open0(Native Method)
                                                  //| 	at java.io.FileInputStream.open(Unknown Source)
                                                  //| 	at java.io.FileInputStream.<init>(Unknown Source)
                                                  //| 	at scala.io.Source$.fromFile(Source.scala:91)
                                                  //| 	at scala.io.Source$.fromFile(Source.scala:76)
                                                  //| 	at scala.io.Source$.fromFile(Source.scala:54)
                                                  //| 	at persist.test$$anonfun$main$1.apply$mcV$sp(persist.test.scala:17)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
                                                  //| ksheetSupport.scala:75)
                                                  //| 	at persist.test$.main(persist.test.scala:8)
                                                  //| 	at persist.test.main(persist.test.scala)
  val sortedList: List[String] = list ::: List(("200" + " ::: " + "Test" + " ::: " + "00:00" + "\n"))

  println(sortedList)

}