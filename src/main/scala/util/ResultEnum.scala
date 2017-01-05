package main.scala.util

object ResultEnum extends Enumeration {
  val ok, fehler, gebauedeFehlt, gebauedeErstellt, gebauedeEntfernt, gebauedeNichtVorhanden, zuWenigRessourcen = Value
  def getMessage(value: ResultEnum.Value): String = value match {
    case ResultEnum.ok                     => "Erledigt!"
    case ResultEnum.fehler                 => "Hat nicht geklappt!"
    case ResultEnum.gebauedeFehlt          => "Es sind nicht alle Gebäudevoraussetzungen vorhanden!"
    case ResultEnum.gebauedeEntfernt       => "Gebäude erfolgreich abgerissen"
    case ResultEnum.gebauedeErstellt       => "Gebäude wurde errichtet"
    case ResultEnum.gebauedeNichtVorhanden => "Gebäude ist nicht vorhanden"
    case ResultEnum.zuWenigRessourcen      => "Zu wenig Ressourcen"
  }
}
