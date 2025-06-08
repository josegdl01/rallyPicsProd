package com.example.rallypicsapi.configuracion;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterJWT extends OncePerRequestFilter {

    private final JWTProvider jwtProvider;

    public FilterJWT() {
        this.jwtProvider = new JWTProvider();
    }

    public FilterJWT(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        List<String> allowedUris = Arrays.asList(
                "/auth/login",
                "/auth/registro");
        String requestUri = request.getRequestURI();

        if (allowedUris.contains(requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header != null){
            String authElementsHeader[] = header.split(" "); 

            if(authElementsHeader.length == 2 && authElementsHeader[0].equals("Bearer")){
                try {
                    SecurityContextHolder.getContext().
                        setAuthentication(jwtProvider.validarToken(authElementsHeader[1]));
                } catch(RuntimeException e){
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}

