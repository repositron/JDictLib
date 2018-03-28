package jdict.lib.Test

import jdict.lib._
import dictionaryparser.EntityElementsMerger
import org.scalatest._

import scala.xml.pull._

class EntityElementsMergerSpec extends FreeSpec with GivenWhenThen {
  "An empty list should return an empty list" in {
    val xmlEvents = List.empty
    val mergedElements = EntityElementsMerger.mergeEntityElementsToText(xmlEvents)
    assertResult(mergedElements)(List.empty)
  }

  "A List of text XMLEvents are merged into 1 EvText when elements are" - {
    "EvText EvEntityRef EvText" in {
      val xmlEvents = List(
        EvElemStart("","a", null, null),
        EvText("text1"),
        EvEntityRef("amp"), EvText("text2"), EvElemEnd("", "a"))
      val mergedElements = EntityElementsMerger.mergeEntityElementsToText(xmlEvents)
      assert(mergedElements match {
        case EvElemStart("","a", null, null) ::
          EvText("text1&text2") ::
          EvElemEnd("", "a") :: xs => true
        case _ => false
      })
    }
    "EvText EvEntityRef " in {
      val xmlEvents = List(
        EvElemStart("","a", null, null),
        EvText("text1"),
        EvEntityRef("amp"), EvElemEnd("", "a"))
      val mergedElements = EntityElementsMerger.mergeEntityElementsToText(xmlEvents)
      assert(mergedElements match {
        case EvElemStart("","a", null, null) ::
          EvText("text1&") ::
          EvElemEnd("", "a") :: xs => true
        case _ => false
      })
    }
    "EvTex EvEntity EvText EvEntity EvText" in {
      val xmlEvents = List(
        EvElemStart("","a", null, null),
        EvText("text1"),
        EvEntityRef("amp"),
        EvText("text2"),
        EvEntityRef("lt"),
        EvText("text3"),
        EvEntityRef("gt"),
        EvElemEnd("", "a"))
      val mergedElements = EntityElementsMerger.mergeEntityElementsToText(xmlEvents)
      assert(mergedElements match {
        case EvElemStart("","a", null, null) ::
          EvText("text1&text2<text3>") ::
          EvElemEnd("", "a") :: xs => true
        case _ => false
      })
    }
    "EvEntityRef EvText" in {
      val xmlEvents = List(
        EvElemStart("","a", null, null),
        EvEntityRef("amp"),
        EvText("text1"),
        EvElemEnd("", "a"))
      val mergedElements = EntityElementsMerger.mergeEntityElementsToText(xmlEvents)
      assert(mergedElements match {
        case EvElemStart("","a", null, null) ::
          EvText("&text1") ::
          EvElemEnd("", "a") :: xs => true
        case _ => false
      })
    }
    "EvEntityRef" in {
      val xmlEvents = List(
        EvElemStart("","a", null, null),
        EvEntityRef("amp"),
        EvElemEnd("", "a"))
      val mergedElements = EntityElementsMerger.mergeEntityElementsToText(xmlEvents)
      assert(mergedElements match {
        case EvElemStart("","a", null, null) ::
          EvText("&") ::
          EvElemEnd("", "a") :: xs => true
        case _ => false
      })
    }
  }
}
