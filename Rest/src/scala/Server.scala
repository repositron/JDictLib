package jdict.RestApi

import jdict.lib.{HeadWordIndexer, Indexer, KanjiElement}
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn
import scala.xml.pull.XMLEventReader

object Server extends App with SearchRoutes {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val source = scala.io.Source.fromFile("resources/JMdict", "utf-8")
  var indexer = new Indexer
  /*val headWordIndexer = new HeadWordIndexer(new XMLEventReader(source), indexer.indexer, indexer.onComplete)
  headWordIndexer.generate()*/

  val searchActor: ActorRef = system.actorOf(SearchActor.props(indexer),
    "searchActor")

  val route = userRoutes

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}