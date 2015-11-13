package com.harrys.tables

import org.jooq.impl.UpdatableRecordImpl

/**
 * Created by chris on 9/16/15.
 */
trait JooqTable[R <: UpdatableRecordImpl[R]] extends AbstractTable[R]{
  override type IdType = java.lang.Integer
}

trait BigJooqTable[R <: UpdatableRecordImpl[R]] extends AbstractTable[R] {
  override type IdType = java.lang.Long
}
