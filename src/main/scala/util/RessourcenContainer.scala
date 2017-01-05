package main.scala.util

import main.scala.model.Ressource

case class RessourcenContainer(private val ressourcen: Map[RessourcenEnum.Value, Ressource] = Map(
                                 RessourcenEnum.Holz -> new Ressource(RessourcenEnum.Holz, 0),
                                 RessourcenEnum.Stein -> new Ressource(RessourcenEnum.Stein, 0),
                                 RessourcenEnum.Gold -> new Ressource(RessourcenEnum.Gold, 0),
                                 RessourcenEnum.Nahrung -> new Ressource(RessourcenEnum.Nahrung, 0),
                                 RessourcenEnum.Siedler -> new Ressource(RessourcenEnum.Siedler, 0))) extends Serializable {

  private def buildContainer(typ: RessourcenEnum.Value, anzahl: Integer) = copy(ressourcen + (typ -> new Ressource(typ, anzahl)))

  def getRessource(res: RessourcenEnum.Value) = ressourcen.filter { _._1 == res }.head._2

  def getAlleRessourcen = ressourcen

  def getAsList: List[Ressource] = ressourcen.values.toList

  def addRessource(anzahl: Integer, typ: RessourcenEnum.Value) = buildContainer(typ, getRessource(typ).getAnzahl + anzahl)

  def minusRessource(anzahl: Integer, typ: RessourcenEnum.Value) = buildContainer(typ, getRessource(typ).getAnzahl - anzahl)

  def addRessourcenByContainer(con: RessourcenContainer): RessourcenContainer = {
    val holz = buildContainer(RessourcenEnum.Holz, con.getRessource(RessourcenEnum.Holz).getAnzahl + getRessource(RessourcenEnum.Holz).getAnzahl)
    val stein = holz.buildContainer(RessourcenEnum.Stein, con.getRessource(RessourcenEnum.Stein).getAnzahl + getRessource(RessourcenEnum.Stein).getAnzahl)
    val gold = stein.buildContainer(RessourcenEnum.Gold, con.getRessource(RessourcenEnum.Gold).getAnzahl + getRessource(RessourcenEnum.Gold).getAnzahl)
    val nahrung = gold.buildContainer(RessourcenEnum.Nahrung, con.getRessource(RessourcenEnum.Nahrung).getAnzahl + getRessource(RessourcenEnum.Nahrung).getAnzahl)
    nahrung.buildContainer(RessourcenEnum.Siedler, con.getRessource(RessourcenEnum.Siedler).getAnzahl + getRessource(RessourcenEnum.Siedler).getAnzahl)
  }

  def minusRessourcenByContainer(con: RessourcenContainer): RessourcenContainer = {
    val holz = buildContainer(RessourcenEnum.Holz, getRessource(RessourcenEnum.Holz).getAnzahl - con.getRessource(RessourcenEnum.Holz).getAnzahl)
    val stein = holz.buildContainer(RessourcenEnum.Stein, getRessource(RessourcenEnum.Stein).getAnzahl - con.getRessource(RessourcenEnum.Stein).getAnzahl)
    val gold = stein.buildContainer(RessourcenEnum.Gold, getRessource(RessourcenEnum.Gold).getAnzahl - con.getRessource(RessourcenEnum.Gold).getAnzahl)
    val nahrung = gold.buildContainer(RessourcenEnum.Nahrung, getRessource(RessourcenEnum.Nahrung).getAnzahl - con.getRessource(RessourcenEnum.Nahrung).getAnzahl)
    nahrung.buildContainer(RessourcenEnum.Siedler, getRessource(RessourcenEnum.Siedler).getAnzahl + con.getRessource(RessourcenEnum.Siedler).getAnzahl)
  }

  def empty : Boolean = {
    
    if (ressourcen.forall(p => p._2.getAnzahl <= 0)) {
      return true
    }
    false
  }
  
  def toString(buffer: String): String = {
    val holz = if (ressourcen.get(RessourcenEnum.Holz).get.getAnzahl > 0) buffer + "Holz: " + ressourcen.get(RessourcenEnum.Holz).get.getAnzahl + "\n" else ""
    val nahrung = if (ressourcen.get(RessourcenEnum.Nahrung).get.getAnzahl > 0) buffer + "Nahrung: " + ressourcen.get(RessourcenEnum.Nahrung).get.getAnzahl + "\n" else ""
    val gold = if (ressourcen.get(RessourcenEnum.Gold).get.getAnzahl > 0) buffer + "Gold: " + ressourcen.get(RessourcenEnum.Gold).get.getAnzahl + "\n" else ""
    val stein = if (ressourcen.get(RessourcenEnum.Stein).get.getAnzahl > 0) buffer + "Stein: " + ressourcen.get(RessourcenEnum.Stein).get.getAnzahl + "\n" else ""
    val siedler = if (ressourcen.get(RessourcenEnum.Siedler).get.getAnzahl > 0) buffer + "Siedler: " + ressourcen.get(RessourcenEnum.Siedler).get.getAnzahl + "\n" else ""

    (holz + nahrung + gold + stein + siedler).dropRight(1)
  }
}
