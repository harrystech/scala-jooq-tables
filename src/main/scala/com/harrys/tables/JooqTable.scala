package com.harrys.tables

import org.jooq._

/**
 * Created by chris on 9/16/15.
 */
trait JooqTable[R <: UpdatableRecord[R]]{

  /**
   * The ID column field from the jOOQ UpdatableRecord[R] object
   * @return
   */
  def idColumn: Field[Integer]

  /**
   * the database tabled as a jOOQ Table[R]
   * @return
   */
  def table: Table[R]

  type RecordType = R

  /**
   * Generic select for database record(s) of type A
   * @param handler A function that takes a SELECT generic
   * @param context jOOQ contextual DSL for interacting with the database
   * @tparam A Type of record returned by the query
   * @return classOf[A]
   */
  def select[A](handler: (SelectWhereStep[R]) => A)(implicit context: DSLContext) : A = {
    context.selectFrom(table)
    handler(context.selectFrom(table))
  }

  /**
   * Return the first record of type R where the condition is met
   * @param condition jOOQ's version of a where clause used for filtering a query
   * @param context jOOQ contextual DSL for interacting with the database
   * @return Option of UpdatableRecord of type R
   */
  def firstWhere(condition: Condition)(implicit context: DSLContext) : Option[R] = {
    select { sql =>
      Option(sql.where(condition).orderBy(idColumn).fetchOne())
    }
  }

  /**
   * A method to find a particular UpdatableRecord[R] give an ID
   * @param id Integer ID of the record being queried
   * @param context jOOQ contextual DSL for interacting with the database
   * @return Option of UpdatableRecord of type R
   */
  def find(id: Int)(implicit context: DSLContext) : Option[R] = {
    findBy(idColumn, id.asInstanceOf[Integer])
  }

  /**
   * Query a Table[R] on a given column and value
   * @param field the jOOQ representation of the SQL column being queried on
   * @param value the value to filter the above field on
   * @param context jOOQ contextual DSL for interacting with the database
   * @tparam A Type i.e. String, Int, etc of the field and value being queried
   * @return Option of UpdatableRecord of type R
   */
  def findBy[A](field: Field[A], value: A)(implicit context: DSLContext) : Option[R] = {
    firstWhere(field.equal(value))
  }

  /**
   * Retrieve a list of UpdatableRecord[R] from a given Table[R]
   * @param where a Sequence of jOOQ Conditions
   * @param orderBy Field[A] that the results should be sorted by
   * @param limit Max number of records to return. Defaults to 50
   * @param offset How many records to offset the result
   * @param context jOOQ contextual DSL for interacting with the database
   * @return Sequence of UpdatableRecord of type R
   */
  def list(where: Seq[Condition] = Seq(), orderBy: Option[SortField[_]] = None, limit: Int = 50, offset: Int = 0)(implicit context: DSLContext) : Seq[R] = {
    select { sql =>
      val sort = orderBy.getOrElse(idColumn.desc())
      sql
        .where(where:_*)
        .orderBy(sort)
        .limit(limit)
        .offset(offset)
        .fetchArray().toSeq
    }
  }

  /**
   * Create an UpdatableRecord of type R
   * @param handler Function that interacts with the newly created UpdatableRecord
   * @param context jOOQ contextual DSL for interacting with the database
   * @return UpdatableRecord of type R
   */
  def create(handler: (R) => Any)(implicit context: DSLContext) : R = {
    withTransaction { sql =>
      val record = context.newRecord(table)
      handler(record)
      record.store()
      record
    }
  }

  /**
   * Wrap the jOOQ database interactions in a transaction
   * @param handler Function that takes a DSL context and has a return type of A
   * @param context jOOQ contextual DSL for interacting with the database
   * @tparam A Return type of the anonymous function
   * @return Return value of passed anonymous function
   */
  def withTransaction[A](handler: (DSLContext) => A)(implicit context: DSLContext) : A = {
    context.transactionResult(new FunctionalTransaction[A](handler))
  }
}
