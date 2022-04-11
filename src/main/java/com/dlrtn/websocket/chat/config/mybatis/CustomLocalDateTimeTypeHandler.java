package com.dlrtn.websocket.chat.config.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(LocalDateTime.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class CustomLocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws
            SQLException {
        ps.setString(i, formatter.format(parameter));
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.parse(rs.getString(columnName), formatter);
        return localDateTime;
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.parse(rs.getString(columnIndex), formatter);
        return localDateTime;
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        LocalDateTime localDateTime = LocalDateTime.parse(cs.getString(columnIndex), formatter);
        return localDateTime;
    }
}
