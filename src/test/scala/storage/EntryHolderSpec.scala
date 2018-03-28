package jdict.lib.test
package storage

import jdict.lib.storage._

import org.scalatest.{FreeSpec}

class EntryHolderSpec extends FreeSpec {
  "a entry as json string" - {
    "is correctly converted to an Entry" in {
      val entryHolder = EntryHolder(entryJsonStr)
      val entry = entryHolder.entry
      assertResult("1000730")(entry.entSeq)
    }

  }

  val entryJsonStr = """{
      |  "entSeq": "1000730",
      |  "keb": [{
      |    "keb": "行けない",
      |    "info": [],
      |    "priority": []
      |  }],
      |  "reb": [{
      |    "pri": ["ichi1"],
      |    "reb": "いけない",
      |    "inf": [],
      |    "restr": [],
      |    "nokanji": false
      |  }],
      |  "sense": [{
      |    "field": [],
      |    "stagr": [],
      |    "ant": [],
      |    "pos": ["expressions (phrases, clauses, etc.)"],
      |    "lsource": [],
      |    "glosses": {
      |      "ita": ["difettoso", "senza speranza", "disperato", "sbagliato", "bisogna(obbligo)", "non bisogna(divieto)"],
      |      "spa": ["(3) no se debe hacer", "(2) imposible", "equivocado", "malo"],
      |      "fre": ["ne doit pas faire"],
      |      "eng": ["of no use", "not good", "wrong"]
      |    },
      |    "dial": [],
      |    "xref": [],
      |    "s_inf": [],
      |    "misc": ["word usually written using kana alone"],
      |    "stagk": []
      |  }, {
      |    "field": [],
      |    "stagr": [],
      |    "ant": [],
      |    "pos": [],
      |    "lsource": [],
      |    "glosses": {
      |      "eng": ["past hope", "hopeless"]
      |    },
      |    "dial": [],
      |    "xref": [],
      |    "s_inf": [],
      |    "misc": ["word usually written using kana alone"],
      |    "stagk": []
      |  }, {
      |    "field": [],
      |    "stagr": [],
      |    "ant": [],
      |    "pos": [],
      |    "lsource": [],
      |    "glosses": {
      |      "eng": ["must not do"]
      |    },
      |    "dial": [],
      |    "xref": [],
      |    "s_inf": [],
      |    "misc": ["word usually written using kana alone"],
      |    "stagk": []
      |  }]
      |}""".stripMargin
}