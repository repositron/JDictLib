package jdict.lib.dictionaryparser

import jdict.lib.entrymodel.Sense

import scala.collection.mutable.{ListBuffer, Map}
import scala.xml.MetaData
import scala.xml.pull._

trait SenseParserTrait {
  def parse(elements: List[XMLEvent]) : List[Sense]
}

class SenseParser extends SenseParserTrait  {
  private val senses: ListBuffer[Sense] = ListBuffer.empty

  override def parse(elements: List[XMLEvent]) : List[Sense] = {
    var s: Map[String, List[String]] = Map.empty
    var g: Map[String, List[String]] = Map.empty

    def loop(elements: List[XMLEvent]) : List[XMLEvent]= {
      elements match {
        case EvElemStart(_, "sense", _, _)  :: xs => {
          val remainingXml = parseSenseContents(xs)
          loop(remainingXml)
        }
        case _ => elements // should probably throw
      }
    }

    def parseSenseContents(elements: List[XMLEvent]) :  List[XMLEvent] = {
      elements match {
        case EvElemStart(_, "gloss", attrs, _) :: EvText(text) :: EvElemEnd(_, "gloss") :: xs =>
          addElement(langAttribute(attrs), text, g)
          parseSenseContents(xs)
        case EvElemStart(_, tagName, _, _) :: EvText(text) :: EvElemEnd(_, key2)  :: xs if (tagName == key2) =>
          addElement(tagName, text, s)
          parseSenseContents(xs)
        case EvElemStart(_, key, _, _) :: EvElemEnd(_, key2)  :: xs if (key == key2) =>
          addElement(key, "", s)
          parseSenseContents(xs)
        case EvElemEnd(_, "sense") :: xs =>
          senses += addSense()
          s = Map.empty
          g = Map.empty
          xs
        case _  => throw new Exception("expected end element 'sense'")
      }
    }
    def addSense() : Sense = {
      Sense(stagk = s.getOrElse("stagk", List.empty).reverse,
        stagr = s.getOrElse("stagr", List.empty).reverse,
        pos = s.getOrElse("pos", List.empty).reverse,
        xref = s.getOrElse("xref", List.empty).reverse,
        ant = s.getOrElse("ant", List.empty).reverse,
        field = s.getOrElse("field", List.empty).reverse,
        misc = s.getOrElse("misc", List.empty).reverse,
        s_inf = s.getOrElse("s_inf", List.empty).reverse,
        lsource = s.getOrElse("lsource", List.empty).reverse,
        dial = s.getOrElse("dial", List.empty).reverse,
        glosses = g.toMap)
    }
    loop(elements)
    senses.toList
  }


  private def addElement(key: String, value: String, map: Map[String, List[String]]): Unit = {
    val currentValue = map.get(key)
    map += (key -> (value :: currentValue.getOrElse(List.empty)))
  }

  private def langAttribute(metaData: MetaData) : String = {
    metaData.foreach(x => if (x.key == "lang") return x.value.toString())
    "eng"
  }

}
