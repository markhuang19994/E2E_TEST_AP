package com.db.repository;

import com.model.PageData;
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
public interface PageDataRepository extends JpaRepository<PageData, String> {
    List<PageData> findPageDataByTestCaseName(String testCaseName);

    @Modifying
    @Query("delete from PageData p  where p.testCaseName = ?1")
    int deleteAllByTestCaseName(String testCaseName);
}
