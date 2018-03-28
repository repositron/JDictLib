package jdict.lib
package storage

trait WordFrequency {
  protected val folder: String
  protected val maxSizeKb: Int
}

trait RareWordWriter extends WordFrequency {
  protected val folder: String = "rare"
  protected val maxSizeKb: Int = 1024
}

trait FrequentWordWriter extends WordFrequency {
  protected val folder: String = "frequent"
  protected val maxSizeKb: Int = 1024*16
}
