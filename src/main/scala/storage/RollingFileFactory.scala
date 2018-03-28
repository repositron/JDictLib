package jdict.lib
package storage

import java.io.{BufferedWriter, FileWriter, Writer}
import java.nio.file.{Files, Path}

import pathcreation.PathGeneration

/**
  * roll incrementing file paths
  */
trait RollingFileFactory {
  this: PathGeneration with WordFrequency =>
  val rootDirectory: Path

  private val saveDirectory = rootDirectory.resolve(folder)
  if (!Files.exists(saveDirectory))
    Files.createDirectory(saveDirectory)
  case class FileDetails(writer: Writer, relativePath: Path)
  def createNewFile() : FileDetails = {
    val filePath = generateFilename(saveDirectory)
    FileDetails(new BufferedWriter(
      new FileWriter(filePath.toFile)), rootDirectory.relativize(filePath))
  }
}