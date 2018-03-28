package jdict.lib
package storage

import java.io._

import scala.collection._
import scala.io.Source
import jdict.lib.entrymodel._


class EntryIndex(printWriter: PrintWriter) {
  def write(sequenceNo: String, saveLocation: SaveLocation): Unit = {
    printWriter.println(
      s"$sequenceNo ${saveLocation.relPath} ${saveLocation.byteLocation} ${saveLocation.length}")
  }
  def close(): Unit = printWriter.close()
}

final case class FilenamePosition(fileName: String, position: Int)

object EntryIndexReader {

  def readIndex(file: Source) : mutable.Map[Int, FilenamePosition] = {
    val map: mutable.Map[Int, FilenamePosition] = mutable.Map.empty
    for (line <- file.getLines) {
      val elements = line.split(" ")
      map += (elements(0).toInt -> FilenamePosition(elements(1), elements(2).toInt))
    }
    map
  }
}
