package com.jasmine.doforlove.handler;


import com.jasmine.doforlove.base.BaseEnum;
import com.jasmine.doforlove.util.BaseEnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 类型处理器
 * @author: wanggc
 */
@Slf4j
public class BaseEnumTypeHandler<E extends BaseEnum<?>> extends BaseTypeHandler<BaseEnum<?>> {

    private Class<E> type;

    public BaseEnumTypeHandler(Class<E> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseEnum<?> baseEnum, JdbcType jdbcType) throws SQLException {
        log.info("## type: {} ##", baseEnum.getCode().getClass().getName());
        if (baseEnum.getCode() instanceof Integer) {
            ps.setInt(i, (Integer) baseEnum.getCode());
        } else {
            ps.setString(i, (String) baseEnum.getCode());
        }
    }

    @Override
    public BaseEnum<?> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        log.info("## column name: {} ##", columnName);
        String code = rs.getString(columnName);
        return rs.wasNull() ? null : BaseEnumUtil.codeOf(type, code);
    }

    @Override
    public BaseEnum<?> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        log.info("## column index: {} ##", columnIndex);
        String code = rs.getString(columnIndex);
        return rs.wasNull() ? null : BaseEnumUtil.codeOf(type, code);
    }

    @Override
    public BaseEnum<?> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        log.info("## column index: {} ##", columnIndex);
        String code = cs.getString(columnIndex);
        return cs.wasNull() ? null : BaseEnumUtil.codeOf(type, code);
    }
}
