package dnd.vention.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter("/")
public class SecurityHeaderFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains"); // Enable HSTS
//        httpResponse.setHeader("Content-Security-Policy", "default-src 'self'"); // Set Content Security Policy
//        httpResponse.setHeader("X-Content-Type-Options", "nosniff"); // Prevent MIME sniffing
//        httpResponse.setHeader("X-Frame-Options", "DENY"); // Avoid Clickjacking
//        httpResponse.setHeader("X-XSS-Protection", "1; mode=block"); // Enable XSS protection
//
//        // Continue with the filter chain
//        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
