package jdict.lib
package dictinfo

import java.io.{FileWriter, PrintWriter}
import java.nio.file.Path
import resource.managed
import scala.io.Source

import jdict.lib.dictinfo.Json._

class InfoFileStore(dataPath: Path) {

  private val file = dataPath.resolve(InfoFileStore.InfoFilename).toFile()

  def write(info: Info) : Unit = {
    val infoJson = infoJsonFormatter.write(info).prettyPrint
    for (printWriter <- managed(new PrintWriter(new FileWriter(
      file)))) {
      printWriter.println(infoJson)
    }
  }

  def read() : Option[Info] = {
    import spray.json._
    val reader = managed(Source.fromFile(file)) map {
      input =>
        input.getLines.takeWhile(s => s != "").mkString.parseJson.convertTo[Info]
    }
    reader.opt
  }
}

object InfoFileStore {
  val InfoFilename = "jdictinfo.json"
}