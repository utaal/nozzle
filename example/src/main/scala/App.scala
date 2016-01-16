import nozzle.server._
import nozzle.server.LoggingSupport._
import nozzle.modules.LoggingSupport._

import spray.routing._
import spray.routing.directives._
import spray.routing.Directives._

class Example extends App {
  implicit val logging = nozzle.logging.BasicLogging()

  val server = Server(
    "test",
    ServerConfig("0.0.0.0", 8085),
    { implicit actorRefFactory =>
      get { complete("hi!") }
    })

  import nozzle.config._
  implicit val configProvider =
      ConfigProvider.empty
        .add(ApiDataModuleConfig(12))
        .add(ApiControllerConfig("hi from config!"))
  implicit def moduleLogger[T](implicit classTag: scala.reflect.ClassTag[T]): ModuleLogger[T] = {
    ModuleLogger(logging.logger(classTag.runtimeClass.getCanonicalName))
  }

  implicit val apiDataModule = ApiDataModuleImpl.apply
  implicit val apiController = ApiControllerImpl.apply
}
