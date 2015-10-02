package nozzle

import scalaz._
import scala.concurrent.Future
import scala.language.higherKinds

trait MonadicCtrlModule {
  import scalaz.syntax.either._

  type CtrlFlow[A] = \/[CtrlError, A]

  object CtrlFlow {
    def ok[T](t: T): CtrlFlow[T] = t.right[CtrlError]
    def error[T](error: CtrlError) = error.left[T]
  }

  sealed trait CtrlError

  object CtrlError {
    case class InvalidParam(param: Symbol, value: String) extends CtrlError
    case class InvalidParams(params: List[String]) extends CtrlError
    case class InvalidOperation(desc: String) extends CtrlError
    case object InvalidCredentials extends CtrlError
    case class Forbidden(desc: String) extends CtrlError
    case object NotFound extends CtrlError
  }

  type CtrlFlowT[F[_], B] = EitherT[F, CtrlError, B]

  type FutureCtrlFlow[B] = CtrlFlowT[Future, B]
}
