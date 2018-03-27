package com.db.repository;

import com.model.TestCase;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
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
@Cacheable(value = "DBCache")
@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, String> {
    @Override
    @Transactional
    <S extends TestCase> S save(S s);

    List<TestCase> findAllByProjectName(String projectName);
}
