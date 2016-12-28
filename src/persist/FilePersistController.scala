package persist

import model.Spiel
import com.lambdaworks.jacks.JacksMapper
import scala.io.Source
import java.io.FileReader
import java.io.ObjectOutputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.FileInputStream

class FilePersistController extends PersistController {

  def save(spiel: Spiel): Boolean = {
    val oos = new ObjectOutputStream(new FileOutputStream("game.aos"))
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
    true
  }

}
