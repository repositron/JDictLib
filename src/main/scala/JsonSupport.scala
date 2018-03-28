package jdict.lib

import jdict.lib.entrymodel._

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val senseJsonFormatter = jsonFormat11(Sense)
  implicit val kanjiElementJsonFormatter = jsonFormat3(KanjiElement)
  implicit val readingElementJsonFormatter = jsonFormat5(ReadingElement)
  implicit val entryJsonFormatFormatter = jsonFormat4(Entry)
  implicit val seqNumJsonFormatFormatter = jsonFormat1(SeqNums)
}

object JsonSupport extends JsonSupport
