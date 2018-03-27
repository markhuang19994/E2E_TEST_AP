package com.db.repository;

import com.model.PageData;
import com.model.Project;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/3/21, MarkHuang,new
 * </ul>
 * @since 2018/3/21
 */
@Cacheable(value = "DBCache")
@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    @Override
    @Transactional
    <S extends Project> S save(S s);
}
