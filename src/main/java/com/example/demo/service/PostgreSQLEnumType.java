package com.example.demo.service;

import com.example.demo.model.ExpenseType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class PostgreSQLEnumType extends org.hibernate.type.EnumType<ExpenseType> {

        public void nullSafeSet(PreparedStatement preparedStatement,
                Object value, int index,
                SharedSessionContractImplementor session) throws HibernateException, SQLException {
            preparedStatement.setObject(index,
                    value != null ?
                            ((ExpenseType) value).name() : null, Types.OTHER);
        }
    }

