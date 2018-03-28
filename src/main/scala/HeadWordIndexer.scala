package jdict.lib

import scala.annotation.tailrec
import scala.xml.pull.XMLEventReader

import jdict.lib.dictionaryparser.DictionaryParser
import jdict.lib.entrymodel._

class HeadWordIndexer(val xmlEventReader: XMLEventReader, val applyFns: List[Entry => Unit], val completeFn: () => Unit,
                      val maxEntries: Option[Int] = None) {

  @tailrec
  final def generate(number: Int = 0) : Unit = {
    DictionaryParser.parseNextEntry(xmlEventReader) match {
      case Some(entry) => {
        if (maxEntries.isDefined && number < maxEntries.get)
          completeFn()
        else {
          applyFns map (fn => fn(entry))
          generate(number + 1)
        }
      }
      case None => completeFn()
    }
  }
}

