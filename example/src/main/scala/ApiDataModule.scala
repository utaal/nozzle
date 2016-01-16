import nozzle.modules.module
import nozzle.modules.LoggingSupport._

trait ApiDataModule

case class ApiDataModuleConfig(size: Int)
  
@module class ApiDataModuleImpl(
  apiDataModuleConfig: nozzle.config.Config[ApiDataModuleConfig],
  log: ModuleLogger[ApiDataModule]) extends ApiDataModule {

}

