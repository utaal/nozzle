package io.buildo.base

trait JsonModule {
  case class WebResponse[T](value: T)
}
