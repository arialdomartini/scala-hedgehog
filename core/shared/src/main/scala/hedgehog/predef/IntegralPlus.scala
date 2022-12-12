package hedgehog.predef

/** Operations that are unfortunately missing from `Integral` */
trait IntegralPlus[A] {

  def toBigInt(a: A): BigInt

  def fromBigInt(a: BigInt): A
}

object IntegralPlus {

  implicit val ByteIntegralPlus: IntegralPlus[Byte] =
    new IntegralPlus[Byte] {

      override def toBigInt(a: Byte): BigInt =
        BigInt(a.toInt)

      override def fromBigInt(a: BigInt): Byte =
        a.toByte
    }

  implicit val ShortIntegralPlus: IntegralPlus[Short] =
    new IntegralPlus[Short] {

      override def toBigInt(a: Short): BigInt =
        BigInt(a.toInt)

      override def fromBigInt(a: BigInt): Short =
        a.toShort
    }

  implicit val IntIntegralPlus: IntegralPlus[Int] =
    new IntegralPlus[Int] {

      override def toBigInt(a: Int): BigInt =
        BigInt(a)

      override def fromBigInt(a: BigInt): Int =
        a.toInt
      }

  implicit val LongIntegralPlus: IntegralPlus[Long] =
    new IntegralPlus[Long] {

      override def toBigInt(a: Long): BigInt =
        BigInt(a)

      override def fromBigInt(a: BigInt): Long =
        a.toLong
    }
}
