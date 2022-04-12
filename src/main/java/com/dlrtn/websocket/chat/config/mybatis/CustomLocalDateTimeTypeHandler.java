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
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@MappedTypes(LocalDateTime.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class CustomLocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    @Bean
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws
            SQLException {
        ps.setString(i, formatter.format(parameter));
    }

    @Bean
    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return LocalDateTime.parse(rs.getString(columnName), formatter);
    }

    @Bean
    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return LocalDateTime.parse(rs.getString(columnIndex), formatter);
    }

    @Bean
    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return LocalDateTime.parse(cs.getString(columnIndex), formatter);
    }
}
