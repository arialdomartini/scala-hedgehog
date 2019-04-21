package hedgehog

/**
 * We have our own FP predef for 2 reasons.
 *
 * 1. The obvious political reasons. I don't think there are any really good reasons to need more than one
 * implementation of this library (if we do our job correctly).
 *
 * Probably more importantly:
 *
 * 2. Library dependencies _do_ have a cost. Especially in the JVM world where we insist of relying on binary
 * compatibility.
 */
package object predef {

  def some[A](a: A): Option[A] =
    Some(a)

  @annotation.tailrec
  def findMap[M[_], A, B](fa: LazyList[A])(f: A => Option[B]): Option[B] = {
    fa match {
      case LazyList.Nil() =>
        None
      case LazyList.Cons(h, t) =>
        f(h()) match {
          case Some(b) =>
            Some(b)
          case None =>
            findMap(t())(f)
        }
    }
  }

  /** Performs the action `n` times, returning the list of results. */
  def replicateM[M[_], A](n: Int, fa: M[A])(implicit F: Applicative[M]): M[List[A]] =
    sequence(List.fill(n)(fa))

  /** Strict sequencing in an applicative functor `M` that ignores the value in `fa`. */
  def sequence[M[_], A](fa: List[M[A]])(implicit F: Applicative[M]): M[List[A]] = {
    fa match {
      case Nil =>
        F.point(Nil)
      case h :: t =>
        F.ap(sequence(t))(F.ap(h)(F.point((h2 : A) => (t2 : List[A]) => h2 :: t2)))
    }
  }
}
