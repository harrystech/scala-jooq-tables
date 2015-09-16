package com.harrys.tables

import org.jooq.impl.DSL
import org.jooq.{Configuration, TransactionalCallable, DSLContext}

/**
 * Created by chris on 9/16/15.
 */
final class FunctionalTransaction[A](handler: (DSLContext) => A) extends TransactionalCallable[A] {

  /**
   * Runs the anonymous function from the handler in a transaction
   * @param configuration jOOQ Configuration object that contains information of how to query the database
   * @return Type A as returned by the anonymous function passed into the class constructor
   */
  override def run(configuration: Configuration): A = handler(DSL.using(configuration))
}
