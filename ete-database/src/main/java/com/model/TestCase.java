package com.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author AndyChen
 * @version <ul>
 * <li>2018/3/8 AndyChen,new
 * </ul>
 * @since 2018/3/8
 */
@Entity
@Table(name = "E2E_TEST_CASE")
public class TestCase {

    @Id
    @Column(name = "TEST_CASE_NAME")
    private String testCaseName;

    @Column(name = "PROJECT_NAME")
    private String projectName;

    @OneToMany(mappedBy = "testCaseName", fetch = FetchType.EAGER)
    private List<PageData> pageDatas;

    @Transient
    private Class[] pageServiceClasses;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public List<PageData> getPageDatas() {
        return pageDatas;
    }

    public void setPageDatas(ArrayList<PageData> pageDatas) throws ClassNotFoundException {
        this.pageDatas = pageDatas;

//        if (this.pageServiceClasses != null)
//            return;
//        List<Class> list = new ArrayList<>();
//        for (PageData pageData : pageDatas) {
//            String className = pageData.getPageServiceClass();
//            Class serviceClass = Class.forName(className);
//            list.add(serviceClass);
//        }
//        this.pageServiceClasses = (Class[]) list.toArray();
    }

    public Class[] getPageServiceClasses() {
        return pageServiceClasses;
    }

    public void setPageServiceClasses(Class[] pageServiceClasses) {
        this.pageServiceClasses = pageServiceClasses;
    }
}
