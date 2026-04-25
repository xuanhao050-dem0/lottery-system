package com.bit.lotterysystem.common.interceptor;

import com.bit.lotterysystem.common.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Configuration
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从 HTTP 请求头中提取名为 token 的字段。
        String token=request.getHeader("token");
        log.info("获取token：{}",token);
        log.info("获取路径：{}",request.getRequestURI());

        //令牌解析
        Claims claims= JWTUtil.parseJWT(token);
        if (null==claims){
            log.error("解析JWT令牌失败！");
            response.setStatus(401);
            return false;
        }
        log.info("解析JWT令牌成功！放行");
        return true;
    }
}
