package main.scala.persist

import main.scala.model.Spiel
import com.lambdaworks.jacks.JacksMapper
import scala.io.Source
import java.io.FileReader
import java.io.ObjectOutputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.FileInputStream
import java.io.File
import java.io.BufferedInputStream

class FilePersistController extends PersistController {

  val filename = "game.aos"
  
  def save(spiel: Spiel): Boolean = {
    val oos = new ObjectOutputStream(new FileOutputStream(filename))
    oos.writeObject(spiel)
    oos.close

    true
  }

  def load: Spiel = {
    val ois = new ObjectInputStream(new FileInputStream("game.aos"))
    val spiel: Spiel = ois.readObject().asInstanceOf[Spiel]
    ois.close()
    spiel
  }

  def delete(spiel: Spiel): Boolean = {
    new File(filename).delete
  }

  def fileExist: Boolean = {
    new File(filename).exists
  }

}
