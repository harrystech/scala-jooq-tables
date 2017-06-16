package com.harrys.sql


import java.sql.Connection

import org.jooq.impl.DSL
import org.jooq.{Configuration, DSLContext, SQLDialect, TransactionalCallable}

import scala.util.{Failure, Success, Try}

/**
  * Created by tom on 2/9/16.
  *
  * Class to aid with executing jOOQ queries within Postgres connections.
  *
  * @param jdbcUrl URL with DSN information, something like postgresql://localhost:5432/public
  * @param applicationName Application name to use for connection (for example, shows up in pg_stat_activity)
  */
final class PostgresConnection(jdbcUrl: String, applicationName: String) {

  if (jdbcUrl == null) {
    throw new IllegalArgumentException("jdbcUrl must not be null!")
  }

  // The jdbc parser is in scala-postgres-utils.
  private val dataSource = PostgresJdbcParser.parseJdbcUrlForDataSource(jdbcUrl)
  dataSource.setLogUnclosedConnections(true)

  def withDSLContext[T](block: (DSLContext) => T): T = {
    withConnection(c => block(DSL.using(c, SQLDialect.POSTGRES)))
  }

  def withConnection[T](block: (Connection) => T) = {
    val connection = dataSource.getConnection
    connection.setClientInfo("ApplicationName", applicationName)
    connection.setAutoCommit(true)
    try {
      block(connection)
    } finally {
      connection.close()
    }
  }

  def withTransaction[A](function: (DSLContext) => A) : A = {
    withDSLContext { sql =>
      sql.transactionResult(new FunctionalTransaction[A](function))
    }
  }

  def tryConnect(): Try[Boolean] = Try(this.withDSLContext(_.selectOne().fetchOne().value1() == 1))

}

final class FunctionalTransaction[A](handler: (DSLContext) => A) extends TransactionalCallable[A] {
  override def run(configuration: Configuration): A = handler(DSL.using(configuration))
}

object PostgresConnectionTest {

  /**
    * Connect to database and execute a "select 1" query to test connection.
    * @param jdbcUrl The connection string with DSN
    * @param applicationName Application name to use for connection
    * @return A boolean denoting successful connection test
    */
  def testConnection(jdbcUrl: String, applicationName: String): Boolean = {
    val db = new PostgresConnection(jdbcUrl, applicationName)

    db.tryConnect match {
      case Failure(reason) => false
      case Success(result) => result
    }

  }
}
