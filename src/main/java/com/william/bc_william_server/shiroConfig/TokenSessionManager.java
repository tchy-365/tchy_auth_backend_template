package com.william.bc_william_server.shiroConfig;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.UUID;


// 自定义头部里的token
public class TokenSessionManager extends DefaultWebSessionManager {

    private static final String TOKEN_HEADER_NAME = "token";

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 从前端头里获取token
        String token = WebUtils.toHttp(request).getHeader("token");
        if(!StringUtils.hasText(token)){
//            token = UUID.randomUUID().toString();
            token = "william";
        }
        return token;
    }
}
