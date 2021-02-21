package net.okhotnikov.aws_signer.service;

import com.amazonaws.services.cloudfront.AmazonCloudFront;
import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import net.okhotnikov.aws_signer.api.exceptions.BadRequestException;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;

import static net.okhotnikov.aws_signer.config.CloudFrontConfig.*;
import java.security.PrivateKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CloudFrontService {
    private final PrivateKey privateKey;
    private final AmazonCloudFront cloudFront;
    private final Cache<String,String> linksCache;

    public CloudFrontService(PrivateKey privateKey, AmazonCloudFront cloudFront, Cache<String, String> linksCache) {
        this.privateKey = privateKey;
        this.cloudFront = cloudFront;
        this.linksCache = linksCache;
    }

    public String getSignedUrl(String key){
        String res = linksCache.get(key);

        if (res != null){
            return res;
        }

        String resourcePath = SignerUtils.generateResourcePath(PROTOCOL, DEFAULT_DISTRIBUTION, key);
        if(privateKey == null){
            throw new RuntimeException("CloudFront private key non found");
        }

        res = CloudFrontUrlSigner.getSignedURLWithCannedPolicy(
                resourcePath,
                PUBLIC_KEY_ID,
                privateKey,
                getExpirationDate());

        linksCache.put(key,res);
        return res;
    }

    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() +CLOUD_FRONT_LINK_TTL);
    }

    public List<String> getSigned(List<String> list) {
        if (list == null)
            throw new BadRequestException();
        return list.stream().map(this::getSignedUrl).collect(Collectors.toList());
    }
}
