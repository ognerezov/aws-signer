package net.okhotnikov.aws_signer.service;

import com.amazonaws.services.cloudfront.AmazonCloudFront;
import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import org.springframework.stereotype.Service;

import static net.okhotnikov.aws_signer.config.CloudFrontConfig.*;
import java.security.PrivateKey;
import java.util.Date;

@Service
public class CloudFrontService {
    private final PrivateKey privateKey;
    private final AmazonCloudFront cloudFront;

    public CloudFrontService(PrivateKey privateKey, AmazonCloudFront cloudFront) {
        this.privateKey = privateKey;
        this.cloudFront = cloudFront;
    }

    public String getSignedUrl(String key){
        String distribution = "d13sgzrfb1x6jg.cloudfront.net";
        String resourcePath = SignerUtils.generateResourcePath(PROTOCOL, distribution, key);
        if(privateKey == null){
            throw new RuntimeException("CloudFront private key non found");
        }
        return CloudFrontUrlSigner.getSignedURLWithCannedPolicy(
                resourcePath,
                cloudFrontKeyPairId,
                privateKey,
                getExpirationDate());
    }

    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() +CLOUD_FRONT_LINK_TTL);
    }
}
