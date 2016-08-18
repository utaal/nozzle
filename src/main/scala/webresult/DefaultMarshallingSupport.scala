package nozzle.webresult

import spray.http.{ StatusCode, StatusCodes }

trait DefaultMarshallingSupport extends MarshallingSupport {
  type WebError = DefaultWebError

  implicit def webErrorToStatusCode(webError: WebError) = webError match {
    case DefaultWebError.InvalidParam(_, _)  => StatusCodes.UnprocessableEntity
    case DefaultWebError.InvalidParams(_)    => StatusCodes.UnprocessableEntity
    case DefaultWebError.InvalidOperation(_) => StatusCodes.UnprocessableEntity
    case DefaultWebError.InvalidCredentials  => StatusCodes.Unauthorized
    case DefaultWebError.Forbidden(_)        => StatusCodes.Forbidden
    case DefaultWebError.NotFound            => StatusCodes.NotFound
  }

  implicit def webErrorToMessageString(webError: WebError) = webError match {
    case DefaultWebError.Forbidden(desc)            => s"Forbidden: $desc"
    case DefaultWebError.InvalidParam(param, value) => s"Invalid parameter: ${param.name} ($value)"
    case DefaultWebError.InvalidParams(params) => {
      val errors = params mkString ", "
      s"IDefaultnvalid parameters: $errors"
    }
    case DefaultWebError.InvalidOperation(desc) => s"Invalid operation. $desc"
    case DefaultWebError.InvalidCredentials     => "Invalid credentials"
    case DefaultWebError.NotFound               => "Not found"
  }

}
