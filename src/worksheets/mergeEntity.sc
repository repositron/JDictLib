import scala.xml.pull._

def mergeFmText(events: List[String]) : List[String] = {
  events.foldLeft(List[String]())((result, cur) =>
    if (cur == "b") (result.head + cur) :: result.tail
    else cur :: result
  ).reverse
}


val l1 = List("a", "b", "a", "c")
mergeFmText(l1)

def mergeFm(events: List[XMLEvent]) : List[XMLEvent] = {
  events.foldLeft(List[XMLEvent]())((r, c) =>  c match  {
    case EvEntityRef(entity) => r.head match {
      case EvText(text) => EvText(text + entity) :: r.tail
      case _ => EvText(entity) :: r
    }
    case EvText(text) => r.head match {
      case EvText(text2) => EvText(text2 + text) :: r.tail
      case _ => c :: r
    }
    case _ => c :: r
  }).reverse
}

val xmlEvents = List(
  EvElemStart("","a", null, null),
  EvText("text1"),
  EvEntityRef("amp"), EvText("text2"), EvElemEnd("", "a"))

mergeFm(xmlEvents)