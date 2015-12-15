package com.harrys.jooq;

import org.postgresql.util.HStoreConverter;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jpetty on 12/15/15.
 */
public final class HStoreValue extends TreeMap<String, String> {

    private static final Comparator<String> keyOrder = String::compareTo;

    public HStoreValue(){
        super(keyOrder);
    }

    public HStoreValue(final Map<String, String> values){
        super(keyOrder);
        this.putAll(values);
    }

    public final HStoreValue merge(final HStoreValue other){
        final HStoreValue output = new HStoreValue(this);
        output.putAll(other);
        return output;
    }

    @Override
    public final String toString(){
        return HStoreConverter.toString(this);
    }

    @SuppressWarnings("unchecked")
    public static final HStoreValue parse(final String value) {
        if (value == null){
            return null;
        } else {
            return new HStoreValue(HStoreConverter.fromString(value));
        }
    }
}
