package com.harrys.tables.error

import com.harrys.jooq.util.PSQLExceptions
import org.jooq.exception.DataAccessException
import org.postgresql.util.PSQLException

/**
  * Created by jpetty on 12/15/15.
  */
object ForeignKeyConstraintViolation {

  def unapply(e: DataAccessException): Option[PSQLException] = extractPSQLException(e) match {
    case Some(p) if PSQLExceptions.isForeignKeyConstraintException(p) => Some(p)
    case None => None
  }
}
