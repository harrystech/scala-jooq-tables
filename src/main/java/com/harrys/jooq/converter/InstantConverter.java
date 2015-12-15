package com.harrys.jooq.converter;

import org.jooq.Converter;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by jpetty on 12/15/15.
 */
public final class InstantConverter implements Converter<Timestamp, Instant> {

    @Override
    public final Instant from(Timestamp databaseObject) {
        if (databaseObject == null){
            return null;
        } else {
            return databaseObject.toInstant();
        }
    }

    @Override
    public final Timestamp to(Instant userObject) {
        if (userObject == null){
            return null;
        } else {
            return Timestamp.from(userObject);
        }
    }

    @Override
    public final Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    public final Class<Instant> toType() {
        return Instant.class;
    }
}
