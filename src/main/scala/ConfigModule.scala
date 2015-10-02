package nozzle

import com.typesafe.config._

case class BaseConfig(
  testMode: Boolean)

trait ConfigModule {
  class ConfigProvider(conf: Config) {
    val base = BaseConfig(
      testMode = if (conf.hasPath("banksealer.testMode"))
                   conf.getBoolean("banksealer.testMode") else false
    )
    private var confCache = collection.mutable.HashSet[Function1[Config, _]]()
    def get[T](loader: Config => T) = {
      if (confCache.contains(loader)) {
        throw new Exception("config.get has already been called with this function - it should only be called once at initialization")
      } else {
        confCache += loader
        loader(conf)
      }
    }
  }

  def projectName: String

  val config: ConfigProvider = {
    val conf = ConfigFactory.load()
    new ConfigProvider(conf)
  }
}
