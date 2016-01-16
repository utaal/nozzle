import nozzle.modules.module
import nozzle.modules.LoggingSupport._

trait ApiController

case class ApiControllerConfig(thing: String)

@module class ApiControllerImpl(
  val apiDataModule: ApiDataModule,
  apiControllerConfig: nozzle.config.Config[ApiControllerConfig],
  log: ModuleLogger[ApiController]) extends ApiController {

  println(apiControllerConfig.thing)
}
