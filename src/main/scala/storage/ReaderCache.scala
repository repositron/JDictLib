package jdict.lib
package storage

import java.io.{BufferedReader, FileReader, Reader}
import java.nio.file.Path

import scala.collection.mutable.Map

class ReaderCache {
  val cached: Map[Path, Reader] = Map.empty
  def getReader(path: Path) : Reader = {
    cached.getOrElseUpdate(path, new BufferedReader(new FileReader(path.toFile)) )
  }
  def closeOldFiles() : Unit = {
    ???
  }
  def close() : Unit = {
    cached.foreach(x => x._2.close())
  }
}
