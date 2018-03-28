package jdict.lib.storage

import jdict.lib.entrymodel.Entry

trait WriteEntryTrait {

  def write(entry: Entry): SaveLocation
  def closeFile(): Unit
}
