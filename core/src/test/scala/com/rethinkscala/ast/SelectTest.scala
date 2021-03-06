package com.rethinkscala.ast

import org.scalatest.FunSuite

import com.rethinkscala.WithBase
import com.rethinkscala.Implicits._
import com.rethinkscala.Document

/**
 * Created with IntelliJ IDEA.
 * User: keyston
 * Date: 7/2/13
 * Time: 11:46 AM
 *
 */

case class SelectFoo(id: Int) extends Document
  /*
class SelectTest extends FunSuite with WithBase {


  ignore("select between") {

    val records = for (i <- 1 to 50) yield SelectFoo(i)
    table.insert(records).run
    val results = table.between(10, 20).order("id").as[SelectFoo]



    assert(results, {
      f: Seq[SelectFoo] => f.size == 11 & f(0).id == 10
    })

  }

  ignore("table select") {

    val results = table.order("id").as[SelectFoo]
    assert(results, {
      f: Seq[SelectFoo] => f.size == 50 && f.last.id == 50
    })
  }

  ignore("table.get") {



    assertAs[SelectFoo](table.get(1), {
      f: SelectFoo => f.id == 1
    })
  }

  ignore("select filter") {

    var results = table.filter(Map("id" -> 1)).as[SelectFoo]
    assert(results, {
      f: Seq[SelectFoo] => f.size == 1
    })

    results = table.filter((f: Var) => f \ "id" > 10).as[SelectFoo]
    assert(results, {
      s: Seq[SelectFoo] => s.size == 40
    })

    results = table.filter((f: Var) => f.hasFields("id")).as[SelectFoo]
    assert(results, {
      s: Seq[SelectFoo] => s.size == 50
    })


  }

}
          */
