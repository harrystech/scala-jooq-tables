package com.harrys.jooq.util;

import java.sql.SQLException;

/**
 * Created by jpetty on 12/15/15.
 */
public final class SQLExceptions {

    public static final String GenericConstraintViolationPrefix = "23";


    public static final boolean isIntegrityConstraintException(final SQLException e){
        return (e.getSQLState() != null && e.getSQLState().startsWith(GenericConstraintViolationPrefix));
    }


}
