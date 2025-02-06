package com.spring.jwt.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsFilter implements Filter {

    private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
            "https://cartechindia.com", 
            "http://localhost:5173", 
            "http://localhost:63342"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String origin = httpServletRequest.getHeader("Origin");

        // Debugging the origin to trace requests
        System.out.println("CORS Request from Origin: " + origin);

        // Allow matching origins or fallback to allow all (for testing)
        if (origin != null && (ALLOWED_ORIGINS.contains(origin) || "*".equals(origin))) {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
        } else {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        }

        // Allow headers and methods
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", 
            "Authorization, Content-Type, Accept, X-Requested-With, Origin");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");

        // Handle pre-flight requests (OPTIONS)
        if (httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}
