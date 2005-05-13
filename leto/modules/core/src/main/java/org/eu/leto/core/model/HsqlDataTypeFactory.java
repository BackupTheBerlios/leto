package org.eu.leto.core.model;


import java.sql.Types;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;


public class HsqlDataTypeFactory extends DefaultDataTypeFactory {
    public DataType createDataType(int jdbcType, String name)
            throws DataTypeException {
        if (Types.BOOLEAN == jdbcType) {
            return DataType.INTEGER;
        }

        return super.createDataType(jdbcType, name);
    }
}
