package nozzle.webresult

import spray.http.StatusCode

trait MarshallingSupport {
  protected type Ok[T] <: WebSuccess[T]
  protected def Ok[T](t: T): Ok[T]

  implicit def webErrorToStatusCode(webError: WebError): StatusCode
  implicit def webErrorToMessageString(webError: WebError): String
}
