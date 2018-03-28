package jdict.lib
package dictinfo

import java.time.{Instant, ZoneOffset}
import java.time.format.DateTimeFormatter

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat}

trait Json extends SprayJsonSupport {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._
  implicit object InstantJsonFormat extends RootJsonFormat[Instant] {
    override def read(json: JsValue): Instant = {
      json match  {
        case JsString(s) => Instant.from(DateTimeFormatter.BASIC_ISO_DATE.
          withZone(ZoneOffset.UTC).parse(s))
        case _ => throw new Exception("Can't parse date string")
      }
    }

    override def write(obj: Instant): JsValue = {
      import spray.json._
      DateTimeFormatter.BASIC_ISO_DATE.
        withZone(ZoneOffset.UTC).
        format(obj).toJson
    }
  }


  implicit val infoJsonFormatter = jsonFormat3(Info)

}

object Json extends Json

