package com.users.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/user/*")
public class UserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            if (session != null && session.getAttribute("admin") == null)
                servletRequest.getServletContext().getRequestDispatcher("/login").forward(request, response);
        }
        filterChain.doFilter(request, response);
    }
}
