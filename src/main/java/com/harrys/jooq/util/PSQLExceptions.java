package com.harrys.jooq.util;

import org.postgresql.util.PSQLException;

/**
 * Created by jpetty on 12/15/15.
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
}
