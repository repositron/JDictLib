package jdict.lib.test
package TestUtils

import scala.collection.JavaConverters._
import java.nio.file.{Path, Paths}

import org.apache.commons.io.FileUtils
import org.scalatest.{BeforeAndAfterEachTestData, Suite, TestData}

/**
  * mixin creates folder and deletes it after the test is completed
  * Test folder name is set in runner: -DtestRootPath=/home/ljw/stuff/jdicttest/
  * complete folder path is in testDirectory
  */
trait TestFolder extends BeforeAndAfterEachTestData {
  this: Suite =>
  val TestRootPathConfig = "testRootPath"
  val testFolder: String
  var testDirectory: Option[Path] = None
  override def beforeEach(testData: TestData) {

    val root = Paths.get(testData.configMap.getRequired[String](TestRootPathConfig))
    if (!root.toFile.exists())
      throw new Exception(s"TestFolder: The path $root doesn't exist")
    testDirectory = Some(root.resolve(testFolder))
    val testPathFile = testDirectory.get.toFile()
    if (testPathFile.exists()) {

      if (testDirectory.get.asScala.exists(folder => folder == "src"))
        throw new Exception("testPath contains typical src paths: " + testDirectory.get)
    }
    FileUtils.deleteDirectory(testPathFile)
    if (!testPathFile.mkdir())
      throw new Exception(s"TestFolder: couldn't make $testDirectory.get")

    super.beforeEach(testData)
  }

  override def afterEach(testData: TestData): Unit = {
    super.afterEach(testData)
    val root = Paths.get(testData.configMap.getRequired[String](TestRootPathConfig))
    val testPath = root.resolve(testFolder)
    FileUtils.deleteDirectory(testPath.toFile())
  }
}
