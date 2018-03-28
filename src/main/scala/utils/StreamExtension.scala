package jdict.lib
package utils

import jdict.lib.entrymodel._

object StreamExtension {
  implicit class StreamExtensions(s: Stream[Entry]) {
    def takeOrAll(n: Option[Int]) = {
      n match {
        case Some(num) => s.take(num)
        case None => s
      }
    }
  }
}