package jdict.RestApi

import jdict.lib.Indexer
import akka.actor.{Actor, ActorLogging, Props}

object SearchActor {
  final case class Search(text: String)
  final case class GetEntry(seqNum: Int)

  def props(indexer: Indexer): Props = Props(new SearchActor(indexer))
}

class SearchActor(indexer: Indexer) extends Actor with ActorLogging {
  import jdict.RestApi.SearchActor._
  override def receive : Receive = {
    case Search(text) =>
      sender() ! indexer.search(text)
    case GetEntry(seqNum) =>
      sender() ! indexer.search(seqNum)
  }
}