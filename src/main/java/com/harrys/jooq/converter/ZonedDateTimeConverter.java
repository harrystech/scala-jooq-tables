package com.harrys.jooq.converter;

import org.jooq.Converter;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Created by jpetty on 12/18/15.
 */
public final class ZonedDateTimeConverter implements Converter<Timestamp, ZonedDateTime> {
    private static final ZoneId UtcId = ZoneId.of(ZoneOffset.UTC.getId());


    @Override
    public ZonedDateTime from(Timestamp databaseObject) {
        if (databaseObject == null){
            return null;
        } else {
            return databaseObject.toInstant().atZone(UtcId);
        }
    }

    @Override
    public Timestamp to(ZonedDateTime userObject) {
        if (userObject == null){
            return null;
        } else {
            return Timestamp.from(userObject.toInstant());
        }
    }

    @Override
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    public Class<ZonedDateTime> toType() {
        return ZonedDateTime.class;
    }
}
