package jdict.lib
package storage

import java.io.RandomAccessFile

import entrymodel.Entry
import jdict.lib.storage.EntryIndexReader.readIndex

import scala.collection.mutable
import scala.io.Source

class EntryReader(entryIndex: mutable.Map[Int, FilenamePosition]) {
  val openFiles: mutable.Map[Int, Source] =  mutable.Map.empty

  // file name to list of entries
  val fileContentCache: mutable.Map[String, String] = mutable.Map.empty

  val entries: mutable.Map[Int, EntryHolder] = mutable.Map.empty

  def readEntry(seqNo: Int, filename: String, position: Int, length : Int) : EntryHolder = {
    entries.getOrElseUpdate(seqNo, {
      val contents = fileContentCache.getOrElseUpdate(filename, {
        val file = Source.fromFile(filename)
        file.mkString // save contents to the map
      })
      EntryHolder(contents.substring(position, position + length))
    })
  }
}
