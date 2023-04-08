package com.tablereservation.manager.config.filter;

import com.tablereservation.manager.service.ManagerService;
import com.tablereservation.secret.common.UserVo;
import com.tablereservation.secret.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/manager/")
@RequiredArgsConstructor
public class ManagerFilter implements Filter{

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final ManagerService managerService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)  request;
        String token = req.getHeader("X-AUTH-TOKEN");
        if(!jwtAuthenticationProvider.validateToken(token)) {
            throw new ServletException("Invalid Access");
        }
        UserVo vo = jwtAuthenticationProvider.getUserVo(token);
        managerService.findByIdAndEmail(vo.getId(), vo.getEmail()).orElseThrow(
                () -> new ServletException("Invalid access")
        );
        chain.doFilter(request, response);
    }
}