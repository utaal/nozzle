package nozzle

import spray.http._
import spray.routing._
import spray.routing.directives._
import spray.routing.Directives._
import spray.httpx.encoding._

trait RouterModule extends LoggingModule {

  private val log = logger(nameOf[RouterModule])

  trait RouterBase extends spray.routing.HttpService {
    def withGzipSupport = (decodeRequest(NoEncoding) | decodeRequest(Gzip)) &
                          (encodeResponse(NoEncoding) | encodeResponse(Gzip))

    def route: (RequestContext => Unit)

    def showErrorResponses(request: HttpRequest): Any => Unit = {
      case HttpResponse(StatusCodes.OK | StatusCodes.NotModified | StatusCodes.PartialContent, _, _, _) => ()
      case response @ HttpResponse(StatusCodes.NotFound, _, _, _) =>
        log.debug(s"${response.status.intValue} ${request.method} ${request.uri}")
      case response @ HttpResponse(StatusCodes.Found | StatusCodes.MovedPermanently, _, _, _) =>
        log.info(s"${response.status.intValue} ${request.method} ${request.uri} -> ${response.header[HttpHeaders.Location].map(_.uri.toString).getOrElse("")}")
      case response @ HttpResponse(_, _, _, _) =>
        log.error(s"${response.status.intValue} ${request.method} ${request.uri}: ${response}")
      case response =>
        log.error(s"UNKNOWN ${request.method} ${request.uri}: ${response}")
    }

    def withErrorResponseLogger: Directive0 = {
      println(implicitly[akka.actor.ActorRefFactory])
      logRequestResponse({ request: HttpRequest =>
        (resp: Any) => {
          showErrorResponses(request)(resp)
          (None : Option[spray.routing.directives.LogEntry])
        }
      })
    }
  }


  type RouterActorImpl

  def routerClass: Class[_ <: RouterActorImplBase]

  trait RouterActorImplBase extends akka.actor.Actor with RouterBase {
    def actorRefFactory = context
    def receive = runRoute(route)
  }

  def routerActorProps = akka.actor.Props(routerClass, this)

}
