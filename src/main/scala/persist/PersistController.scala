package main.scala.persist

import main.scala.model.Spiel

trait PersistController {
  def save(spiel: Spiel): Boolean
  def load: Spiel
  def delete(spiel: Spiel): Boolean
}
