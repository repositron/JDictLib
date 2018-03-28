package jdict.lib.dictionaryparser

import scala.xml.pull.{EvEntityRef, EvText, XMLEvent}

object EntityElementsMerger {
  def mergeEntityElementsToText(events: List[XMLEvent]) : List[XMLEvent] = {
    events.foldLeft(List[XMLEvent]())((r, c) =>  c match  {
      case EvEntityRef(entity) => r.head match {
        case EvText(text) => EvText(text + entityElement(entity)) :: r.tail
        case _ => EvText(entityElement(entity)) :: r
      }
      case EvText(text) => r.head match {
        case EvText(text2) => EvText(text2 + text) :: r.tail
        case _ => c :: r
      }
      case _ => c :: r
    }).reverse
  }

  private def entityElement(entity : String) : String = {
    entity match {
      case "quot" => "\""
      case "amp" => "&"
      case "apos" => "'"
      case "lt" => "<"
      case "gt" => ">"
      case _ => "no match for entity element"
    }
  }
}
