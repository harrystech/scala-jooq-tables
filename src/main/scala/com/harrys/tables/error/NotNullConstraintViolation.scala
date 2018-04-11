package com.harrys.tables.error

import java.sql.BatchUpdateException

import com.harrys.jooq.util.PSQLExceptions
import org.jooq.exception.DataAccessException
import org.postgresql.util.PSQLException


import scala.collection.JavaConverters
/**
  * Created by jpetty on 12/15/15.
  */
object NotNullConstraintViolation {

  def unapplySeq(e: DataAccessException): Option[Seq[PSQLException]] = extractSQLException(e) match {
    case Some(batch: BatchUpdateException) => extractNotNullConstraintErrors(JavaConverters.asScalaIterator(batch.iterator()))
    case Some(psql:  PSQLException)        => extractNotNullConstraintErrors(JavaConverters.asScalaIterator(psql.iterator()))
    case _ => None
  }

  private def extractNotNullConstraintErrors(errors: Iterator[Throwable]): Option[Seq[PSQLException]] = {
    val matched = errors.collect {
      case e: PSQLException if PSQLExceptions.isNonNullConstraintException(e) => e
    }.toSeq
    if (matched.isEmpty) {
      None
    } else {
      Some(matched)
    }
  }
}
