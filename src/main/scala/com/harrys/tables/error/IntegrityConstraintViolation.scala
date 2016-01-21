package com.harrys.tables.error

import java.sql.SQLException

import com.harrys.jooq.util.SQLExceptions
import org.jooq.exception.DataAccessException

import scala.collection.JavaConversions

/**
  * Created by jpetty on 12/15/15.
  */
object IntegrityConstraintViolation {

  def unapplySeq(e: DataAccessException): Option[Seq[SQLException]] = extractSQLException(e) match {
    case Some(s) =>
      val matched = JavaConversions.asScalaIterator(s.iterator()).collect {
        case check: SQLException if SQLExceptions.isIntegrityConstraintException(check) => check
      }.toList
      if (matched.isEmpty){
        None
      } else {
        Some(matched)
      }
    case None => None
  }

}
