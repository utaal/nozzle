# a set of cake traits for uniform project bootstrap and homogeneus improvement of frequently used features

Install with

```scala
resolvers += "buildo" at "https://github.com/buildo/mvn/raw/master/releases"

libraryDependencies += "io.buildo" %% "nozzle" % <version>
```

## Provided traits

*ConfigModule* provides configuration facilities

```scala
private[this] case class LocalConfig(beAwesome: Boolean)

private[this] val localConfig = config.get { conf =>
  LocalConfig(conf.getBoolean("beAwesome"))
}
```

It requires, in the launcher

```scala
override def projectName = "<prjname>"
```

which sets the namespace for the basic config options, like logging.

*JsonSerializerModule* and *MonadicCtrlJsonModule* provide a facility for monadic control in controllers and automatic marshalling of responses / results. Refer to hailadoc on how to use this correctly.

*Router* provides basic facilities for routers. Refer to hailadoc.

*Boot* provides default initialization routines, in your project use the following:

```scala
private val log = logger("Boot")
val b = boot()
import b._
```

*LoggingModule* provides logging facilities. You can enable logging for additional logger names by overriding `logsEnabled`:

```scala
override def logsEnabled(name: Name, level: io.buildo.base.logging.Level)
```

*MonadicRouterHelperModule* provides helper to write compatc, easy-to-read routes. Refer to hailadoc.
