package main

import controller.GameController
import util.GebauedeFactory
import util.ConfigLoader
import view.Ui
import view.GuiFx
import view.Tui
import util.RessourcenContainer
import util.RessourcenEnum

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
