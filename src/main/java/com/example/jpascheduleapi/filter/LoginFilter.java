package com.example.jpascheduleapi.filter;

import com.example.jpascheduleapi.common.Const;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class LoginFilter implements Filter {
    private static final String[] WHITE_LIST = {"/users", "/login"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        // POST WHITE_LIST에 포함되지 않을 시
        if (!(isWhiteList(requestURI, method))) {
            HttpSession session = httpRequest.getSession(false);

            if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
                httpResponse.sendRedirect("/login");
                return;
            }

        }

        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI, String method) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI) && method.equals("POST");
    }
}
