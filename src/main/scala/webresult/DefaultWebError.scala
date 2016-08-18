package nozzle.webresult

abstract sealed trait DefaultWebError

import spray.http._

object DefaultWebError {
  case class InvalidParam(param: Symbol, value: String) extends DefaultWebError
  case class InvalidParams(params: List[String]) extends DefaultWebError
  case class InvalidOperation(desc: String) extends DefaultWebError
  case object InvalidCredentials extends DefaultWebError
  case class Forbidden(desc: String) extends DefaultWebError
  case object NotFound extends DefaultWebError
}
