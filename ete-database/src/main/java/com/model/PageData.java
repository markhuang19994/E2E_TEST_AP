package com.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * @author AndyChen
 * @version <ul>
 *          <li>2018/3/7 AndyChen,new
 *          </ul>
 * @since 2018/3/7
 */
@Entity
@Table(name="E2E_PAGE_DATA")
public class PageData implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(PageData.class);

    @Id
    private String oid;

    @Column(name = "TEST_CASE_NAME", nullable = false)
    private String testCaseName;

    @Column(name = "PAGE_URL" , nullable = false)
    private String pageUrl;

    @Column(name = "PAGE_SRV_CLASS" , nullable = false)
    private String pageServiceClass;

    @Column(name = "DATA_JSON_STR" , nullable = false)
    private String dataJsonStr;

    @Transient
    private List<JsonData> jsonDatas; //TODOed must order data


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageServiceClass() {
        return pageServiceClass;
    }

    public void setPageServiceClass(String pageServiceClass) {
        this.pageServiceClass = pageServiceClass;
    }

    public String getDataJsonStr() {
        return dataJsonStr;
    }

    public void setDataJsonStr(String dataJsonStr) throws IOException {
        this.dataJsonStr = dataJsonStr;
        if (this.jsonDatas != null)
            return;
        //TODOed 解析
        ObjectMapper mapper = new ObjectMapper();
        List<JsonData> jsonDatas = mapper.readValue(dataJsonStr, new TypeReference<List<JsonData>>() {});
        ArrayList<JsonData> resultDatas = new ArrayList<>();
        resultDatas.addAll(jsonDatas);
        this.jsonDatas = resultDatas;
    }

    public List<JsonData> getJsonDatas() {
        return jsonDatas;
    }

    private void setJsonDatas(ArrayList<JsonData> jsonDatas) throws IOException {
        this.jsonDatas = jsonDatas;
        if (this.dataJsonStr != null)
            return;
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(jsonDatas);
        this.dataJsonStr = jsonString;
    }

}
