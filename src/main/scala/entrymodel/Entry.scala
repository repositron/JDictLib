/**
  * Created by ljw on 6/08/16.
  */
package jdict.lib
package entrymodel

import scala.collection.immutable.Map

final case class Sense(
  stagk: List[String],
  stagr: List[String],
  pos : List[String],
  xref : List[String],
  ant : List[String],
  field : List[String],
  misc : List[String],
  s_inf : List[String],
  lsource: List[String],
  dial : List[String],
  glosses : Map[String, List[String]] // key represents a language
  )

final case class KanjiElement(
  keb : String,
  info : List[String],
  priority : List[String])

final case class ReadingElement(
  reb : String,
  nokanji : Boolean,
  restr : List[String],
  inf : List[String],
  pri: List[String])

final case class Entry(
  entSeq : String,
  keb: List[KanjiElement],
  reb: List[ReadingElement],
  sense: List[Sense])

final case class SeqNums(
  seqnums: List[Int])
