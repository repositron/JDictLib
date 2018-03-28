package jdict.lib
package storage.pathcreation

import java.nio.file.Path

trait PathGeneration {
  def generateFilename(rootPath: Path): Path
}
trait IncrementingIntsPath extends PathGeneration {
  var value: Int = 1
  override def generateFilename(rootPath: Path): Path = {
    val newPath = rootPath.resolve(f"$value%07d")
    value = value + 1
    newPath
  }
}
