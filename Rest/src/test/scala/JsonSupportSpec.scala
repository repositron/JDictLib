package jdict.RestApi.Test

import org.scalatest._
import jdict.lib.{KanjiElement, Sense}


import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._

import scala.collection._
import scala.concurrent.Await
import scala.concurrent.duration._


class JsonSupportSpec extends FreeSpec with GivenWhenThen {
  "Sense to json" - {
    "has glosses" in {
      implicit val system = akka.actor.ActorSystem("system")
      import system.dispatcher
      import jdict.lib.JsonSupport._
      val sense1 = Sense(
        stagk = List("stagk1", "stagk2"),
        stagr = List("stag1", "stagr2"),
        pos = List("pos1", "pos2"),
        xref = List("xref1", "xref2"),
        ant = List("stagk1", "stagk2"),
        field = List("stagk1", "stagk2"),
        misc = List("stagk1", "stagk2"),
        s_inf = List("stagk1", "stagk2"),
        lsource = List("stagk1", "stagk2"),
        dial = List("stagk1", "stagk2"),
        glosses = immutable.Map("eng" -> List("a", "b"), "dut" -> List("aaa", "bbb"))
      )

      //var senseJson : String = MyJsonSupport.SenseJson.write(sense1).prettyPrint
     /* val errorMsg = "easy goes it!"
      val entityFuture  = Marshal(420 -> errorMsg).to[HttpResponse]
      val entity = Await.result(entityFuture, 1.second)
      */
      val kanji = KanjiElement(keb = "keb1",
        info = List("1", "2"),
        priority = List("a", "b", "c"))

      /*
      val entityFuture2 = Marshal(kanji).to[MessageEntity]
      val entity2 = Await.result(entityFuture2, 1.second)
*/
      val senseFuture = Marshal(sense1).to[MessageEntity]
      val senseJson = Await.ready(senseFuture, 1.second)

    /*  val senseFuture = Marshal(sense1).to[MessageEntity]
      val senseJson = Await.ready(senseFuture, 1.second)


*/
     // println(SenseJson.write(sense1).prettyPrint)
      println(senseJson)

    }
  }
}
