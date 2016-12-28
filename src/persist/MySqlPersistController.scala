package persist

import model.Spiel

class MySqlPersistController extends PersistController {

  def save(spiel: Spiel): Boolean = {
    true
  }

  def load: Spiel = {
    null // TODO Dummy, mit Spiel dann ersetzen
  }

  def delete(spiel: Spiel): Boolean = {
    true
  }

}
