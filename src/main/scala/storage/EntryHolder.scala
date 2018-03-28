package jdict.lib
package storage

import jdict.lib.entrymodel.Entry
import jdict.lib.JsonSupport._

import spray.json._

/**
  * The entry as a json string and lazily create Entry type
 */
class EntryHolder(entryJson: String) {
  lazy val entry : Entry = {
    entryJson.parseJson.convertTo[Entry]
  }
}

object EntryHolder {
  def apply(entryJson: String)  = new EntryHolder(entryJson)
}