package jdict.lib.storage

import java.io.{FileWriter, PrintWriter}
import java.nio.file.Path

import jdict.lib.utils.FileUtils


class FileFactory(savePath: Path) {
  private def createPrintWriter(folder: String, append: Boolean = false): PrintWriter = {
    val filePath = savePath.resolve(folder).toFile()
    if (filePath.exists())
      throw new Exception(s"The file ${filePath.toString} already exists")
    new PrintWriter(new FileWriter(filePath, append))
  }
  def createEntryIndex: PrintWriter =
    FileUtils.createPrintWriter(FileUtils.join(savePath, FileNames.EntryIndex))
  def createReadingElementIndex: PrintWriter =
    FileUtils.createPrintWriter(FileUtils.join(savePath, FileNames.RebIndex))
  def createJDictInfoPrintWriter() = ???
}

object FileNames {
  val EntryIndex = "entryindex.txt"
  val RebIndex = "rebindex.txt"
  val InfoFilename = "jdictinfo.json"
}