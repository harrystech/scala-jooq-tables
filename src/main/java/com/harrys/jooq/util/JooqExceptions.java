package com.harrys.jooq.util;

import org.jooq.exception.DataAccessException;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

/**
 * Created by jpetty on 12/15/15.
 */
public final class JooqExceptions {

    public static final boolean isIntegrityConstraintException(final DataAccessException e) {
        return (containsSQLException(e) && SQLExceptions.isIntegrityConstraintException((SQLException)e.getCause()));
    }

    public static final boolean isPSQLUniqueConstraintException(final DataAccessException e){
        return (containsPSQLException(e) && PSQLExceptions.isUniqueConstraintException((PSQLException)e.getCause()));
    }

    public static final boolean isPSQLForeignKeyConstraintException(final DataAccessException e){
        return (containsPSQLException(e) && PSQLExceptions.isForeignKeyConstraintException((PSQLException)e.getCause()));
    }

    public static final boolean isPSQLNonNullConstraintException(final DataAccessException e){
        return (containsPSQLException(e) && PSQLExceptions.isNonNullConstraintException((PSQLException)e.getCause()));
    }

    private static final boolean containsPSQLException(final DataAccessException e) {
        return (e.getCause() != null && e.getCause() instanceof PSQLException);
    }

    private static final boolean containsSQLException(final DataAccessException e){
        return (e.getCause() != null && e.getCause() instanceof SQLException);
    }
}
