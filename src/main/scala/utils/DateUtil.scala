package jdict.lib
package utils

import java.time.{Instant, ZoneOffset}
import java.time.format.DateTimeFormatter

object DateUtil {
  private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").
    withZone(ZoneOffset.UTC)
  def createUtcTimeStamp(time: Instant): String =
    dateTimeFormatter.format(time)
  def parseUtcTimeStamp(s: String): Instant =
    Instant.from(dateTimeFormatter.parse(s))

}
