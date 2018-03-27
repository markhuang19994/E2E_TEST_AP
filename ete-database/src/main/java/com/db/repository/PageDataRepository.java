package com.db.repository;

import com.model.PageData;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/3/21, MarkHuang,new
 * </ul>
 * @since 2018/3/21
 */
@Cacheable(value = "DBCache", keyGenerator = "keyGenerator")
@Repository
public interface PageDataRepository extends JpaRepository<PageData, String> {
    @Override
    @Transactional
    <S extends PageData> S save(S s);

    @Override
    List<PageData> findAll();

    @Override
    List<PageData> findAll(Sort sort);

    @Override
    List<PageData> findAll(Iterable<String> iterable);

    @Override
    <S extends PageData> List<S> save(Iterable<S> iterable);

    @Override
    void flush();

    @Override
    <S extends PageData> S saveAndFlush(S s);

    @Override
    void deleteInBatch(Iterable<PageData> iterable);

    @Override
    void deleteAllInBatch();

    @Override
    PageData getOne(String s);

    @Override
    <S extends PageData> List<S> findAll(Example<S> example);

    @Override
    <S extends PageData> List<S> findAll(Example<S> example, Sort sort);

    @Override
    Page<PageData> findAll(Pageable pageable);

    @Override
    PageData findOne(String s);

    @Override
    boolean exists(String s);

    @Override
    long count();

    @Override
    void delete(String s);

    @Override
    void delete(PageData pageData);

    @Override
    void delete(Iterable<? extends PageData> iterable);

    @Override
    void deleteAll();

    @Override
    <S extends PageData> S findOne(Example<S> example);

    @Override
    <S extends PageData> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends PageData> long count(Example<S> example);

    @Override
    <S extends PageData> boolean exists(Example<S> example);

    List<PageData> findPageDataByTestCaseName(String testCaseName);
}
