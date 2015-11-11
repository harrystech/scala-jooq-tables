package com.harrys.tables

import org.jooq._

import scala.util.Try

/**
  * Created by jpetty on 11/10/15.
  */
trait AbstractTable[R <: UpdatableRecord[R]]{

  //  Path-dependent type that allows defining result types more easily
  type RecordType = R

  //  Path-dependent type that allows the ID column type to be overridden
  type IdType

  /**
    * The ID column field from the jOOQ UpdatableRecord[R] object
    * @return
    */
  def idColumn: Field[IdType]

  /**
    * the database tabled as a jOOQ Table[R]
    * @return
    */
  def table: Table[R]

  /**
    * @return The default maximum limit to return from this table. This forces larger values
    *          to explicitly provide the limit and allows a single point to categorically override this later on
    */
  def defaultLimit: Int = 50

  /**
    * @return The default sort to use when returning list expressions. This provides a single place to uniformly
    *          override the ordering of list returns
    */
  def defaultOrderBy: SortField[_] = idColumn.desc()

  /**
    * @return The class that corresponds to the record type for this table
    */
  def recordClass: Class[R] = table.getRecordType.asInstanceOf[Class[R]]

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
      Option(sql.where(condition).orderBy(idColumn).limit(1).fetchOne())
    }
  }

  /**
    * A method to find a particular UpdatableRecord[R] give an ID
    * @param id Integer ID of the record being queried
    * @param context jOOQ contextual DSL for interacting with the database
    * @return Option of UpdatableRecord of type R
    */
  def find(id: IdType)(implicit context: DSLContext) : Option[R] = {
    findBy(idColumn, id)
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
  def list(where: Seq[Condition] = Seq(), orderBy: SortField[_] = defaultOrderBy, limit: Int = defaultLimit, offset: Int = 0)(implicit context: DSLContext) : Seq[R] = {
    select { sql =>
      sql
        .where(where:_*)
        .orderBy(orderBy)
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
    val record = context.newRecord(table)
    handler(record)
    val reload = reloadFieldsOnCreate.filterNot(field => record.changed(field))
    record.store()
    if (reload.nonEmpty){
      record.refresh(reload:_*)
    }
    record
  }

  /**
    * Create an UpdatableRecord of type R
    * @param handler Function that interacts with the newly created UpdatableRecord
    * @param context jOOQ contextual DSL for interacting with the database
    * @return The created record wrapped in a Success or Failure
    */
  def tryCreate(handler: (R) => Any)(implicit context: DSLContext) : Try[R] = Try(create(handler))

  /**
    * Cached reference to fields with default values that will reload from the database after the create operation
    */
  private lazy val reloadFieldsOnCreate: Seq[Field[_]] = table.fields().filter(_.getDataType.defaulted())
}
