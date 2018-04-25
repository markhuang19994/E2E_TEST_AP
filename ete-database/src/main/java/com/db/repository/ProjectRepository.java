package com.db.repository;

import com.model.Project;
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
public interface ProjectRepository extends JpaRepository<Project, String> {
    @Override
    <S extends Project> S save(S s);

    @Query("select projectName from Project")
    List<String> getProjectName();

    @Modifying
    @Query("delete from Project p  where p.projectName = ?1")
    int deleteAllByProjectName(String projectName);
}
