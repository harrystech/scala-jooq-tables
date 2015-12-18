package com.harrys.jooq.converter;

import com.typesafe.config.*;
import org.jooq.Converter;

import java.util.Objects;

/**
 * Created by jpetty on 12/18/15.
 */
public final class JsonConfigConverter implements Converter<Object, Config> {

    private static final ConfigParseOptions jsonParseOptions = ConfigParseOptions.defaults()
            .setSyntax(ConfigSyntax.JSON)
            .setAllowMissing(false);

    private static final ConfigResolveOptions jsonResolveOptions = ConfigResolveOptions.defaults()
            .setAllowUnresolved(false)
            .setUseSystemEnvironment(false);

    private static final ConfigRenderOptions jsonRenderOptions = ConfigRenderOptions.concise()
            .setJson(true)
            .setComments(false)
            .setFormatted(false)
            .setOriginComments(false);


    @Override
    public Config from(Object databaseObject) {
        if (databaseObject == null){
            return null;
        } else {
            return ConfigFactory.parseString(Objects.toString(databaseObject), jsonParseOptions).resolve(jsonResolveOptions);
        }
    }

    @Override
    public Object to(Config userObject) {
        if (userObject == null){
            return null;
        } else {
            return userObject.root().render(jsonRenderOptions);
        }
    }

    @Override
    public Class<Object> fromType() {
        return Object.class;
    }

    @Override
    public Class<Config> toType() {
        return Config.class;
    }
}
