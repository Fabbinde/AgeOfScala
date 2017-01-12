package main.scala.run

import main.scala.controller.GameController
import main.scala.util.GebauedeFactory
import main.scala.util.ConfigLoader
import main.scala.view.Ui
import main.scala.view.GuiFx
import main.scala.view.Tui
import main.scala.util.RessourcenContainer
import main.scala.util.RessourcenEnum

object Run extends App {

  val gebauede: GebauedeFactory = ConfigLoader.ladeGebauede

  val cont: RessourcenContainer = new RessourcenContainer
  val res = cont.addRessource(200, RessourcenEnum.Holz).
    addRessource(20, RessourcenEnum.Siedler).
    addRessource(200, RessourcenEnum.Gold).
    addRessource(200, RessourcenEnum.Stein).
    addRessource(200, RessourcenEnum.Nahrung)

  val controller: GameController = new GameController(null, gebauede, res)
  controller.spielStarten(true)

  val guiFx = new GuiFx(controller) with Ui
  val hello = new Thread(new Runnable {
    def run() {
      guiFx.run
    }
  })
  hello.start()

  val tui = new Tui(controller) with Ui
  tui.run

}
