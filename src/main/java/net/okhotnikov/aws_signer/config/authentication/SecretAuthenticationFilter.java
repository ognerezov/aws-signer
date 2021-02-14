package net.okhotnikov.aws_signer.config.authentication;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Sergey Okhotnikov.
 */
@Configuration
public class SecretAuthenticationFilter implements Filter {

    public static final String AUTHORIZATION = "Authorization";
    private final SecretAuthenticationProvider secretAuthenticationProvider;

    protected SecretAuthenticationFilter(SecretAuthenticationProvider secretAuthenticationProvider) {
        this.secretAuthenticationProvider = secretAuthenticationProvider;
    }
    private void returnUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.getOutputStream().print(message);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;

        String path = request.getServletPath();
        if(!path.matches("^/sign.*")){
            chain.doFilter(req,res);
            return;
        }

        String token = getToken(request);
        if(token == null){
            returnUnauthorizedResponse(response, "secret is null");
            return;
        }

        try {
            Authentication authentication = secretAuthenticationProvider.authenticate(new SecretAuthentication(token));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req,res);
        } catch (Exception e){
            returnUnauthorizedResponse(response,e.getMessage());
        }
    }

    private String getToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION);
    }
}
