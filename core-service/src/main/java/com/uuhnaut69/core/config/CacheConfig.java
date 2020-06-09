package com.uuhnaut69.core.config;

import com.github.benmanes.caffeine.jcache.configuration.CaffeineConfiguration;
import com.uuhnaut69.core.domain.model.Category;
import com.uuhnaut69.core.domain.model.Product;
import com.uuhnaut69.core.domain.model.Tag;
import com.uuhnaut69.core.domain.model.User;
import com.uuhnaut69.core.repository.user.UserRepository;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

/**
 * @author uuhnaut
 * @project mall
 */
@EnableCaching
@Configuration
public class CacheConfig {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfig() {
        CaffeineConfiguration caffeineConfiguration = new CaffeineConfiguration();
        caffeineConfiguration.setMaximumSize(OptionalLong.of(500));
        caffeineConfiguration.setExpireAfterWrite(OptionalLong.of(TimeUnit.SECONDS.toNanos(3600)));
        caffeineConfiguration.setStatisticsEnabled(true);
        this.jcacheConfiguration = caffeineConfiguration;
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cacheManager -> {
            createCache(cacheManager, UserRepository.USERS_LOGIN_CACHE);
            createCache(cacheManager, Product.class.getName());
            createCache(cacheManager, Product.class.getName() + ".tags");
            createCache(cacheManager, Product.class.getName() + ".categories");
            createCache(cacheManager, Category.class.getName());
            createCache(cacheManager, Tag.class.getName());
            createCache(cacheManager, User.class.getName());
            createCache(cacheManager, User.class.getName() + ".products");
            createCache(cacheManager, User.class.getName() + ".tags");
        };
    }


    private void createCache(CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }
}
