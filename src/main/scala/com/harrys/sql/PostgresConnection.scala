package com.harrys.sql


import java.sql.Connection

import org.jooq.impl.DSL
import org.jooq.{DSLContext, SQLDialect}

import scala.util.{Failure, Success, Try}

/**
  * Created by tom on 2/9/16.
  *
  * Class to aid with executing jOOQ queries within Postgres connections.
  *
  * @param jdbcUrl URL with DSN information, something like postgresql://localhost:5432/public
  * @param applicationName Optional application name to use for connection (for example, used in pg_stat_activity)
  */
final class PostgresConnection(jdbcUrl: String, applicationName: Option[String] = None) {

  if (jdbcUrl == null) {
    throw new IllegalArgumentException("jdbcUrl must not be null!")
  }

  // The jdbc parser is in scala-postgres-utils.
  private val dataSource = PostgresJdbcParser.parseJdbcUrlForDataSource(jdbcUrl)

  def withDSLContext[T](block: (DSLContext) => T): T = {
    withConnection(c => block(DSL.using(c, SQLDialect.POSTGRES)))
  }

  def withConnection[T](block: (Connection) => T) = {
    val connection = dataSource.getConnection
    val appName = applicationName getOrElse block.getClass.getPackage.getName
    connection.setClientInfo("ApplicationName", appName)
    connection.setAutoCommit(true)
    try {
      block(connection)
    } finally {
      connection.close()
    }
  }

  def tryConnect(): Try[Boolean] = Try(this.withDSLContext(_.selectOne().fetchOne().value1() == 1))

}


object PostgresConnectionTest {

  /**
    * Connect to database and execute a "select 1" query to test connection.
    * @param jdbcUrl The connection string with DSN
    * @return A boolean denoting successful connection test
    */
  def testConnection(jdbcUrl: String): Boolean = {
    val db = new PostgresConnection(jdbcUrl)

    db.tryConnect match {
      case Failure(reason) => false
      case Success(result) => result
    }

  }
}
