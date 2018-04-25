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

    @Transient
    private String hostUrl;

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

    public void setPageDatas(ArrayList<PageData> pageDatas) {
        this.pageDatas = pageDatas;
//
//        if (this.pageServiceClasses != null)
//            return;
//        List<Class> list = new ArrayList<>();
//        for (PageData pageData : pageDatas) {
//            String classPrefix = "com.project.pcl2.Page";
//            String pageUrl = pageData.getPageUrl();
//            Class serviceClass = null;
//            try {
//                pageUrl = pageUrl.contains("index") ? "Index" :
//                        pageUrl.substring(pageUrl.lastIndexOf("step"), pageUrl.lastIndexOf("?"))
//                                .replace("step", "Step").replaceAll("-", "_");
//                serviceClass = Class.forName(classPrefix + pageUrl);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            list.add(serviceClass);
//        }
//        this.pageServiceClasses = list.toArray(new Class[list.size()]);
    }

    public Class[] getPageServiceClasses() {
        return pageServiceClasses;
    }

    public void setPageServiceClasses(Class[] pageServiceClasses) {
        this.pageServiceClasses = pageServiceClasses;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }
}
