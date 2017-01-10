package main.scala.persist

import main.scala.model.Spiel

class MySqlPersistController extends PersistController {

  def save(spiel: Spiel): Boolean = {
    true
  }

  def load: Option[Spiel] = {
    None // TODO Dummy, mit Spiel dann ersetzen
  }

  def delete(spiel: Spiel): Boolean = {
    true
  }

}
