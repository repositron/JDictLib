package jdict.lib
package utils

import java.io.{File, FileWriter, PrintWriter}
import java.nio.file.Path

object FileUtils {
  def createPrintWriter(filepath: File, append: Boolean = false): PrintWriter = {
    if (append && filepath.exists())
      throw new Exception(s"The file ${filepath.toString} already exists")
    new PrintWriter(new FileWriter(filepath, append))
  }

  def join(rootPath: Path, subPath: String) :File = {
    rootPath.resolve(subPath).toFile()
  }
}
