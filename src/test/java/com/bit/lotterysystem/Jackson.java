package com.bit.lotterysystem;

import com.bit.lotterysystem.common.pojo.Result;
import com.bit.lotterysystem.common.utils.JacksonUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class Jackson {

    @Test
    void jacksonUtil(){
        Result<String> result =Result.success("success");
        String str;

        //序列化
        str=JacksonUtil.writeValueAsString(result);
        System.out.println(str);

        //反序列化
        result=JacksonUtil.readValue(str,Result.class);
        System.out.println(result);

        //序列化list
        List<Result<String>> results= Arrays.asList(
                Result.success("success1"),
                Result.success("success2")
        );
        str=JacksonUtil.writeValueAsString(results);
        System.out.println(str);

        //反序列化list
        results=JacksonUtil.readListValue(str,Result.class);
        for (Result<String> result0:results){
            System.out.println(result0.getDate());
        }
    }

}
