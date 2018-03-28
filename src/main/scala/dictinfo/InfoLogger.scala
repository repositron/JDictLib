package jdict.lib
package dictinfo

import java.io.{FileWriter, PrintWriter}
import java.nio.file.Path
import resource.managed
import scala.io.Source

import jdict.lib.dictinfo.Json._

class InfoLogger(rootPath: Path) {
  private val file = rootPath.resolve(InfoFileStore.InfoFilename).toFile
  def append(info: String) : Unit = {
    for (printWriter <- managed(new PrintWriter(
      new FileWriter(file, true /* append */)))) {
      printWriter.println(info)
      printWriter.println()
    }
  }
  def getFirstInfoEntry() : Option[Info] = {
    import spray.json._
    val reader = managed(Source.fromFile(file)) map {
      input =>
        input.getLines.takeWhile(s => s != "").mkString.parseJson.convertTo[Info]
    }
    reader.opt
  }
}
