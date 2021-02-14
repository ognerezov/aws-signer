package net.okhotnikov.aws_signer.config.authentication;


/**
 * Created by Sergey Okhotnikov.
 */
public class SecretAuthentication extends BaseAuthentication {

    protected final String token;

    public SecretAuthentication(String token) {
        super(true);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
