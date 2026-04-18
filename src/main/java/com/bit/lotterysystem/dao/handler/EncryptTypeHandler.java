package com.bit.lotterysystem.dao.handler;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.bit.lotterysystem.dao.dateobject.Encrypt;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Encrypt.class) //被处理类型
@MappedJdbcTypes(JdbcType.VARCHAR) //对应类型
/**
 * 翻译工具
 * 1.入库加密：执行sql前，拦截encrypt，将明文加密为密文
 * 2.出库解密：读取结果集时，解密密文，重新封装为encrypt对象交给业务层
 *
 *
 */
public class EncryptTypeHandler extends BaseTypeHandler<Encrypt> {

    private final byte[] KEY = "123456789abcdefg".getBytes(StandardCharsets.UTF_8);



    /**
     * 设置参数
     *
     * @param ps 预编译对象
     * @param i 赋值索引位置
     * @param parameter
     * @param jdbcType jdbc类型
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Encrypt parameter, JdbcType jdbcType) throws SQLException {
        AES aes = SecureUtil.aes(KEY);
        String string=aes.encryptHex(parameter.getValue());
        ps.setString(i,string);
    }

    /**
     * 获取值
     * @param rs
     * @param columnName
     * @return
     * @throws SQLException
     */
    @Override
    public Encrypt getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return decrypt(rs.getString(columnName));
    }

    /**
     * 获取值
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public Encrypt getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return decrypt(rs.getString(columnIndex));
    }

    /**
     * 获取值
     * @param cs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public Encrypt getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return decrypt(cs.getString(columnIndex));
    }

    public Encrypt decrypt(String string){
        if (!StringUtils.hasText(string)){
            return null;
        }
        return new Encrypt(SecureUtil.aes(KEY).decryptStr(string));
    }

}
