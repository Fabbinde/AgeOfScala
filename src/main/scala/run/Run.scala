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

  val controller: GameController = new GameController("TestSpiel", null, gebauede)

  val cont: RessourcenContainer = new RessourcenContainer
  val res = cont.addRessource(200, RessourcenEnum.Holz).
    addRessource(20, RessourcenEnum.Siedler).
    addRessource(200, RessourcenEnum.Gold).
    addRessource(200, RessourcenEnum.Stein).
    addRessource(200, RessourcenEnum.Nahrung)

  val guiFx = new GuiFx(controller, res) with Ui
  val hello = new Thread(new Runnable {
    def run() {
      guiFx.main(Array())
    }
  })
  hello.start()

  val tui = new Tui(controller, res) with Ui
  tui.run

}
