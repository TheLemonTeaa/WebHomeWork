package edu.hitwh.homework.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.utils.StringUtils;
import edu.hitwh.homework.pojo.Result;
import edu.hitwh.homework.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("请求拦截...");
        String token = request.getHeader("token");
        log.info("token:{}", token);
        if(StringUtils.isEmpty(token)) {
            log.info("令牌为空!");
            Result responseResult = Result.error("NOT_LOGIN");
            String json = JSONObject.toJSONString(responseResult);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return false;
        }
        try {
            JwtUtils.parseJWT(token);
        }catch (Exception e) {
            log.info("令牌解析失败!");
            Result responseResult = Result.error("NOT_LOGIN");
            String json = JSONObject.toJSONString(responseResult);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return false;
        }
        System.out.println("请求通过...");
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("请求处理中...");
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("处理完成...");
    }
}
