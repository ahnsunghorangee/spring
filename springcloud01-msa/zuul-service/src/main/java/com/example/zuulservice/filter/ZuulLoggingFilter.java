package com.example.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class ZuulLoggingFilter extends ZuulFilter {

    // Logger logger = LoggerFactory.getLogger(ZuulLoggingFilter.class);
    // @Slf4j를 쓰면 위에 처럼 Logger 객체를 자동으로 만들어준다. 객체 이름은 log

    @Override
    public Object run() throws ZuulException { // 실제 어떤 동작을 하는지
        log.info("*** printing Logs");

        RequestContext ctx = RequestContext.getCurrentContext(); // Request, Response 객체를 가지고 있는 최상위 오브젝트
        HttpServletRequest request = ctx.getRequest(); // 사용자의 Request 정보를 가지고 있다.

        log.info("*** printing Logs " + request.getRequestURI());
        return null;
    }

    @Override
    public String filterType() { // 사전 필터인지, 사후 필터인지
        return "pre";
    }

    @Override
    public int filterOrder() { // 여러 개 필터가 있을 때의 순서
        return 1;
    }

    @Override
    public boolean shouldFilter() { // 필터를 쓰겠다 or 안 쓰겠다.
        return true;
    }
}
