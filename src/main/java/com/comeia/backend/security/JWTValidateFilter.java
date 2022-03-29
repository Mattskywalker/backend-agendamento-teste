package com.comeia.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTValidateFilter extends BasicAuthenticationFilter {

    public static final String HEADER_ATRIBUTE = "Authorization";
    public static final String ATRIBUTE_PREFIX = "Bearer";
    private final UserDetailsService userDetailsService;

    public JWTValidateFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String atribute = request.getHeader(HEADER_ATRIBUTE);
//        System.out.println(atribute);
        if(atribute == null) {
            chain.doFilter(request,response);
            return;
        }

        if(!atribute.startsWith(ATRIBUTE_PREFIX)) {
            chain.doFilter(request,response);
            return;
        }

        String token = atribute.replace(ATRIBUTE_PREFIX + ' ', "");

        UsernamePasswordAuthenticationToken authenticationToken = this.getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        String user = JWT.require(Algorithm.HMAC512(JWTAutenticationFilter.TOKEN_SENHA))
                .build()
                .verify(token).getSubject();

        if(user == null) {
            return null;
        }
        //org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User) this.userDetailsService.loadUserByUsername(user);

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails, userDetails.getAuthorities());

    }

}
