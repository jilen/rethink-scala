package com.rethinkscala.ast

import com.rethinkscala.{InsertResult, DocumentConversion, BinaryConversion, TermMessage}

import ql2.Term.TermType

trait WithDB {
  val db: Option[DB]
}

case class DB(name: String) extends TermMessage {
  override lazy val args = buildArgs(name)

  def termType = TermType.DB

  def newTable(name: String, primaryKey: Option[String] = None, dataCenter: Option[String] = None, cacheSize: Option[Int] = None) = {
    TableCreate(name, primaryKey, dataCenter, cacheSize, Some(this))
  }

  def ^^(name: String, primaryKey: Option[String], dataCenter: Option[String], cacheSize: Option[Int]) = this newTable(name, primaryKey, dataCenter, cacheSize)

  def dropTable(name: String) = TableDrop(name)

  def ^-(name: String) = this dropTable (name)

  def table(name: String, useOutDated: Boolean = false) = Table(name, Some(useOutDated), Some(this))

  def ^(name: String, useOutDated: Boolean = false) = this table(name, useOutDated)

}


case class DBCreate(name: String, db: Option[DB] = None)
  extends TermMessage
          with ProduceBinary with BinaryConversion with WithDB {
  override lazy val args = buildArgs(name)
  val resultField = "created"

  def termType = TermType.DB_CREATE
}

case class DBDrop(name: String) extends TermMessage with ProduceBinary with BinaryConversion {
  override lazy val args = buildArgs(name)
  val resultField = "dropped"

  def termType = TermType.DB_DROP
}

case class DBList(db:Option[DB]=None) extends TermMessage with ProduceSequence with WithDB{
  def termType = TermType.DB_LIST
}


/*
case class SIndexCreate(table:Table,field:String) extends TermMessage with MethodTerm {
  //override lazy val args=buildArgs(db)
  def termType = p.TermMessage.TermType
}
*/ 