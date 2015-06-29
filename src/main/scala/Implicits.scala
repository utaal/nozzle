package io.buildo.base

object Implicits {

  /**
   * typesafe version of contains
   * it does not compile when the type of the container
   * does not match the type of the element
   */
  implicit class SeqWithHas[A](s: Seq[A]) {
    def has(a: A) = s.contains(a)
  }
}
