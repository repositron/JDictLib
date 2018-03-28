package jdict.lib.test
package dictinfospec

import java.nio.file.{Files, Path, Paths}

import org.scalatest.FreeSpec

import jdict.lib.dictinfo.{Info, InfoFileStore}
import jdict.lib.test.TestUtils.TestFolder
import jdict.lib.utils.DateUtil

class DictInfoSpec extends {override val testFolder = "DictInfoSpec"}
  with FreeSpec
  with TestFolder {

  val DataFolder = "dataFolder"

  def dataDir : Path = Paths.get(testDirectory.get.toString(), DataFolder)

  "an Info object written to file" - {
    " can be read back to an Info object" in {

      Files.createDirectory(dataDir)
      val dictInfo = Info(
        dataDir.toString,
        DateUtil.parseUtcTimeStamp("20171220130301"),
        true)

      val infoFile = new InfoFileStore(testDirectory.get)
      infoFile.write(dictInfo)

      val infoFileRead = infoFile.read()

      assertResult(dictInfo.path)(infoFileRead.get.path)
    }
  }
  "an InfoFile with 0 entries" - {
    "return None" in {

    }
  }
}
