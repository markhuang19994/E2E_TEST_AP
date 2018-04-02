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
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/13, MarkHuang,new
 * </ul>
 * @since 2018/2/13
 */
@Aspect
@Service
public class AspectjConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectjConfig.class);

    private final
    KeyGenerator keyGenerator;

    @Autowired
    public AspectjConfig(KeyGenerator keyGenerator) {
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

    @Around(value = "dbCache()")
    public Object setDBCache(ProceedingJoinPoint joinPoint) throws Throwable {
        CacheManager cacheManager = ApplicationContextProvider.getBean("cacheManager", CacheManager.class);
        Cache cache = (Cache) cacheManager.getCache("DBCache").getNativeCache();
        Object generate = keyGenerator.generate(joinPoint.getTarget(),
                ((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getArgs());
        //FIXME 這邊需要有這一行隔開 否則會產生bug...
        if (cache.get(generate) != null) {
            return cache.get(generate).getValue();
        } else {
            Object proceed = joinPoint.proceed();
            cache.put(new Element(generate, proceed));
            return proceed;
        }
    }

    @After(value = "dbCacheEvict()")
    public void cleanDBCache(JoinPoint joinPoint) {
        CacheManager cacheManager = ApplicationContextProvider.getBean("cacheManager", CacheManager.class);
        Cache cache = (Cache) cacheManager.getCache("DBCache").getNativeCache();
        List keys = cache.getKeys();
        String signatureName = joinPoint.getSignature().getDeclaringTypeName();
        int dotIndex = signatureName.lastIndexOf(".");
        int repIndex = signatureName.indexOf("Repository");
        String modelName = signatureName.substring(dotIndex + 1, repIndex);
        for (Object key : keys) {
            if (String.valueOf(key).contains(modelName)) {
                LOGGER.debug("remove dbCache from " + modelName);
                cache.remove(key);
            }
        }
    }

//    @Around(value = "dbCacheEvict()")
//    public Object nothing(ProceedingJoinPoint joinPoint) throws Throwable {
//        return joinPoint.proceed();
//    }


}

