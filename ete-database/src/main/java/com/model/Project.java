package com.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2018/3/20 AndyChen,new
 * </ul>
 * @since 2018/3/20
 */
@Entity
@Table(name = "E2E_PROJECT")
public class Project implements Serializable {

    @Id
    @Column(name = "PROJECT_NAME")
    private String projectName;

    /**
     * class names must following format who extend PageTestService
     * ex: "PageIndex,PageStep1,...,PageStep5"
     */
    @Column(name = "TEST_CLASS_NAMES")
    private String testClassNames;

    /**
     * url collection must mapping with testClassNames field like:
     * "page/index,page/step1?ar=1,...,page/step5?ar=1"
     * ("PageIndex,PageStep1,...,PageStep5")
     */
    @Column(name = "URL_COLLECTION")
    private String urlCollection;

    @OneToMany(mappedBy = "projectName", fetch = FetchType.EAGER)
    private List<TestCase> testCases;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTestClassNames() {
        return testClassNames;
    }

    public void setTestClassNames(String testClassNames) {
        this.testClassNames = testClassNames;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    public String getUrlCollection() {
        return urlCollection;
    }

    public void setUrlCollection(String urlCollection) {
        this.urlCollection = urlCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return (getProjectName() != null ? getProjectName().equals(project.getProjectName())
                : project.getProjectName() == null) && (getTestClassNames() != null ? getTestClassNames().equals(project.getTestClassNames())
                : project.getTestClassNames() == null) && (getUrlCollection() != null ? getUrlCollection().equals(project.getUrlCollection())
                : project.getUrlCollection() == null) && (getTestCases() != null ? getTestCases().equals(project.getTestCases())
                : project.getTestCases() == null);
    }

    @Override
    public int hashCode() {
        int result = getProjectName() != null ? getProjectName().hashCode() : 0;
        result = 31 * result + (getTestClassNames() != null ? getTestClassNames().hashCode() : 0);
        result = 31 * result + (getUrlCollection() != null ? getUrlCollection().hashCode() : 0);
        result = 31 * result + (getTestCases() != null ? getTestCases().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", testClassNames='" + testClassNames + '\'' +
                ", urlCollection='" + urlCollection + '\'' +
                ", testCases=" + testCases +
                '}';
    }
}
