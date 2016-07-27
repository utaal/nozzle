package nozzle.webresult

abstract sealed trait WebError

import spray.http._

object WebError {
  case class InvalidParam(param: Symbol, value: String) extends WebError
  case class InvalidParams(params: List[String]) extends WebError
  case class InvalidOperation(desc: String) extends WebError
  case object InvalidCredentials extends WebError
  case class Forbidden(desc: String) extends WebError
  case object NotFound extends WebError
  case class GenericError(statusCode: StatusCode, error: GenericErrorDesc) extends WebError
  case class GenericErrors(statusCode: StatusCode, errors: List[GenericErrorDesc]) extends WebError
}

trait WebSuccess[T] {
  val value: T
}

trait GenericErrorDesc {
  def desc: String
}
