package io.buildo.base

import com.typesafe.config._

case class BaseConfig(
  testMode: Boolean)

trait ConfigModule {
  class ConfigProvider(conf: Config) {
    val base = BaseConfig(
      testMode = if (conf.hasPath("banksealer.testMode"))
                   conf.getBoolean("banksealer.testMode") else false
    )
    private var confCache = collection.mutable.HashMap[Function1[Config, _], Any]()
    def get[T](loader: Config => T) = {
      confCache.get(loader).map { t =>
        t.asInstanceOf[T]
      }.getOrElse {
        val loaded = loader(conf)
        confCache(loader) = loaded
        loaded
      }
    }
  }

  def projectName: String

  val config: ConfigProvider = {
    val conf = ConfigFactory.load()
    new ConfigProvider(conf)
  }
}
