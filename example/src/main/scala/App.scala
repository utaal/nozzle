import nozzle.server._
import nozzle.server.LoggingSupport._
import nozzle.modules.LoggingSupport._

object Example extends App {
  implicit val logging = nozzle.logging.BasicLogging()

  import nozzle.config._
  implicit val configProvider =
      ConfigProvider.empty
        .add(CampingControllerConfig("Le Marze"))

  implicit val campingController = CampingControllerImpl.apply
  implicit val campingRouter = CampingRouterImpl.apply

  val server = Server(
    "test",
    ServerConfig("0.0.0.0", 8085),
    { implicit actorRefFactory =>
      campingRouter.route
    })
}
