package com.harrys.tables.error

import java.sql.SQLException

import com.harrys.jooq.util.SQLExceptions
import org.jooq.exception.DataAccessException

/**
  * Created by jpetty on 12/15/15.
  */
object IntegrityConstraintViolation {

  def unapply(e: DataAccessException): Option[SQLException] = extractSQLException(e) match {
    case Some(s) if SQLExceptions.isIntegrityConstraintException(s) => Some(s)
    case None => None
  }

}
