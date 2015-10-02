package nozzle

trait JsonModule {
  case class WebResponse[T](value: T)
}
