package com.harrys.jooq.converter;

import com.harrys.jooq.HStoreValue;
import org.jooq.Converter;

/**
 * Created by jpetty on 12/15/15.
 */
public final class HStoreValueConverter implements Converter<Object, HStoreValue> {

    @Override
    public final HStoreValue from(Object databaseObject) {
        if (databaseObject == null){
            return null;
        } else {
            return HStoreValue.parse(databaseObject.toString());
        }
    }

    @Override
    public final Object to(HStoreValue userObject) {
        if (userObject == null){
            return null;
        } else {
            return org.postgresql.util.HStoreConverter.toString(userObject);
        }
    }

    @Override
    public final Class<Object> fromType() {
        return Object.class;
    }

    @Override
    public final Class<HStoreValue> toType() {
        return HStoreValue.class;
    }
}