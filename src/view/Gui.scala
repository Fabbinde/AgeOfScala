package view

import scala.concurrent.duration.DurationInt

import akka.actor.ActorSystem
import controller.GameController
import model.Gebauede
import model.Ressource
import model.ProduzierendesGebauede
import model.Wohngebauede
import model.LagerGebauede
import scalafx.Includes.eventClosureWrapperWithParam
import scalafx.Includes.jfxActionEvent2sfx
import scalafx.Includes.jfxButton2sfx
import scalafx.Includes.jfxText2sfxText
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.Platform
import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.ObjectProperty.sfxObjectProperty2jfx
import scalafx.beans.property.StringProperty
import scalafx.beans.property.StringProperty.sfxStringProperty2jfx
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.Button
import scalafx.scene.control.Button.sfxButton2jfx
import scalafx.scene.control.Menu
import scalafx.scene.control.MenuBar
import scalafx.scene.control.MenuItem
import scalafx.scene.control.Tooltip
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.FlowPane
import scalafx.scene.layout.HBox
import scalafx.scene.layout.Region
import scalafx.scene.layout.TilePane
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
import scalafx.scene.text.FontWeight
import scalafx.scene.text.FontWeight.sfxEnum2jfx
import scalafx.scene.text.Text
import scalafx.scene.text.Text.sfxText2jfx
import util.GebauedeEnum
import util.RessourcenContainer
import util.RessourcenEnum
import util.ResultEnum
import javafx.scene.input.MouseEvent
import javafx.event.EventHandler



class GuiFx(controller: GameController, startRessourcen: RessourcenContainer) extends JFXApp {

  val BUFFER = "        "

  controller.spielStarten(startRessourcen)

  val verfuegbareGebauede = ObservableBuffer[Gebauede](controller.getAlleVerfuegbarenGebauede.getAlle)
  val verfuegbareRessourcen = ObservableBuffer[Ressource](controller.getMeineRessourcen.getAsList)
  val gebauedeButtons = ObservableBuffer[Button]()
  val gebauedeLabel = ObservableBuffer[Text]()

  val gebauedeInfoListe: ObjectProperty[List[Text]] = ObjectProperty(this, "gebauedeInfoListe", List(new Text))

  val aktuelleGebaudeInfo = new StringProperty(this, "aktuelleGebauedeInfo", "")
  //val statistik = ObservableBuffer[String](List("Punkte: " + controller.getGameScore, "Aktuelle Spielzeit: " + controller.aktuelleSpielzeitAlsString, "Anzahl gesamter Gebäude: " + controller.getAlleGebautenGebauede().getAlle.size))

  gebauedeInfoListe.onChange {
    if (aktuelleGebaudeInfo.get != "") {
      gebauedeInfoBox.visible = true
    } else {
      gebauedeInfoBox.visible = false
    }
  }

  val gebauedeInputListeText = new VBox {
    children = gebauedeInfoListe.get
  }

  val gebauedeInfoBoxInput = new BorderPane {
    top = new HBox {
      children = new ImageView() {
        image = new Image(this, "/closeButton.png")
        margin = Insets(5, 0, 0, 259)
        alignmentInParent = Pos.TopLeft
        onMouseClicked = new EventHandler[MouseEvent] {
          def handle(mouseEvent: MouseEvent) {
            aktuelleGebaudeInfo() = ""
          }
        }

      }
    }
    center = gebauedeInputListeText
    bottom = new HBox {
      children = new ImageView {
        image = new Image(this, "/buttonBauen.png")
        prefHeight = 32
        prefWidth = 138
        //padding = Insets(0)
        padding = Insets(20, 0, 0, 70)

        onMouseClicked = new EventHandler[MouseEvent] {
          def handle(mouseEvent: MouseEvent) {
            gebauedeBauen(GebauedeEnum.withName(aktuelleGebaudeInfo.get))
          }

        }
      }
    }
  }

  createGebauedeButtons

  def resultMatcher(result: ResultEnum.Value) {

    // TODO WO ANDERS RAUS: LOG ODER SO
    new Alert(AlertType.Information, "Hinweis: " + ResultEnum.getMessage(result)) {
      initOwner(stage)
      title = "Hinweis"
      //headerText = "Hinweis: " + result.get
      //contentText = result.g.toString()
    }.showAndWait()
  }

  private def createRessourcenTitel = new TilePane {

    snapToPixel = false
    style = "-fx-background-image: url('/Ressourcen.jpg'); " +
      "-fx-background-position: top left; " +
      "-fx-background-repeat: no-repeat; "
    children = List(new HBox {
      children = Seq(
        new Text(controller.getMeineRessourcen.getRessource(RessourcenEnum.Holz).getAnzahl.toString()) {
          //text = ressourceHolz
          font = Font.font("Poor Richard", FontWeight.Bold, 12)
          fill = Color.White
          id = RessourcenEnum.Holz.toString() + "_Text"
          margin = Insets(5, 0, 0, 27)
          wrappingWidth = 50
        },
        new Text(controller.getMeineRessourcen.getRessource(RessourcenEnum.Nahrung).getAnzahl.toString()) {
          font = Font.font("Poor Richard", FontWeight.Bold, 12)
          fill = Color.White
          id = RessourcenEnum.Nahrung.toString() + "_Text"
          margin = Insets(5, 0, 0, 27)
          wrappingWidth = 50
        },
        new Text(controller.getMeineRessourcen.getRessource(RessourcenEnum.Gold).getAnzahl.toString()) {
          font = Font.font("Poor Richard", FontWeight.Bold, 12)
          fill = Color.White
          id = RessourcenEnum.Gold.toString() + "_Text"
          margin = Insets(5, 0, 0, 43)
          wrappingWidth = 50
        },
        new Text(controller.getMeineRessourcen.getRessource(RessourcenEnum.Stein).getAnzahl.toString()) {
          font = Font.font("Poor Richard", FontWeight.Bold, 12)
          fill = Color.White
          id = RessourcenEnum.Stein.toString() + "_Text"
          margin = Insets(5, 0, 0, 11)
          wrappingWidth = 50
        },
        new Text(controller.getMeineRessourcen.getRessource(RessourcenEnum.Siedler).getAnzahl.toString()) {
          font = Font.font("Poor Richard", FontWeight.Bold, 12)
          fill = Color.White
          id = RessourcenEnum.Siedler.toString() + "_Text"
          margin = Insets(5, 0, 0, 30)
          wrappingWidth = 50
        }, createMenus())

    })

    prefTileHeight = 40
    prefTileWidth = 1000
  }

  def createGebauedeButtons = {
    for (e <- verfuegbareGebauede) {
      gebauedeButtons.insert(0, new Button {
        val baubar = if (controller.kannGebautWerden(e.name) == ResultEnum.ok) "" else "-fx-opacity: 0.2;"
        style = "-fx-background-color: black; " + baubar
        graphic = new ImageView { image = new Image(this, "/" + e.name.toString() + ".png") }
        maxHeight = 35
        maxWidth = 35
        padding = Insets(0)
        margin = Insets(4)
        tooltip = new Tooltip(e._name) {
          font = Font.font("Poor Richard", FontWeight.Bold, 14)
        }

        id = e.name.toString + "_Button"
        onAction = (event: ActionEvent) => {
          aktuelleGebaudeInfo() = e.name.toString()
          //gebauedeBauen(e.name)
        }

      })
    }
  }

  val gebauedeInfoBox: VBox = new VBox {
    prefWidth = 282
    prefHeight = 305
    visible = false
    style = "-fx-background-image: url('/gebauedeInfoSmall.png'); " +
      "-fx-background-repeat: stretch;"
    alignmentInParent = Pos.CenterLeft
    children = gebauedeInfoBoxInput

    /*List()*/
  }

  val borderPane = new BorderPane {
    style = "-fx-background-image: url('/background.png'); " +
      "-fx-background-position: center center; " +
      "-fx-background-repeat: stretch;"
    top = createRessourcenTitel
    center = gebauedeInfoBox

    bottom = new FlowPane {
      style = "-fx-background-image: url('/hud.png'); " +
        "-fx-background-position: center center; "
      prefHeight = 174
      //for (a <- 1 to 10) {
      children = new HBox {
        prefWidth = 250
        padding = Insets(32, 0, 0, 30)
        children = new FlowPane {

          children = gebauedeButtons
        }

        alignment = Pos.TopLeft
      }
    }

  }

  stage = new PrimaryStage {
    title = "Age of Scala"
    scene = new Scene(1024, 576) {

      root = borderPane

    }

  }

  private def createBackground = new Region {
    style = "-fx-background-image: url('/background.png'); -fx-background-repeat: no-repeat;" +
      "-fx-background-position: right top; -fx-background-attachment: fixed;" +
      "-fx-font: 14px 'Poor Richard';"
  }

  def aktuallisieren = {
    Platform.runLater(new Runnable {
      def run() {

        verfuegbareRessourcen.clear()
        verfuegbareRessourcen.insertAll(0, controller.getMeineRessourcen.getAsList)
        verfuegbareRessourcen.foreach { r =>

          val id_ = r.getTyp.toString() + "_Text"
          val text: scalafx.scene.text.Text = stage.scene().lookup("#" + id_).asInstanceOf[javafx.scene.text.Text]

          if (text != null) {
            val newText = new Text(text) {
              text = controller.getMeineRessourcen.getRessource(r.getTyp).getAnzahl.toString
            }
          }
        }

        // Fuer alle Buttons, ob man dieses Gebauede bauen kann updaten
        verfuegbareGebauede.clear()
        verfuegbareGebauede.insertAll(0, controller.getAlleVerfuegbarenGebauede.getAlle.sortBy(_.kategorie).sortBy(_.name))
        verfuegbareGebauede.foreach { g =>
          val id_ = g.name.toString() + "_Button"
          val button: scalafx.scene.control.Button = stage.scene().lookup("#" + id_).asInstanceOf[javafx.scene.control.Button]

          if (button != null) {
            val newButton = new Button(button) {
              val baubar = if (controller.kannGebautWerden(g.name) == ResultEnum.ok) "" else "-fx-opacity: 0.2;"
              style = "-fx-background-color: black; " + baubar
            }
          }
        }

        if (aktuelleGebaudeInfo.get != "") {
          val gebauedeEnum: GebauedeEnum.Value = GebauedeEnum.withName(aktuelleGebaudeInfo.get)
          val gebauede = controller.getAlleVerfuegbarenGebauede.getGebauede(gebauedeEnum).get
          gebauedeInfoListe() = List(new Text() {
            alignmentInParent = Pos.CenterLeft
            text = gebauede._name + " - (" + controller.getAlleGebautenGebauede().getAnzahlGebauede(gebauedeEnum) + ")"
            margin = Insets(5, 0, 0, 37)
            font = Font.font("Poor Richard", FontWeight.Bold, 18)
            fill = Color.White
          }, new Text() {
            alignmentInParent = Pos.CenterLeft
            text = gebauede.information + "\n"
            margin = Insets(0, 0, 0, 37)
            font = Font.font("Poor Richard", FontWeight.Bold, 12)
            fill = Color.White
          }, new Text() {
            alignmentInParent = Pos.CenterLeft
            text = "Kosten:\n" + gebauede.benoetigteBauRessourcen.toString(BUFFER)
            margin = Insets(0, 0, 0, 37)
            font = Font.font("Poor Richard", FontWeight.Bold, 15)
            fill = Color.White
          }, new Text() {
            alignmentInParent = Pos.CenterLeft
            margin = Insets(0, 0, 0, 37)
            font = Font.font("Poor Richard", FontWeight.Bold, 15)
            fill = Color.White
            gebauede match {
              case p: ProduzierendesGebauede => if (!p.input.empty) text = "Betriebskosten:\n" + p.input.toString(BUFFER)
              case w: Wohngebauede           => if (!w.input.empty) text = "Betriebskosten:\n" + w.input.toString(BUFFER)
              case l: LagerGebauede          => text = "Lagerkapazität: " + l.getKapazitaet + BUFFER
              case _ => {
                text = ""
              }
            }
          }, new Text() {
            alignmentInParent = Pos.CenterLeft
            margin = Insets(0, 0, 0, 37)
            font = Font.font("Poor Richard", FontWeight.Bold, 15)
            fill = Color.White
            gebauede match {
              case p: ProduzierendesGebauede => if (!p.output.empty) text = "Produziert:\n" + p.output.toString(BUFFER)
              case w: Wohngebauede           => text = "Platz für Siedler: " + w.getPlatzFuerSiedler + BUFFER
              case l: LagerGebauede          => text = "" // (Noch) Keine weiteren Attribute
              case _                         => text = ""
            }

          })

          gebauedeInputListeText.children = gebauedeInfoListe.get

        } else {
          gebauedeInfoBox.visible = false
        }
      }
    })

  }

  private def createMenus() = new MenuBar {
    menus = List(
      new Menu("Menü") {
        style = "-fx-background-image: url('/button.jpg'); " +
          "-fx-background-repeat: no-repeat; "
        // graphic = new ImageView { image = new Image(this, "/button.png") }
        items = List(
          new MenuItem("Neues Spiel") { //onAction = {
            onAction = {
              e: ActionEvent => controller.spielStarten(startRessourcen) // TODO ResultMatcher drum rum?
            }
          },
          new MenuItem("Spiel speichern") {
            onAction = {
              e: ActionEvent => controller.spielSpeichern // TODO ResultMatcher drum rum?
            }
          },
          new MenuItem("Spiel Laden") {
            onAction = {
              e: ActionEvent => controller.spielLaden // TODO ResultMatcher drum rum?
            }
          },
          new MenuItem("Beenden") {
            onAction = {
              e: ActionEvent =>
                {
                  cancellable.cancel()
                  controller.spielBeenden
                  stage.close()
                  stopApp()
                }
            }
          })
      })
  }

  def run() {
    controller.spielStarten(startRessourcen)
  }

  def gebauedeBauen(typ: GebauedeEnum.Value) {
    resultMatcher(controller.gebauedeBauen(typ))
  }

  def gebauedeAbreissen(typ: GebauedeEnum.Value) {
    resultMatcher(controller.gebauedeAbreissen(typ))
  }

  // Starte Actor
  val actorSystem = ActorSystem()
  val scheduler = actorSystem.scheduler
  val taskAktuallisieren = new Runnable { def run() { aktuallisieren } }
  implicit val executor = actorSystem.dispatcher

  val cancellable = scheduler.schedule(
    initialDelay = 0 seconds,
    interval = 500 milliseconds,
    runnable = taskAktuallisieren)
}