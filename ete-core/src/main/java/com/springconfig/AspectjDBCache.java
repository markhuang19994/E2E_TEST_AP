package com.springconfig;


import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Use aop to cache Entity from jdbc result,when insert or update db cache will remove
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/13, MarkHuang,new
 * </ul>
 * @since 2018/2/13
 */
@Aspect
@Service
public class AspectjDBCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectjDBCache.class);

    private final
    KeyGenerator keyGenerator;

    @Autowired
    public AspectjDBCache(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Pointcut(value = "" +
            "execution(* com.db.repository.*.save(..)) || " +
            "execution(* com.db.repository.*.clear(..))|| " +
            "execution(* com.db.repository.*.delete*(..))")
    private void dbCacheEvict() {
    }

    @Pointcut(value = "" +
            "execution(* com.db.repository.*.find*(..)) || " +
            "execution(* com.db.repository.*.get*(..))")
    private void dbCache() {
    }

    /**
     * set sql result in to cache and generate key by KeyGenerator
     *
     * @param joinPoint aop point cut method
     * @return Object point cut method return
     * @throws Throwable throw  all exception and error
     * @see KeyGenerator
     */
    @Around(value = "dbCache()")
    public Object setDBCache(ProceedingJoinPoint joinPoint) throws Throwable {
        CacheManager cacheManager = ApplicationContextProvider.getBean("cacheManager", CacheManager.class);
        Cache cache = (Cache) cacheManager.getCache("DBCache").getNativeCache();
        Object generate = keyGenerator.generate(joinPoint.getTarget(),
                ((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getArgs());
        if (cache.get(generate) != null) {
            return cache.get(generate).getValue();
        } else {
            Object proceed = joinPoint.proceed();
            cache.put(new Element(generate, proceed));
            return proceed;
        }
    }

    /**
     * when db insert or update, will clean cache who is relative from changed table
     *
     * @param joinPoint aop point cut method
     */
    @After(value = "dbCacheEvict()")
    public void cleanDBCache(JoinPoint joinPoint) {
        CacheManager cacheManager = ApplicationContextProvider.getBean("cacheManager", CacheManager.class);
        Cache cache = (Cache) cacheManager.getCache("DBCache").getNativeCache();
        List keys = cache.getKeys();
        String signatureName = joinPoint.getSignature().getDeclaringTypeName();
        int dotIndex = signatureName.lastIndexOf(".");
        int repIndex = signatureName.indexOf("Repository");
        String modelName = signatureName.substring(dotIndex + 1, repIndex);
        Boolean isRemove = false;
        for (Object key : keys) {
            if (String.valueOf(key).contains(modelName)) {
                isRemove = true;
                cache.remove(key);
            }
        }
        if (isRemove) {
            LOGGER.debug("remove dbCache from " + modelName);
        }
    }

}

