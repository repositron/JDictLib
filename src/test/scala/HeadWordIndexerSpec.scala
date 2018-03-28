/**
  * Created by ljw on 25/07/16.
  */

import jdict.lib._
import jdict.lib.dictionaryparser.DictionaryParser
import org.scalatest._

import scala.xml.pull.XMLEventReader
import collection.mutable.Map

private object StaticsTestMaterials
{
  val sourceXml = """<?xml version="1.0" encoding="UTF-8"?>
<entry>
<ent_seq>1291780</ent_seq>
<k_ele>
<keb>座り込み</keb>
<ke_pri>news1</ke_pri>
<ke_pri>nf23</ke_pri>
</k_ele>
<r_ele>
<reb>すわりこみ</reb>
<re_pri>news1</re_pri>
<re_pri>nf23</re_pri>
</r_ele>
<sense>
<pos>n;</pos>
<pos>vs;</pos>
<gloss>sit-in (i.e. in protest)</gloss>
<gloss xml:lang="dut">sit-in</gloss>
<gloss xml:lang="dut">sit-indemonstratie</gloss>
<gloss xml:lang="dut">sit-downdemonstratie</gloss>
<gloss xml:lang="dut">sit-downstaking</gloss>
<gloss xml:lang="dut">zitstaking</gloss>
<gloss xml:lang="dut">bedrijfsbezetting</gloss>
<gloss xml:lang="dut">bezetting</gloss>
<gloss xml:lang="ger">(n) Sit-in</gloss>
<gloss xml:lang="ger">(m) Sitzstreik</gloss>
</sense>
</entry>
""".replace("\n", "")

  val sourceXml2 =
    """<?xml version="1.0" encoding="UTF-8"?>
<entry>
<ent_seq>1909630</ent_seq>
<k_ele>
<keb>茉莉</keb>
</k_ele>
<r_ele>
<reb>まつり</reb>
</r_ele>
<r_ele>
<reb>マツリ</reb>
<re_nokanji/>
</r_ele>
<sense>
<pos>n;</pos>
<xref>茉莉花</xref>
<misc>uk;</misc>
<gloss>Arabian jasmine (Jasminum sambac)</gloss>
<gloss xml:lang="hun">kuszaság</gloss>
<gloss xml:lang="swe">oreda</gloss>
</sense>
</entry>
    """.replace("\n", "")
}


class HeadWordIndexerSpec extends FreeSpec with GivenWhenThen {
  "JDict XML" - {
    "from extract" - {
      "should have " - {
        "Seq 1291780" in {
          val source = scala.io.Source.fromString(StaticsTestMaterials.sourceXml)
          val headWordIndexer = new HeadWordIndexer(new XMLEventReader(source), List(entry => {
            assert(entry.entSeq == "1291780")
            }), () => ())
          headWordIndexer.generate()
        }
        "Keb equal to 座り込み" in {
          val source = scala.io.Source.fromString(StaticsTestMaterials.sourceXml)
          val headWordIndexer = new HeadWordIndexer(new XMLEventReader(source), List(entry => {
            assert(entry.keb(0).keb == "座り込み")
            assert(entry.keb(0).info.isEmpty)
            assertResult(List("news1", "nf23"))(entry.keb(0).priority)
          }), () => ())
          headWordIndexer.generate()
        }
        "Reading elements まつり and マツリ should be contained" in {
          val source = scala.io.Source.fromString(StaticsTestMaterials.sourceXml2)
          val headWordIndexer = new HeadWordIndexer(new XMLEventReader(source), List(entry => {
            assert(entry.reb(0).reb == "まつり")
            assert(entry.reb(1).reb == "マツリ")
          }), () => ())
          headWordIndexer.generate()
        }
        "Reading no_kanji マツリ entry " in {
          val source = scala.io.Source.fromString(StaticsTestMaterials.sourceXml2)
          val headWordIndexer = new HeadWordIndexer(new XMLEventReader(source), List(entry => {
            assertResult("まつり")(entry.reb(0).reb)
            assertResult("マツリ")(entry.reb(1).reb)
            assert(entry.reb(1).nokanji)
          }), () => ())
          headWordIndexer.generate()
        }
        "Reading まつり entry should have no_kanji as false" in {
          val source = scala.io.Source.fromString(StaticsTestMaterials.sourceXml2)
          val headWordIndexer = new HeadWordIndexer(new XMLEventReader(source), List(entry => {
            assertResult("まつり")(entry.reb(0).reb)
            assert(!entry.reb(0).nokanji)
          }), () => ())
          headWordIndexer.generate()
        }
      }
    }
    "from dictionary file" - {
      "should parse" in  {
        //println()
        val source = scala.io.Source.fromFile("resources/JMdict", "utf-8")
        var count : Int = 0
        val headWordIndexer = new HeadWordIndexer(new XMLEventReader(source), List(x => {
          count = count + 1
          println("seq: " + x.entSeq)
        }), () => ())
        headWordIndexer.generate();
        println("total entries: " + count)
      }
      "using stream" in {
        val source = scala.io.Source.fromFile("resources/JMdict", "utf-8")
        lazy val streamedEntries = DictionaryParser.entries(new XMLEventReader(source))
        streamedEntries.take(10).foreach(entry => println(entry.entSeq))
      }
    }
  }
}
