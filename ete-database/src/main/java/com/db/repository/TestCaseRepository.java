package com.db.repository;

import com.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
@Repository
@Transactional
public interface TestCaseRepository extends JpaRepository<TestCase, String> {
    @Override
    <S extends TestCase> S save(S s);

    List<TestCase> findAllByProjectName(String projectName);

    @Modifying
    @Query("delete from TestCase t  where t.projectName = ?1")
    int deleteAllByProjectName(String projectName);
}
