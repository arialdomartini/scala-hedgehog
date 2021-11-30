import hedgehog.core._
import hedgehog.extra._
import hedgehog.predef.ApplicativeSyntax

package object hedgehog extends ApplicativeSyntax {

  /**
   * This is _purely_ to make consuming this library a nicer experience,
   * mainly due to Scala's type inference problems and higher kinds.
   */
  object Gen
    extends GenTOps
    with ByteOps
    with CharacterOps
    with StringOps
  type Gen[A] = GenT[A]

  type Property = PropertyT[Result]
  object Property extends PropertyTOps

  type PropertyR[A] = core.PropertyR[A]
  val PropertyR = core.PropertyR

  type Result = hedgehog.core.Result
  val Result = hedgehog.core.Result

  type MonadGen[M[_]] = MonadGenT[M]
  def MonadGen[M[_]] =
    new MonadGenOps[M] {}

  def propertyT: PropertyTOps =
    new PropertyTOps {}

  implicit class Syntax[A](private val a1: A) extends AnyVal {

    // FIX Is there a way to get this to work with PropertyT and type-inference?
    def ====(a2: A): Result =
      Result.diffNamed("=== Not Equal ===", a1, a2)(_ == _)

    def matchPattern(right: PartialFunction[A, _]): Result =
      if (right.isDefinedAt(a1)) Result.success else Result.failure

  }

}
