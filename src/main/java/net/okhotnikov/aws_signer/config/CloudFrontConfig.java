package net.okhotnikov.aws_signer.config;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.PEM;
import com.amazonaws.services.cloudfront.AmazonCloudFront;
import com.amazonaws.services.cloudfront.AmazonCloudFrontClientBuilder;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;

import java.security.PrivateKey;

@Configuration
public class CloudFrontConfig {
    public static final String PUBLIC_KEY = System.getenv("PUBLIC_KEY");
    public static final String DEFAULT_DISTRIBUTION = System.getenv("DISTRIBUTION");
    public static final int CLOUD_FRONT_LINK_TTL = Integer.parseInt(System.getenv("LINK_TTL"));
    private static PrivateKey privateKey;
    public static final SignerUtils.Protocol PROTOCOL = SignerUtils.Protocol.https;

    static {
        try {
            String s = System.getenv("PRIVATE_KEY");
            privateKey = PEM.readPrivateKey(new ByteArrayInputStream(s.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    @Bean
    public AmazonCloudFront getCloudFrontClient(){
        String region = System.getenv("AWS_DEFAULT_REGION");
        return AmazonCloudFrontClientBuilder.standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(region)
                .build();
    }
}
