package nozzle

import spray.json._
import io.buildo.ingredients.jsend._

trait MonadicCtrlJsonModule extends JsonModule with MonadicCtrlModule {

  import DefaultJsonProtocol._
  import JSendJsonProtocol._

  implicit def WebResponseWriter[T: JsonFormat](
    implicit jsendable: JSendable[T]) = new RootJsonWriter[WebResponse[T]] {
      def write(t: WebResponse[T]) = jsendable.toJSendSuccess(
      t.value).toJson
    }

  implicit def WebResponsePluralWriter[T: JsonFormat](
    implicit jsendable: JSendable[List[T]]) = new RootJsonWriter[WebResponse[List[T]]] {
      def write(t: WebResponse[List[T]]) = jsendable.toJSendSuccess(
        t.value).toJson
    }

  implicit def WebResponseUnitWriter = new RootJsonWriter[WebResponse[Unit]] {
      def write(t: WebResponse[Unit]) = JSendEmptySuccess.toJson
    }

  implicit object CtrlErrorWriter extends RootJsonWriter[CtrlError] {
    def write(ctrlError: CtrlError) =
      JSendError(
        message = ctrlError match {
          case CtrlError.Forbidden(desc)            => s"Forbidden: $desc"
          case CtrlError.InvalidParam(param, value) => s"Invalid parameter: ${param.name} ($value)"
          case CtrlError.InvalidParams(params) => {
            val errors = params mkString ", "
            s"Invalid parameters: $errors"
          }
          case CtrlError.InvalidOperation(desc) => s"Invalid operation. $desc"
          case CtrlError.InvalidCredentials     => "Invalid credentials"
          case CtrlError.NotFound               => "Not found"
        }).toJson
  }
}
