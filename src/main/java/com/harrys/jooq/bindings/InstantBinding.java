package com.harrys.jooq.bindings;

import com.harrys.jooq.converter.InstantConverter;
import org.jooq.impl.DefaultBinding;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by jpetty on 12/15/15.
 */
public final class InstantBinding extends DefaultBinding<Timestamp, Instant> {

    private static final long serialVersionUID = 1L;

    public InstantBinding(){
        super(new InstantConverter());
    }
}
