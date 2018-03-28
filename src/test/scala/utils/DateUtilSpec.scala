package jdict.lib.test
package utils

import java.time.{LocalDateTime, ZoneOffset}
import java.time.temporal.TemporalAccessor

import org.scalatest._
import jdict.lib.utils.DateUtil

class DateUtilSpec extends FreeSpec {
  "A datetime of 20111021045011 is parsed" in {
    val dateTime= LocalDateTime.ofInstant(
      DateUtil.parseUtcTimeStamp("20111021045011"), ZoneOffset.UTC)
    assertResult(dateTime)(LocalDateTime.of(2011, 10, 21, 4, 50, 11))
  }

  "A date time of 20111021045011 is printed correctley" in {
    val dateTimeStr = DateUtil.createUtcTimeStamp(
      LocalDateTime.of(2011, 10, 21, 4, 50, 11).toInstant(ZoneOffset.UTC))
    assertResult("20111021045011")(dateTimeStr)
  }
}
