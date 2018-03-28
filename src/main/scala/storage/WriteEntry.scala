package jdict.lib
package storage

import jdict.lib.entrymodel._

import java.nio.file.{Files, Path, Paths}

trait RollingWriter {
  this: RollingFileFactory with WordFrequency =>
  private var fileWriter : FileDetails = createNewFile()
  private var bytesWritten = 0

  def write(string: String) : SaveLocation = {
    if (bytesWritten >= maxSizeKb) {
      rollNewFile()
    }
    fileWriter.writer.write(string, 0, string.length)
    val pos = bytesWritten
    bytesWritten = bytesWritten + string.length
    SaveLocation(fileWriter.relativePath.toString, pos, string.length)
  }

  private def rollNewFile() : Unit = {
    fileWriter.writer.close()
    fileWriter = createNewFile()
    bytesWritten = 0
  }

  def dispose() : Unit = {
    fileWriter.writer.close()
  }
}

final case class SaveLocation(relPath: String, byteLocation: Int, length: Int)

class WriteEntry(frequentWordWriter: RollingWriter,
                 rareWordWriter: RollingWriter) extends WriteEntryTrait {
  import JsonSupport._

  override def write(entry: Entry): SaveLocation = {
    frequentWordWriter.write(entryJsonFormatFormatter.write(entry).prettyPrint)
  }

  override def closeFile(): Unit = {
    frequentWordWriter.dispose()
    rareWordWriter.dispose()
  }
}

object WriteEntry {
  def apply(frequentWordWriter: RollingWriter, rareWordWriter: RollingWriter) =
    new WriteEntry(frequentWordWriter, rareWordWriter)

  def createSaveDirectory(dataParentDir: String, newFolder: String): Path = {
    if (dataParentDir.isEmpty)
      throw new Exception("parent directory name is empty")
    val subDirectory = Paths.get(dataParentDir)
    if (!Files.exists(subDirectory))
      throw new Exception("Directory " + dataParentDir + " doesn't exist")

    val savePath = subDirectory.resolve(newFolder)
    return Files.createDirectory(savePath)
  }
}


