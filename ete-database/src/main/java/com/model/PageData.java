package com.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class PageData implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(PageData.class);

    private String testCaseName;

    private String pageUrl;

    private String dataJsonStr;

    private List<JsonData> jsonDatas; //TODOed must order data


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
