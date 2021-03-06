package com.rethinkscala.ast

import ql2.Ql2.Term.TermType
import com.rethinkscala.Document

case class Get[R <: Document](target: Table[R], attribute: Any) extends ProduceTypedSingleSelection[R] {

  def termType = TermType.GET
}

case class GetAll[R <: Document](target: Table[R], attr: String, index: Option[String] = None) extends ProduceTypedArray[R] {

  override lazy val optargs = buildOptArgs(Map("index" -> index))
  override lazy val args = buildArgs(target, attr)

  def termType = TermType.GET_ALL
}

case class Between[T](target: StreamSelection[T], start: Literal, end: Literal, index: Option[String] = None) extends ProduceTypedStreamSelection[T] {
  override lazy val args = buildArgs(target, start, end)

  override lazy val optargs = buildOptArgs(Map("index" -> index))

  def termType = TermType.BETWEEN
}

case class Filter[T](target: Sequence[T], wrap: FuncWrap, default: Option[Boolean] = None) extends ProduceTypedStreamSelection[T] {

  override lazy val args = buildArgs(target, wrap)
  override lazy val optargs = buildOptArgs(Map("default" -> default))

  def termType = TermType.FILTER
}