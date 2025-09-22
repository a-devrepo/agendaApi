package br.com.nca.filters;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.filter.GenericFilterBean;

import br.com.nca.components.JWTComponent;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends GenericFilterBean {
    
    private final JWTComponent jwtComponent;

    public AuthenticationFilter(JWTComponent jwtComponent) {
        this.jwtComponent = jwtComponent;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
            return;
        }

        UUID userId = jwtComponent.getUser(request);
        if (userId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acesso não autorizado.");
            return;
        }

        request.setAttribute("userId", userId);
        filterChain.doFilter(request, response);
    }
}