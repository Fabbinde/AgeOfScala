import org.specs2._
import main.scala.model.LagerGebauede


class LagerGebauedeSpec extends mutable.Specification {
  lager: LagerGebauede = new LagerGebauede(_name, info, benoetigtGebauede, gebauedeE, null, kostetContainer, kategorie, config.getString("Kapazitaet").toInt)
  "A new stock building " should {
    "have the name "  in {
      "" must_== ""
    }


  }
}
