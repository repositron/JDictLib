package jdict.lib

import java.io.{FileWriter, PrintWriter}
import java.nio.file.{Path, Paths}
import java.time.Instant

import jdict.lib.utils.DateUtil

class CompletionMarker(root: Path) {
  private val fileName: Path = root.resolve("completion.txt")
  def begin() : Unit = {
    val printWriter = new PrintWriter(new FileWriter(fileName.toFile))
    printWriter.println("started" + DateUtil.createUtcTimeStamp(Instant.now()))
    printWriter.close()
  }
  def finishSuccessfully(): Unit = {
    val printWriter = new PrintWriter(new FileWriter(fileName.toFile, true))
    printWriter.println("finished " + DateUtil.createUtcTimeStamp(Instant.now()))
    printWriter.close()
  }
}
