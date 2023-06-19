package com.example.bookstore_backend.Intercepter;

import javax.servlet.*;
import java.io.IOException;

public interface Filter {
    void init(FilterConfig filterConfig) throws ServletException;

    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
    throws IOException, ServletException;
}
