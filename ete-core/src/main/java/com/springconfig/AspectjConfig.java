package com.springconfig;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
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


    @Pointcut(value = "execution(* db.dao.impl.*.insert(..)) || execution(* db.dao.impl.*.update(..))")
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
        EhCacheCacheManager cacheManager = ApplicationContextProvider.getBean("cacheManager", EhCacheCacheManager.class);
        Cache cache = (Cache) cacheManager.getCache("DBCache").getNativeCache();
        List<String> keys = cache.getKeys();
        for (String key : keys) {
            if(key.contains(joinPoint.getArgs()[0].getClass().getSimpleName())){
                System.err.println("REMOVE CACHE"+joinPoint.getArgs()[0].getClass().getSimpleName());
                cache.remove(key);
            }
        }
    }

//    @Around(value = "cache()")
//    public Object nothing(ProceedingJoinPoint joinPoint) throws Throwable {
//        return joinPoint.proceed();
//    }


}

