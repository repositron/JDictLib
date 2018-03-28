package jdict.lib.test

import org.scalatest.FreeSpec
import jdict.lib.utils._

class PrefixMapSpec extends FreeSpec {
  "a prefix string" - {
    "should return the full string" in {
      val prefixMap: PrefixMap[List[Int]] = PrefixMap.empty
      prefixMap.update("あかさたな", List(10))

      prefixMap.withPrefix("あか") map {case (k, v) => println(k)}

      assertResult(List(10))(prefixMap.withPrefix("あか"))
    }
  }
}
