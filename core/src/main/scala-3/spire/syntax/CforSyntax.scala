package spire
package syntax

trait CforSyntax:
  import macros._
  import collection.immutable.NumericRange

  final type RangeLike = Range | NumericRange[Long]

  final type RangeElem[X <: RangeLike] = X match
    case Range              => Int
    case NumericRange[Long] => Long

  inline def cfor[A](inline init: A)(inline test: A => Boolean, inline next: A => A)(inline body: A => Unit): Unit =
    cforInline(init, test, next, body)

  inline def cforRange[R <: RangeLike](inline r: R)(inline body: RangeElem[R] => Unit): Unit =
    ${ cforRangeMacroGen('r, 'body) }

  inline def cforRange2[R <: RangeLike](inline r1: R, inline r2: R)(inline body: (RangeElem[R], RangeElem[R]) => Unit): Unit =
    cforRange(r1) { x => cforRange(r2) { y => body(x, y) } }
end CforSyntax

