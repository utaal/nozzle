package io.buildo.base

import spray.http._
import spray.routing._
import scala.concurrent.Future

trait MonadicCtrlRouterModule extends RouterModule
  with MonadicCtrlModule
  with JsonModule {

  import spray.httpx.marshalling._
  implicit def controllerFlowMarshaller[T](
    implicit m: ToResponseMarshaller[WebResponse[T]],
             em: ToResponseMarshaller[(StatusCode, CtrlError)]) =
      ToResponseMarshaller[CtrlFlow[T]] { (value, ctx) =>
        value.map((right: T) => m(WebResponse(right), ctx))
          .valueOr { (left: CtrlError) =>
            val statusCode = left match {
                 case CtrlError.InvalidParam(_, _)         => StatusCodes.UnprocessableEntity
                 case CtrlError.InvalidParams(_)           => StatusCodes.UnprocessableEntity
                 case CtrlError.InvalidOperation(_)        => StatusCodes.UnprocessableEntity
                 case CtrlError.InvalidCredentials         => StatusCodes.Unauthorized
                 case CtrlError.Forbidden(_)               => StatusCodes.Forbidden
                 case CtrlError.NotFound                   => StatusCodes.NotFound
                 case _ => StatusCodes.InternalServerError
            }
            em((statusCode, left), ctx)
          }
      }

  implicit def controllerFlowTMarshaller[A](
    implicit m: ToResponseMarshaller[Future[CtrlFlow[A]]]) =
      ToResponseMarshaller[CtrlFlowT[Future, A]] { (value, ctx) =>
        m(value.run, ctx)
      }

}
