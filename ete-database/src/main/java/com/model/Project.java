package com.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author AndyChen
 * @version <ul>
 *          <li>2018/3/20 AndyChen,new
 *          </ul>
 * @since 2018/3/20
 */
@Entity
@Table(name="E2E_PROJECT")
public class Project implements Serializable {

    @Id
    @Column(name = "PROJECT_NAME")
    private String projectName;

    @Column(name = "TEST_CLASS_NAMES")
    private String testClassNames;

    @Column(name = "URL_COLLECTION")
    private String urlCollection;

    @OneToMany(mappedBy = "testCaseName", fetch=FetchType.EAGER)
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
}
