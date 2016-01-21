package com.harrys.tables

import java.sql.SQLException

import com.harrys.jooq.util.JooqExceptions
import org.jooq.exception.DataAccessException
import org.postgresql.util.PSQLException

/**
  * Created by jpetty on 12/15/15.
  */
package object error {

  private [error] def extractSQLException(e: DataAccessException): Option[SQLException] = e match {
    case _ if e != null && JooqExceptions.containsSQLException(e) => Some(e.getCause.asInstanceOf[SQLException])
    case _ => None
  }

}
