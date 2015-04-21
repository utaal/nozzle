package io.buildo.base

trait Boot extends App
  with ConfigModule
  with IngLoggingModule
  with RouterModule {

  private val log = logger(nameOf[Boot])

  case class BootConfig(
    interface: String,
    port: Int
  )

  trait BootDef {
    implicit val system: akka.actor.ActorSystem
  }

  def boot(): BootDef = new BootDef {
    log.info("Starting")

    val bootConfig = config.get { conf =>
      BootConfig(
        interface = conf.getString(s"$projectName.interface"),
        port = conf.getInt(s"$projectName.port"))
    }

    implicit val system = akka.actor.ActorSystem(s"$projectName")

    val service = system.actorOf(routerActorProps, s"$projectName-router")

    class LauncherActor extends akka.actor.Actor {
      override def preStart: Unit = {
        akka.io.IO(spray.can.Http) ! spray.can.Http.Bind(service,
          interface = bootConfig.interface,
          port = bootConfig.port)
      }

      override def receive = {
        case spray.can.Http.Bound(addr) =>
          log.info(s"Listening on $addr")
          context.stop(self)
      }
    }

    val launcherActor = system.actorOf(akka.actor.Props(new LauncherActor), s"$projectName-launcher")
  }
}
