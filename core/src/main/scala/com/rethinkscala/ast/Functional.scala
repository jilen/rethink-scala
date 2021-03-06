package com.rethinkscala.ast

import com.rethinkscala.Term

import ql2.Ql2.Term.TermType
import java.util.concurrent.atomic.AtomicInteger


object Predicate {
  private val _nextVarId = new AtomicInteger()

  def nextVarId = _nextVarId.incrementAndGet()


}

object Wrap {


  private def scan(node: Any): Boolean = node match {
    case node: ImplicitVar => true
    case t: Term if (t.args.collectFirst {
      case arg: Term if (scan(arg)) => true
    }.getOrElse(false)) => true
    case t: Term if (t.optargs.collectFirst {
      case p: com.rethinkscala.AssocPair if (scan(p.token)) => true
    }.getOrElse(false)) => true
    case _ => false
  }

  //def apply(t: Typed) = new Predicate1((v: Var) => t)

  def apply(t: Any) = {
    val e = Expr(t).asInstanceOf[Typed]
    val rtn = if (scan(e)) new Predicate1((v: Var) => e).apply() else e
    rtn
  }

  // def apply(t: Typed) = new Predicate1((v: Var) => t)
}

abstract class Predicate {

  val amount: Int

  protected def _invoke(v: Seq[Var]): Typed

  private[rethinkscala] def invoke: Seq[Typed] = {
    val (ids, vars) = take(amount)
    val product = _invoke(vars)
    Seq(MakeArray(ids), product)
  }

  protected def take(amount: Int): (Seq[Int], Seq[Var]) = {
    val ids = for (i <- 1 to amount) yield Predicate.nextVarId
    val vars = ids.map(Var(_))
    (ids, vars)
  }

  def apply(): Term = Func(this)

}

case class Func(f: Predicate) extends Term {

  override lazy val args = buildArgs(f.invoke: _*)

  def termType = TermType.FUNC
}


class Predicate1(f: (Var) => Typed) extends Predicate {

  protected def _invoke(vars: Seq[Var]) = f(vars(0))

  val amount: Int = 1
}

class Predicate2(f: (Var, Var) => Typed) extends Predicate {

  protected def _invoke(v: Seq[Var]) = f(v(0), v(1))

  val amount: Int = 2
}

trait BooleanPredicate extends Predicate with Binary


case class BooleanPredicate1(f: (Var) => Binary) extends BooleanPredicate {

  protected def _invoke(vars: Seq[Var]) = f(vars(0))

  val amount: Int = 1
}

case class BooleanPredicate2(f: (Var, Var) => Binary) extends BooleanPredicate {
  protected def _invoke(v: Seq[Var]) = f(v(0), v(1))

  val amount: Int = 2
}

//class Functional {

// TODO : Reduce

// TODO : Map
// TODO : Filter
// TODO : ConcatMap
// TODO : OrderBy
// TODO : Distinct
// TODO : Count
// TODO : Union
// TODO : Nth
// TODO : GroupedMapReduce
// TODO : GroupBy
// TODO : InnerJoin
// TODO : OuterJoin
// TODO : EqJoin
// TODO : Zip
// TODO : CoerceTo
// TODO : TypeOf
// TODO : Update  ^
// TODO : Delete
// TODO : Replace
// TODO : Insert

//}
