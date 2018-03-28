package jdict.RestApi

import jdict.lib.{JsonSupport, SeqNums}
import jdict.RestApi.SearchActor.Search
import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging

import scala.concurrent.duration._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.delete
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.MethodDirectives.post
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.directives.PathDirectives.path

import scala.concurrent.Future
import akka.pattern.ask
import akka.util.Timeout

trait SearchRoutes extends JsonSupport {
  // we leave these abstract, since they will be provided by the App
  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[SearchRoutes])

  // other dependencies that UserRoutes use
  def searchActor: ActorRef

  // Required by the `ask` (?) method below
  implicit lazy val timeout = Timeout(5.seconds) // usually we'd obtain the timeout from the system's configuration

  //log.info("message")

  lazy val userRoutes: Route =
  pathPrefix("search") {
    path(IntNumber) { int =>
      get {
        val seqNums: Future[SeqNums] = (searchActor ? Search).mapTo[SeqNums]
        complete(seqNums)
      }
    }
  }
}
