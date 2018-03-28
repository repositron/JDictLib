//import JDict.Lib.Listerfier
import org.scalatest.FlatSpec

import scala.xml.pull._

class ListerfySpec extends FlatSpec {
  var flatXml = Util.EventReaderToList(new XMLEventReader(scala.io.Source.fromString(
    "<a>atext</a><b>btext</b>")))
  /*"Flat xml" should "produce a flat List of 2 elements" in {
    var result = Listerfier.listerfy(flatXml)
    assert(result.size == 2)
  }*/

}
