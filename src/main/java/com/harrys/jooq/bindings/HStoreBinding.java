package com.harrys.jooq.bindings;

import com.harrys.jooq.HStoreValue;
import com.harrys.jooq.converter.HStoreValueConverter;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

/**
 * Created by jpetty on 12/15/15.
 */
public final class HStoreBinding implements Binding<Object, HStoreValue> {

    private static final long serialVersionUID = 1L;

    private final HStoreValueConverter converter = new HStoreValueConverter();

    @Override
    public final Converter<Object, HStoreValue> converter(){
        return converter;
    }

    // Rending a bind variable for the binding context's value and casting it to the hstore type
    @Override
    public final void sql(BindingSQLContext<HStoreValue> ctx) throws SQLException {
        ctx.render().visit(DSL.val(ctx.convert(converter()).value())).sql("::hstore");
    }

    // Registering VARCHAR types for JDBC CallableStatement OUT parameters
    @Override
    public final void register(BindingRegisterContext<HStoreValue> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
    }

    // Converting the HStoreValue to a String value and setting that on a JDBC PreparedStatement
    @Override
    public final void set(BindingSetStatementContext<HStoreValue> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
    }

    // Getting a String value from a JDBC ResultSet and converting that to a HStoreValue
    @Override
    public final void get(BindingGetResultSetContext<HStoreValue> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()));
    }

    // Getting a String value from a JDBC CallableStatement and converting that to a HStoreValue
    @Override
    public final void get(BindingGetStatementContext<HStoreValue> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
    }

    // Setting a value on a JDBC SQLOutput (useful for Oracle OBJECT types)
    @Override
    public final void set(BindingSetSQLOutputContext<HStoreValue> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    // Getting a value from a JDBC SQLInput (useful for Oracle OBJECT types)
    @Override
    public final void get(BindingGetSQLInputContext<HStoreValue> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
