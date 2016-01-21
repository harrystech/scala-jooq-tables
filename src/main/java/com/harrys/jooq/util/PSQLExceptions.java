package com.harrys.jooq.util;

import org.postgresql.util.PSQLException;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.stream.StreamSupport;

/**
 * The error codes checked in the SQL state are derived from Postgres' SQL error codes, which can be found
 * <a href="http://www.postgresql.org/docs/9.4/static/errcodes-appendix.html">on their website</a>. The codes
 * checked in this file are only the ones I found necessary at the time, and should definitely be supplemented
 * if you find a useful error code check is missing.
 *
 * @author  James Petty
 * @since   1.4.0
 */
public final class PSQLExceptions {

    public static final String PostgresNotNullConstraintCode    = "23502";
    public static final String PostgresForeignKeyConstraintCode = "23503";
    public static final String PostgresUniqueConstraintCode     = "23505";


    public static final boolean isNonNullConstraintException(final PSQLException e){
        return PostgresNotNullConstraintCode.equals(e.getSQLState());
    }

    public static final boolean isUniqueConstraintException(final PSQLException e) {
        return PostgresUniqueConstraintCode.equals(e.getSQLState());
    }

    public static final boolean isForeignKeyConstraintException(final PSQLException e){
        return PostgresForeignKeyConstraintCode.equals(e.getSQLState());
    }

    public static final boolean isIntegrityConstraintException(final PSQLException e){
        return SQLExceptions.isIntegrityConstraintException(e);
    }

    public static final boolean containsAnyUniqueConstraintExceptions(final PSQLException e) {
        final Iterator<Throwable> checks = e.iterator();
        while (checks.hasNext()) {
            final Throwable check = checks.next();
            if (check instanceof PSQLException) {
                if (isUniqueConstraintException((PSQLException)check)){
                    return true;
                }
            }
        }
        return false;
    }
}
