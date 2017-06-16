package com.harrys.jooq.bindings;

import com.harrys.jooq.converter.JsonConfigConverter;
import com.typesafe.config.Config;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

/**
 * Created by jpetty on 12/18/15.
 */
public final class JsonConfigBinding implements Binding<Object, Config> {

    private static final long serialVersionUID = 1L;

    private final JsonConfigConverter converter = new JsonConfigConverter();


    @Override
    public Converter<Object, Config> converter() {
        return converter;
    }

    @Override
    public void sql(BindingSQLContext<Config> ctx) throws SQLException {
        ctx.render().visit(DSL.value(ctx.convert(converter).value())).sql("::json");
    }

    @Override
    public void register(BindingRegisterContext<Config> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
    }

    @Override
    public void set(BindingSetStatementContext<Config> ctx) throws SQLException {
        ctx.statement().setString(
                ctx.index(),
                Objects.toString(ctx.convert(converter).value(), null));
    }

    @Override
    public void set(BindingSetSQLOutputContext<Config> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetResultSetContext<Config> ctx) throws SQLException {
        ctx.convert(converter).value(ctx.resultSet().getString(ctx.index()));
    }

    @Override
    public void get(BindingGetStatementContext<Config> ctx) throws SQLException {
        ctx.convert(converter).value(ctx.statement().getString(ctx.index()));
    }

    @Override
    public void get(BindingGetSQLInputContext<Config> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
