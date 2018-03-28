package jdict.lib.dictionaryparser

import jdict.lib.entrymodel._

import scala.annotation.tailrec
import scala.collection.mutable.Map
import scala.xml.pull._

private object ParserUtil {
  private def addElement(key: String, value: String, textEnclosed: Map[String, List[String]]): Map[String, List[String]] = {
    val currentValue = textEnclosed.get(key)
    textEnclosed + (key -> (value :: currentValue.getOrElse(List.empty)).reverse)
  }

  def extractEnclosures(elements: List[XMLEvent]) : (Map[String, List[String]], List[XMLEvent]) = {
    @tailrec
    def iter(elements: List[XMLEvent], textEnclosed: Map[String, List[String]]): (Map[String, List[String]], List[XMLEvent]) = {
      elements match {
        case EvElemStart(_, key, _, _) :: EvText(text) :: EvElemEnd(_, key2)  :: xs if (key == key2) =>
          iter(xs, addElement(key, text, textEnclosed))
        case EvElemStart(_, key, _, _) :: EvElemEnd(_, key2)  :: xs if (key == key2) =>
          // closed tag
          iter(xs, addElement(key, "", textEnclosed))
        case _ => (textEnclosed, elements) // found an end tag?
      }
    }
    iter(elements, Map.empty)
  }
}


private object XmlEntryListConverter {
  @tailrec
  def nextEntry(xmlEventReader: XMLEventReader) : List[XMLEvent] = {
    @tailrec
    def loopToEndEntry(entryElements: List[XMLEvent]): List[XMLEvent] = {
      if (!xmlEventReader.hasNext) {
        return entryElements
      }
      xmlEventReader.next match {
        case EvElemEnd(_, "entry") =>
          entryElements
        case EvText("\n") =>
          // assume these are actual newlines and remove
          loopToEndEntry(entryElements)
        case elem @ _ =>
          loopToEndEntry(elem :: entryElements)
      }
    }
    if (!xmlEventReader.hasNext)
      return List.empty

    xmlEventReader.next match {
      case  EvElemStart(_, "entry", _, _) =>
        loopToEndEntry(List.empty)
      case _ =>
        nextEntry(xmlEventReader)
    }
  }
}

object DictionaryParser
{
  def extractEntSeq(entryElements: List[XMLEvent]): (Option[String], List[XMLEvent]) = {
    entryElements match {
      case EvElemStart(_, "ent_seq", _, _) :: EvText(seq) :: EvElemEnd(_, "ent_seq") :: y =>
        (Some(seq), y)
      case _ =>
        (None, entryElements)
    }
  }

  def extractKanjiElements(entryElements: List[XMLEvent]) : (List[KanjiElement], List[XMLEvent]) = {
    case class KebOptionalElements(info: List[String], priority: List[String], remainingXml: List[XMLEvent])

    def matchInfElement(entryElements: List[XMLEvent]) : KebOptionalElements = {
      val textEnclosures = ParserUtil.extractEnclosures(entryElements)._1
      KebOptionalElements(textEnclosures.getOrElse("ke_inf", List.empty),
        textEnclosures.getOrElse("ke_pri", List.empty),
        ParserUtil.extractEnclosures(entryElements)._2)
    }

    def iter(entryElements: List[XMLEvent], kanjiElements: List[KanjiElement]) : (List[KanjiElement], List[XMLEvent]) = {
      entryElements match {
        case EvElemStart(_, "k_ele", _, _)
          :: EvElemStart(_, "keb", _, _)
          :: EvText(kebText)
          :: EvElemEnd(_, "keb")
          :: xs => {
          val inf = matchInfElement(xs)
          inf.remainingXml match {
            case EvElemEnd(_, "k_ele") :: ys =>
              iter(ys, KanjiElement(kebText, inf.info, inf.priority) :: kanjiElements)
            case _ => throw new Exception("expected k_ele at " + kebText)
          }
        }
        case _ => (kanjiElements, entryElements)
      }
    }
    iter(entryElements, List.empty)
  }

  def extractReadingElements(entryElements: List[XMLEvent]) : (List[ReadingElement], List[XMLEvent]) = {
    def loop(entryElements: List[XMLEvent], readingElements: List[ReadingElement]) : (List[ReadingElement], List[XMLEvent]) = {
      entryElements match {
        case EvElemStart(_, "r_ele", _, _)
          :: EvElemStart(_, "reb", _, _)
          :: EvText(rebText)
          :: EvElemEnd(_, "reb")
          :: xs => {
          val (textEnclosuresKeyValues, remainingXml) =  ParserUtil.extractEnclosures(xs)
          remainingXml match {
            case EvElemEnd(_, "r_ele") :: ys => {

              loop(ys,
                ReadingElement(rebText,
                  textEnclosuresKeyValues.isDefinedAt("re_nokanji"),
                  textEnclosuresKeyValues.getOrElse("re_restr", List.empty),
                  textEnclosuresKeyValues.getOrElse("re_inf", List.empty),
                  textEnclosuresKeyValues.getOrElse("re_pri", List.empty))
                  :: readingElements)
            }
            case _ => throw new Exception("expected r_ele at " + rebText)
          }
        }
        case _ => (readingElements, entryElements)
      }
    }
    loop(entryElements, List.empty)
  }

  def parseNextEntry(xmlEventReader: XMLEventReader): Option[Entry] = {
    val entryElements = EntityElementsMerger.mergeEntityElementsToText(
      XmlEntryListConverter.nextEntry(xmlEventReader).reverse);
    if (entryElements.isEmpty)
      None
    else {
      val (seqNum, entryElements2) = extractEntSeq(entryElements)
      val (kanjiElements, e3) = extractKanjiElements(entryElements2)
      val (readingElements, e4) = extractReadingElements(e3)
      val sense =  new SenseParser().parse(e4)
      val entry = new Entry(seqNum.getOrElse("none"),
        kanjiElements.reverse,
        readingElements.reverse,
        sense)
      Some(entry)
    }
  }

  def entries(xmlEventReader: XMLEventReader) : Stream[Entry] = {
    parseNextEntry(xmlEventReader) match {
      case Some(entry) =>  entry #:: entries(xmlEventReader)
      case None => Stream.empty
    }
  }

}