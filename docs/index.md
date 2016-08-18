---
layout: default
title: Nozzle

---

# Introduction

Nozzle is a set of of opinionated - but customisable - tools to rapidly develop Spray-based web services. It's geared towards JSON-based WEB-apis.

It provides (each component optional):

* an _error/success model_ for web services that hides the http semantics from the business logic,
* a _controller monad_ for error management and early return in business logic,
* _serialization_ helpers,
* Spray _bootstrap helpers_ and _logging wrappers_ (for easier management of Akka's logging output),
* Spray _routing helpers_ for concise and readable routers,
* logging and _configuration_ facilities,
* a minimalistic, optional _module system_.

# Show me

Here's the bootstrap function for an example app that leverages all the features of the toolkit:

{% highlight scala %}
object Example extends App {
  implicit val logging = nozzle.logging.BasicLogging()

  implicit val globalExecutionContext: ExecutionContext =
    ExecutionContext.global

  import nozzle.config._
  implicit val configProvider =
      ConfigProvider.empty
        .add(CampingControllerConfig("Le Marze"))

  val campingController = new CampingControllerImpl
  val campingRouter = new CampingRouterImpl(campingController)

  val server = Server(
    "test",
    ServerConfig("0.0.0.0", 8085),
    { implicit actorRefFactory =>
      campingRouter.route
    })
}
{% endhighlight %}
