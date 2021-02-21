package net.okhotnikov.aws_signer.config;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

import static net.okhotnikov.aws_signer.config.CloudFrontConfig.CLOUD_FRONT_LINK_TTL;


/**
 * Created by Sergey Okhotnikov.
 */
@Configuration
public class EhCacheConfig {

    public static final String LINKS_CACHE = "links";
    public static final int TTL_SAFE_PERIOD = Integer.parseInt(System.getenv("TTL_SAFE_PERIOD"));

    @Bean
    public CacheManager ehCacheManager() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache(LINKS_CACHE,
                                CacheConfigurationBuilder
                                .newCacheConfigurationBuilder(
                                        String.class,
                                        String.class,
                                        ResourcePoolsBuilder.heap(1000)
                                )
                                        .withExpiry(ExpiryPolicyBuilder
                                                .timeToLiveExpiration(
                                                        Duration.ofMillis(CLOUD_FRONT_LINK_TTL - TTL_SAFE_PERIOD))
                                        )
                )
                .build();

        cacheManager.init();

        return cacheManager;
    }


}


