package com.bit.lotterysystem.common.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JsonParseException;

import java.util.List;
import java.util.concurrent.Callable;

public class JacksonUtil {
    public JacksonUtil(){

    }

    public static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
    }

    public static ObjectMapper getObjectMapper(){
        return OBJECT_MAPPER;
    }

    private static <T> T tryParse(Callable<T> parser) {
        return tryParse(parser, JacksonException.class);
    }

    private static <T> T tryParse(Callable<T> parser, Class<? extends Exception> check) {
        try {
            return parser.call();
        } catch (Exception var4) {
            if (check.isAssignableFrom(var4.getClass())) {
                throw new JsonParseException(var4);
            }

            throw new IllegalStateException(var4);
        }
    }

    /**
     * 序列化
     * @param object 对象，将对象序列化
     * @return
     */
    public static String writeValueAsString(Object object) {
        return JacksonUtil.tryParse(() -> {
            return JacksonUtil.getObjectMapper().writeValueAsString(object);
        });
    }

    /**
     * 反序列化
     * @param content 将json字符串
     * @param valueType 转为指定类
     * @return
     * @param <T>
     */
    public static <T> T readValue(String content, Class<T> valueType) {
        return JacksonUtil.tryParse(() -> {
            return JacksonUtil.getObjectMapper().readValue(content, valueType);
        });
    }


    /**
     * 数组反序列化
     * @param content
     * @param paramClasses
     * @return
     * @param <T>
     */
    public static <T> T readListValue(String content, Class<?> paramClasses) {
        JavaType javaType = JacksonUtil.getObjectMapper().getTypeFactory()
                .constructParametricType(List.class, paramClasses);
        return JacksonUtil.tryParse(() -> {
            return JacksonUtil.getObjectMapper().readValue(content, javaType);
        });
    }

}
