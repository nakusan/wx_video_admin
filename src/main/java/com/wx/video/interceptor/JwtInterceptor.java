package com.wx.video.interceptor;

import com.wx.video.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.wx.video.utils.Constrants.BEARER_PREFIX;


/**
 * @description api訪問拦截器
 */
@Component
@Slf4j
public class JwtInterceptor  implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    @Autowired
    private JwtService jwtService;


    /**
     * 在处理请求之前进行JWT验证
     *
     * @param request HTTP请求对象，用于获取请求头中的Authorization字段
     * @param response HTTP响应对象，用于设置响应状态
     * @param handler 请求处理对象，未在此方法中直接使用
     * @return 如果JWT验证通过，则返回true；否则返回false，中断请求处理
     * @throws Exception 如果处理过程中发生异常，会抛出异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的 Authorization 字段
        String authorizationHeader = request.getHeader("Authorization");

        // 定义 Bearer 前缀

        // 验证 JWT
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String token = authorizationHeader.substring(BEARER_PREFIX.length());

            try {
                // 从 token 中获取 openid 并验证
                String openid = jwtService.getOpenidFromToken(token);

                // 检查 token 是否过期
                if (jwtService.isTokenExpired(token)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                    log.warn("Token has expired.");
                    return false;  // token 过期，返回 401
                }

                // 请求继续
                return true;

            } catch (IllegalArgumentException e) {
                // 处理非法参数异常，例如 token 格式错误
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                log.error("Invalid JWT token: {}", e.getMessage(), e);
                return false;
            } catch (Exception e) {
                // 其他异常
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
                log.error("Error validating JWT token: {}", e.getMessage(), e);
                return false;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            log.warn("No JWT token provided.");
            return false;  // 没有提供 JWT，返回 401
        }
    }


}
