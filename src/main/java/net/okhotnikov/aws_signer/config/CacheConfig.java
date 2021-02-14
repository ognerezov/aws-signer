package net.okhotnikov.aws_signer.config;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static net.okhotnikov.aws_signer.config.EhCacheConfig.LINKS_CACHE;

@Configuration
public class CacheConfig {

    private final CacheManager cacheManager;

    public CacheConfig(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Bean
    public Cache<String,String> linksCache(){
        return cacheManager.getCache(LINKS_CACHE, String.class, String.class);
    }
}
