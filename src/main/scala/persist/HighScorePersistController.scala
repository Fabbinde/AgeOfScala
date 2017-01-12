package persist

import java.io.File

import scala.io.Source

import main.scala.model.Spiel
import java.io.PrintWriter

class HighScorePersistController {
  val highscoreFile = "highscore.aos"

  def sortByScore(s1: String, s2: String) = {
    s1.split(":::")(0).replaceAll("\\s", "").toInt > s2.split(":::")(0).replaceAll("\\s", "").toInt
  }

  def save(spiel: Spiel): Boolean = {
    val p = spiel.aktuelleSpielZeit
    val zeit = "Tage: " + p.getDays + " | Stunden: " + p.getHours + " | Minuten: " + p.getMinutes + " | Sekunden: " + p.getSeconds
    val list: List[String] = Source.fromFile(highscoreFile).getLines.toList.map(x => if (x.split(":::").size == 4) { x.split("::: ").tail.toList.mkString("::: ") + "\n" } else { x + "\n" })
    val sortedList: List[String] = list ::: List(new String(spiel.berechneAktuellePunktzahl + " ::: " + spiel.name + " ::: " + zeit + "\n"))

    import java.io._

    val writer = new BufferedWriter(new FileWriter(highscoreFile))
    sortedList.sortWith(sortByScore).zipWithIndex.map {
      case (element, index) =>
        val inc = index + 1
        s"${inc}. ::: ${element}"

    }.foreach(writer.write)
    writer.close()

    true
  }

  def load: Option[List[String]] = {
    if (fileExist) {
      return Some(Source.fromFile(highscoreFile).getLines.toList)
    }
    Some(Nil)
  }

  def delete: Boolean = new File(highscoreFile).delete

  def fileExist: Boolean = new File(highscoreFile).exists

}