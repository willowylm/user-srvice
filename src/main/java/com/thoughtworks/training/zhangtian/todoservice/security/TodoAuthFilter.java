package com.thoughtworks.training.zhangtian.todoservice.security;

import com.google.common.net.HttpHeaders;
import com.thoughtworks.training.zhangtian.todoservice.model.User;
import com.thoughtworks.training.zhangtian.todoservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.RequestContext;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class TodoAuthFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;

//    @Value("${private.password}")
//    private String privatePassword;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(request);
        if (!StringUtils.isEmpty(token)) {

            User user = analyzeToken(token);
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(user,
                                null,
                                Collections.emptyList())
                );
        }

        filterChain.doFilter(request, response);
    }

    private User analyzeToken(String token) {

        String[] tokens = token.split(":");
        User user = new User();

        user.setName(tokens[1]);
        user.setId(Integer.valueOf(tokens[0]));
        return user;

    }
}
