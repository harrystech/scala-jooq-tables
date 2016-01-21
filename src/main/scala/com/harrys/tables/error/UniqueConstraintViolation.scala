package com.harrys.tables.error

import com.harrys.jooq.util.PSQLExceptions
import org.jooq.exception.DataAccessException
import org.postgresql.util.PSQLException

import scala.collection.JavaConversions

/**
  * Created by jpetty on 12/15/15.
  */
object UniqueConstraintViolation {

  def unapplySeq(e: DataAccessException): Option[List[PSQLException]] = extractPSQLException(e) match {
    case Some(p) =>
      val matched = JavaConversions.asScalaIterator(p.iterator()).collect {
        case e: PSQLException if PSQLExceptions.isUniqueConstraintException(e) => e
      }.toList
      if (matched.isEmpty){
        None
      } else {
        Some(matched)
      }
    case _ => None
  }

}
