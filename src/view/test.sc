package view
import controller.GameController
import model.Gebauede
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.geometry.Orientation
import scalafx.scene.Scene
import scalafx.scene.control.ListView
import scalafx.scene.control.Menu
import scalafx.scene.control.MenuBar
import scalafx.scene.control.MenuItem
import scalafx.scene.control.Tab
import scalafx.scene.control.TabPane
import scalafx.scene.control.TableCell
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.TableView
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.StackPane
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import util.GebauedeEnum
import util.ResultEnum
import scalafx.scene.control.TableColumn
import java.awt.image.BufferedImage
import util.RessourcenContainer
import util.RessourcenEnum
import javafx.scene.text.Text
import scalafx.scene.control.TextField

import scalafx.scene.image.Image

import scalafx.scene.image.ImageView

import java.io.File
import java.io.InputStream

import java.nio.file.{ Paths, Files }

object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  getClass.getResource("Markt.png")               //> res0: java.net.URL = null
  getClass.getResource("/Markt.png")              //> res1: java.net.URL = file:/C:/Users/Fabian/Desktop/TEMP_AoS/AgeOfScala/bin/
                                                  //| Markt.png

  val file = new File("resources/Markt.png")      //> file  : java.io.File = resources\Markt.png
  file.exists()                                   //> res2: Boolean = false
  Paths.get("resources/Markt.png")                //> res3: java.nio.file.Path = resources\Markt.png
  file.getPath                                    //> res4: String = resources\Markt.png
  file.toString()                                 //> res5: String = resources\Markt.png
  //val stream: InputStream = getClass.getResourceAsStream(file.getPath)
  //val img = new Image("/Users/Fabian/Desktop/TEMP AoS/AgeOfScala/resources/Holzfaeller.png")

  Files.exists(Paths.get("Markt.png"))            //> res6: Boolean = false
  // val graphic = new ImageView { file }

  //val iv = new ImageView(getClass().getResource("Markt.png").toExternalForm());

  val path = getClass.getResource("/")            //> path  : java.net.URL = file:/C:/Users/Fabian/Desktop/TEMP_AoS/AgeOfScala/bi
                                                  //| n/
  val folder = new File(path.getPath)             //> folder  : java.io.File = C:\Users\Fabian\Desktop\TEMP_AoS\AgeOfScala\bin
  if (folder.exists && folder.isDirectory)
    folder.listFiles
      .toList
      .foreach(file => println(file.getName))     //> Bergbau.png
                                                  //| Bibliothek.png
                                                  //| controller
                                                  //| Dorfzentrum.png
                                                  //| Feld.png
                                                  //| game.conf
                                                  //| Holzfaeller.png
                                                  //| main
                                                  //| Markt.png
                                                  //| model
                                                  //| MÃ¼hle.png
                                                  //| persist
                                                  //| util
                                                  //| view
                                                  //| Wohnhaus.png

}