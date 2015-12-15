package com.harrys.tables.error

import com.harrys.jooq.util.PSQLExceptions
import org.jooq.exception.DataAccessException
import org.postgresql.util.{PSQLState, PSQLException}
import org.scalatest.{WordSpec, Matchers}

/**
  * Created by jpetty on 12/15/15.
  */
class ExtractorTests extends WordSpec with Matchers {

  "The exception extractors" must {

    "correctly identify exceptions" in {
      val base = new PSQLException("Fake unique constraint violation", new PSQLState(PSQLExceptions.PostgresUniqueConstraintCode))
      val jooq = new DataAccessException("Fake jooq exception", base)
      try {
        throw jooq
      } catch {
        case UniqueConstraintViolation(psql) => psql shouldEqual base
        case _: Throwable => fail("Failed to identify a PSQL unique constraint violation")
      }
    }

    "correctly fall back to the base matcher when appropriate" in {
      val base = new PSQLException("Fake null constraint violation", new PSQLState(PSQLExceptions.PostgresNotNullConstraintCode))
      val jooq = new DataAccessException("Fake jooq exception", base)
      try {
        throw jooq
      } catch {
        case UniqueConstraintViolation(e) => fail(e.getSQLState + " is not a unique constraint violation code")
        case IntegrityConstraintViolation(e) => e shouldEqual base
        case _: Throwable => fail("failed to identify a valid constraint violation")
      }
    }
  }
}
