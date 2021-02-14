package net.okhotnikov.aws_signer.config.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * Created by Sergey Okhotnikov.
 */
@Service
public class SecretAuthenticationProvider implements AuthenticationProvider {

    private final SecretAuthenticationManager secretAuthenticationManager;

    public SecretAuthenticationProvider(SecretAuthenticationManager secretAuthenticationManager) {
        this.secretAuthenticationManager = secretAuthenticationManager;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return secretAuthenticationManager.authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
