package persist

import main.scala.model.Spiel
import java.io.File

class HighScorePersistController {
  val highscore = "highscore.aos"

  def save(spiel: Spiel): Boolean = {
    /*val oos = new ObjectOutputStream(new FileOutputStream(filename))
    oos.writeObject(spiel)
    oos.close
*/
    //new PrintWriter("filename") { write("file contents"); close }
    true
  }

  def load: Option[String] = {
    if (fileExist) {
      /*val ois = new ObjectInputStream(new FileInputStream("savegame.aos"))
      val spiel: Spiel = ois.readObject().asInstanceOf[Spiel]
      ois.close()
      return Some(spiel)*/
    }
    None
  }

  def delete: Boolean = new File(highscore).delete

  def fileExist: Boolean = new File(highscore).exists

}