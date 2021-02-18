package com.alexander.security.examples.persistent.xss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ContentSecurityPolicy implements HandlerInterceptor {

    private static final String CSP_HEADER = "Content-Security-Policy";
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentSecurityPolicy.class);

    private final boolean enabled;
    private final String policy;

    @Inject
    public ContentSecurityPolicy(@Value("${security.csp.enabled}") boolean enabled,
                                 @Value("${security.csp.policy}") String policy) {
        this.enabled = enabled;
        this.policy = policy;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        LOGGER.info("CSP is enabled:{}", enabled);
        if (enabled) {
            LOGGER.info("CSP is set:{}", policy);
            response.addHeader(CSP_HEADER, policy);
        }
    }
}
