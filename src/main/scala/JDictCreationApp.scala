package jdict.lib

import java.nio.file.Path
import java.time.Instant
import scala.collection._
import scala.collection.mutable.Map
import scala.collection.concurrent.TrieMap
import scala.xml.pull.XMLEventReader

import jdict.lib.storage.pathcreation.IncrementingIntsPath
import jdict.lib.storage._
import jdict.lib.utils.DateUtil
import jdict.lib.entrymodel.Entry
import jdict.lib.dictionaryparser.DictionaryParser


class Indexer {
  val readingsToEntries : mutable.TreeMap[String, List[Int]] = mutable.TreeMap.empty
  val seqEntryMap : mutable.Map[Int, Entry] = Map.empty
  val trie: TrieMap[String, List[Int]] = TrieMap.empty

  def indexer(entry: Entry) : Unit = {
    val seqNum = entry.entSeq.toInt
    seqEntryMap += (seqNum -> entry)

    entry.reb.foreach(x => {
      readingsToEntries += (x.reb -> (seqNum :: readingsToEntries.getOrElse(x.reb, List.empty)))
      trie += (x.reb -> (seqNum :: readingsToEntries.getOrElse(x.reb, List.empty)))
    })

  }

  def onComplete() : Unit = {
    println("Number of readings: " + readingsToEntries.size)
    println("Number of entries: " + seqEntryMap.size)
  }

  def search(text: String) : Seq[Int] = {
    Seq.empty
  }

  def search(seqNum : Int) : Option[Entry] = {
    None
  }
}



object JDictCreationApp extends App {
  try {
    val source = scala.io.Source.fromFile(args(0), "utf-8")

    // for testing
    val maxEntries: Option[Int] = if (args.length > 2) Some(args(2).toInt) else None
    var count: Int = 0

    val savePath = WriteEntry.createSaveDirectory(args(1), DateUtil.createUtcTimeStamp(Instant.now()))

    val completionMarker = new CompletionMarker(savePath)
    completionMarker.begin()

    class WriteEntryFrequent(path: Path) extends {override val rootDirectory = path } with FrequentWordWriter with RollingFileFactory  with RollingWriter with IncrementingIntsPath
    class WriteEntryRare(path: Path) extends {override val rootDirectory = path }  with RareWordWriter with RollingFileFactory with RollingWriter with IncrementingIntsPath

    val fileFactory = new FileFactory(savePath)
    val writeEntry = WriteEntry(
      new WriteEntryFrequent(savePath), new WriteEntryRare(savePath))


    val entryIndex = new EntryIndex(fileFactory.createEntryIndex)
    val indexer = new Indexer

    val readingElementIndex = new ReadingElementIndex(fileFactory.createReadingElementIndex)
    lazy val entryStream = DictionaryParser.
      entries(new XMLEventReader(source))

    import jdict.lib.utils.StreamExtension._
    entryStream.takeOrAll(maxEntries).
      foreach(entry => {
        // main work done here
        var saveLocation = writeEntry.write(entry)
        entryIndex.write(entry.entSeq, saveLocation)
        indexer.indexer(entry)
      })

    indexer.readingsToEntries map {case (r, seqNos) =>  readingElementIndex.write(r, seqNos)}

    entryIndex.close()
    readingElementIndex.close()
    completionMarker.finishSuccessfully()
  }
  catch  {
    case e: Exception => println("an exception occurred: " + e.getMessage)
  }

  def printHelp() : String = {
    "index [jdict.xml] [output path]"
  }
}
