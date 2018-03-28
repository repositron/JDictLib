package jdict.lib.test
package storage

import java.io.{ByteArrayOutputStream, OutputStreamWriter, Writer}
import java.nio.file.{Path, Paths}

import jdict.lib._
import jdict.lib.entrymodel._

import jdict.lib.storage.pathcreation.{IncrementingIntsPath, PathGeneration}
import jdict.lib.storage._
import jdict.lib.test.TestUtils.TestFolder
import org.scalatest.{FreeSpec}

import scala.collection.immutable

object Fixtures {
  val bufferedWriterFrequentMock = new OutputStreamWriter(new ByteArrayOutputStream())
  val bufferedWriterRareMock = new OutputStreamWriter(new ByteArrayOutputStream())

  class WriteEntryFrequentMock(path: Path) extends {override val rootDirectory = path } with FrequentWordWriter  with  RollingFileFactory with RollingWriter with IncrementingIntsPath

  class WriteToFileRareMock(path: Path) extends {override val rootDirectory = path } with RareWordWriter with RollingFileFactory with RollingWriter  with IncrementingIntsPath

}

class WriteEntrySpec extends {override val testFolder = "writeEntrySpec"}
  with FreeSpec
  with TestFolder {

  "writes" in {

    val writeEntry = new WriteEntry(new Fixtures.WriteEntryFrequentMock(testDirectory.get),
      new Fixtures.WriteToFileRareMock(testDirectory.get))

    val saveLocation = writeEntry.write(entries.entry)
    writeEntry.closeFile()
  }
}

object entries {
  import jdict.lib.JsonSupport._
  val sense1 = Sense(
    stagk = List("stagk1", "stagk2"),
    stagr = List("stag1", "stagr2"),
    pos = List("pos1", "pos2"),
    xref = List("xref1", "xref2"),
    ant = List("stagk1", "stagk2"),
    field = List("stagk1", "stagk2"),
    misc = List("stagk1", "stagk2"),
    s_inf = List("stagk1", "stagk2"),
    lsource = List("stagk1", "stagk2"),
    dial = List("stagk1", "stagk2"),
    glosses = immutable.Map("eng" -> List("a", "b"), "dut" -> List("aaa", "bbb"))
  )
  val kanji = KanjiElement(keb = "keb1",
    info = List("1", "2"),
    priority = List("a", "b", "c"))

  val reading = ReadingElement("reb", true, List(), List(), List())
  val entry = Entry("111000", List(kanji), List(reading), List(sense1))
}
