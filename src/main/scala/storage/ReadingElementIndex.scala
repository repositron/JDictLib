package jdict.lib.storage

import java.io.PrintWriter

import scala.collection.mutable
import scala.io.Source

/**
  * Writes an index of kanji readings
  *
  * @param printWriter inject a java PrintWriter
  */
class ReadingElementIndex(printWriter: PrintWriter) {
  def write(keb: String, seqNos: List[Int]): Unit = {
    printWriter.println(s"$keb, ${seqNos.mkString(",")}")
  }

  def close(): Unit = printWriter.close()
}

object ReadingElementIndexReader {
  def read(file: Source) : mutable.Map[Int, List[Int]] = {
    val map: mutable.Map[Int, List[Int]] = mutable.Map.empty
    for (line <- file.getLines) {
      val elements = line.split(",").map(_.toInt)
      val seqNos : List[Int] = elements.tail.toList
      map += (elements(0) -> seqNos)
    }
    map
  }
}