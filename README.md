# scala-jooq-tables
Library for interacting with jooq tables in a friendlier DSL

# Setting up JooqTables

To use the scala-jooq-tables library in your project you'll need to register the tables in a in a JooqTables Object within your project

Ex:

```scala
package com.example

import com.example.schema.records

object JooqTables {
    final object Users extends JooqTable[records.User] {
       override final val table = tables.User.USERS
       override final val table = table.ID
    }
}

```

# API

## select

Select a record from a table.

select[A](handler: (SelectWhereStep[R]) => A)(implicit context: DSLContext)

Arguments:
 * handler: Ananoymous function that expects a jOOQ SelectWhereStep and returns type A
 * context: Implicit DSLConext object to interact with the database using jOOQ query framework

```scala
import com.example.JooqTables.Users

...

Users.select { sql =>
    sql.where(Users.tables.NAME.eq("Bob")).fetchOne()
}

```

## firstWhere

Select the first record from a table where the passed condition is met

firstWhere(condition: Condition)(implicit context: DSLContext)

Arguments:
 * condition: jOOQ condition object that represents the desired filtering for the table
 * context:   Implicit DSLConext object to interact with the database using jOOQ query framework
 
```scala
import com.example.JooqTables.Users

...

Users.firstWhere(Users.tables.NAME.equal("Bob"))

```

## find

Find the record in the table by ID

find(id: Int)(implicit context: DSLContext)

Arguments:
 * id:      The ID of the record being queried 
 * context: Implicit DSLConext object to interact with the database using jOOQ query framework
 
```scala
import com.example.JooqTables.Users

...

Users.find(13)
```

## findBy

Find a record in the database by a specific field/value combination. Returns the first record where the condition is met. If multiple results are expected, use the #list method

findBy[A](field: Field[A], value: A)(implicit context: DSLContext)

Arguments:
 * field:   jOOQ Field object from the Table
 * value:   the value queried on within the Field
 * context: Implicit DSLConext object to interact with the database using jOOQ query framework
 
```scala
import com.example.JooqTables.Users

...

Users.findBy(Users.table.AGE, 99)
```

## list

List N number of records given a number of conditions

list(where: Seq[Condition] = Seq(), orderBy: Option[SortField[_]] = None, limit: Int = 50, offset: Int = 0)(implicit context: DSLContext)

Arguments:
 * where:   Sequence of jOOQ Conditions to filter query on. Defaults to an Empty Seq
 * orderBy: Field on Table to order results by. Defaults to None
 * limit:   Maximum number of results to return in the Seq. Defaults to 50
 * offset:  Count of records to offset the result sequence by. Defaults to 0
 * context: Implicit DSLConext object to interact with the database using jOOQ query framework
 
```scala
import com.example.JooqTables.Users

...

Users.list(limit=100)
```

## create

Create and save a new Record to a Table

create(handler: (R) => Any)(implicit context: DSLContext)

Arguments:
 * handler: Anonymous function that takes an UpdatableRecord 
 * context: Implicit DSLConext object to interact with the database using jOOQ query framework
 
```scala
import com.example.JooqTables.Users

...

Users.create { record =>
    record.setFirstName("John")
    record.setLastName("Doe")
    record.setAge("33")
}
```

# Using Custom Functions

You can easily add your own custom functions as needs change:

```scala
package com.example

import com.example.schema.records

object JooqTables {
    object Users extends JooqTable[records.User] {
       override final val table = tables.User.USERS
       
       override final val table = table.ID
       
       // Custom Function
       final def findByLastName(lastName: String)(implicit context: DSLContext) : Option[RecordType] = {
         this.findBy(table.LAST_NAME, lastName)
       }
    }
}

```