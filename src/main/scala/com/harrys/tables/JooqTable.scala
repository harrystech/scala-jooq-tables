package com.harrys.tables

import org.jooq._

import scala.util.Try

/**
 * Created by chris on 9/16/15.
 */
trait JooqTable[R <: UpdatableRecord[R]] extends AbstractTable[R]{
  override type IdType = java.lang.Integer
}

trait BigJooqTable[R <: UpdatableRecord[R]] extends AbstractTable[R] {
  override type IdType = java.lang.Long
}
