package com.springconfig;


import net.sf.ehcache.Cache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.cache.CacheManager;
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

    @Pointcut(value = "execution(* com.db.repository.*.save(..)) || execution(* com.db.repository.*.clear(..))")
    private void cache() {
    }

    //
//    @Before(value = "cache()")
//    public void nothing()  {
//        EhCacheCacheManager cacheManager = ApplicationContextProvider.getBean("cacheManager", EhCacheCacheManager.class);
//        cacheManager.getCache("DBCache").clear();
//        Cache cache =(Cache) cacheManager.getCache("DBCache").getNativeCache();
//        cache.getKeys().forEach(System.out::println);
//    }


    @After(value = "cache()")
    public void cleanDBCache(JoinPoint joinPoint) {
        CacheManager cacheManager = ApplicationContextProvider.getBean("cacheManager", CacheManager.class);
        Cache cache = (Cache) cacheManager.getCache("DBCache").getNativeCache();
        List keys = cache.getKeys();
        String simpleName = joinPoint.getTarget().getClass().getSimpleName();
        String signatureName = joinPoint.getSignature().getDeclaringTypeName();
        int dotIndex = signatureName.lastIndexOf(".");
        int repIndex = signatureName.indexOf("Repository");
        String modelName = signatureName.substring(dotIndex + 1, repIndex);
        for (Object key : keys) {
            if (String.valueOf(key).contains(simpleName)) {
                LOGGER.debug("remove cache from " + modelName);
                cache.remove(key);
            }
        }
    }

//    @Around(value = "cache()")
//    public Object nothing(ProceedingJoinPoint joinPoint) throws Throwable {
//        return joinPoint.proceed();
//    }


}

