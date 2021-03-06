package com.rethinkscala.ast

import org.scalatest.FunSuite
import com.rethinkscala._

/**
 * Created by IntelliJ IDEA.
 * User: Keyston
 * Date: 10/28/13
 * Time: 10:19 AM 
 */
class SequenceTest extends FunSuite with WithBase {

  test("mapping from ProduceAny with casting to String") {


    val rows = Expr(Seq(Map("hello" -> "1"), Map("hello" -> "2"), Map("hello" -> "3")))


    val a = rows.map(r.row("hello").string).filter(_ =!= "1")






    val ast = a.ast

    assert(a.run, {
      x: Seq[String] => x.size == 2
    })



    val composed = a.reduce ! ((x, y) => x add y)




    assert(composed, "23")
  }
}
