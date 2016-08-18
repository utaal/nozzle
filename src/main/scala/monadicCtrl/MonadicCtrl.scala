package nozzle.monadicctrl

import nozzle.webresult._
import scalaz._
import scala.concurrent.Future
  
trait MonadicCtrl {
  type WebError
  
  type CtrlFlow[A] = \/[WebError, A]
  type CtrlFlowT[F[_], B] = EitherT[F, WebError, B]
  type FutureCtrlFlow[B] = CtrlFlowT[Future, B]
}

trait DefaultErrorMonadicCtrl extends MonadicCtrl {
  type WebError = DefaultWebError
}

object DefaultErrorMonadicCtrl extends DefaultErrorMonadicCtrl
