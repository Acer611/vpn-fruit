package com.dragon.fruit.filter;

import com.dragon.fruit.common.constant.Constant;
import com.dragon.fruit.common.utils.EncryptionUtils;
import com.dragon.fruit.common.utils.WrapperedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @program fruit
 * @description:
 * @author: Gaofei
 * @create: 2018/11/21 11:12
 */



@Configuration
@WebFilter(filterName = "commonDataFilter",urlPatterns={"/api/*,/user/*"})
public class CORSFilter  implements Filter {


    private static final Logger logger = LoggerFactory.getLogger(CORSFilter.class);


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        HttpServletRequest request = (HttpServletRequest) req;
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$","");
        logger.info("*********************************过滤器被使用**************************");
        WrapperedResponse wrapResponse = new WrapperedResponse((HttpServletResponse) response);
        if(path.startsWith("/user") || path.startsWith("/api") || path.startsWith("/order")   ){
            chain.doFilter(req, wrapResponse);
            byte[] data = wrapResponse.getResponseData();
            String dataStr = new String(data, "utf-8");
            logger.info("原始返回数据： " + new String(data, "utf-8"));
            // 加密返回报文
            String responseAESBody = EncryptionUtils.NewAESEncode("siweikeji8888888",dataStr);
            logger.info("AES加密返回数据： " + responseAESBody);
            writeResponse(response, responseAESBody);
        }else{
            chain.doFilter(req, response);
        }


    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void destroy() {}

    private void writeResponse(ServletResponse response, String responseString)
            throws IOException {
        PrintWriter out = response.getWriter();
        out.print(responseString);
        out.flush();
        out.close();
    }


}

