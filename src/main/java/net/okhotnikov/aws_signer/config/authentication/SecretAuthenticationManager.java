package net.okhotnikov.aws_signer.config.authentication;

import net.okhotnikov.aws_signer.api.exceptions.UnauthorizedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * Created by Sergey Okhotnikov.
 */
@Service
public class SecretAuthenticationManager implements AuthenticationManager {

    private final String secret = System.getenv("APP_SECRET");

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(! (authentication instanceof SecretAuthentication))
            throw  new UnauthorizedException("wrong authentication type");
        SecretAuthentication secretAuthentication = (SecretAuthentication) authentication;

        if (! secret.equals(secretAuthentication.token)){
            throw new UnauthorizedException("wrong secret");
        }

        return new SecretAuthentication(secretAuthentication.token);
    }

}
