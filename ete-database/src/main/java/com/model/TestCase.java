package com.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
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
public class TestCase implements Serializable {

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

    public void setPageDatas(List<PageData> pageDatas) {
        this.pageDatas = pageDatas;
    }

    public Class[] getPageServiceClasses() {
        return pageServiceClasses;
    }

    public void setPageServiceClasses(Class[] pageServiceClasses) {
        this.pageServiceClasses = pageServiceClasses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestCase)) return false;
        TestCase testCase = (TestCase) o;
        if (getTestCaseName() != null ? !getTestCaseName().equals(testCase.getTestCaseName())
                : testCase.getTestCaseName() != null) return false;
        if (getProjectName() != null ? !getProjectName().equals(testCase.getProjectName())
                : testCase.getProjectName() != null) return false;
        if (getPageDatas() != null ? !getPageDatas().equals(testCase.getPageDatas())
                : testCase.getPageDatas() != null) return false;
        Class[] thisPageServiceClasses = getPageServiceClasses();
        Class[] otherPageServiceClasses = testCase.getPageServiceClasses();
        if (thisPageServiceClasses == null || otherPageServiceClasses == null) {
            return thisPageServiceClasses == otherPageServiceClasses;
        }
        if (thisPageServiceClasses.length != otherPageServiceClasses.length) return false;
        for (int i = 0; i < thisPageServiceClasses.length; i++)
            if (thisPageServiceClasses[i] != otherPageServiceClasses[i]) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getTestCaseName() != null ? getTestCaseName().hashCode() : 0;
        result = 31 * result + (getProjectName() != null ? getProjectName().hashCode() : 0);
        result = 31 * result + (getPageDatas() != null ? getPageDatas().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getPageServiceClasses());
        return result;
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "testCaseName='" + testCaseName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", pageDatas=" + pageDatas +
                ", pageServiceClasses=" + Arrays.toString(pageServiceClasses) +
                '}';
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }
}
