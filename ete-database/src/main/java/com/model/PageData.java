package com.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2018/3/7 AndyChen,new
 * </ul>
 * @since 2018/3/7
 */
public class PageData implements Serializable {

    private String pageUrl;

    private String dataJsonStr;
    //TODO json format

    /**
     * {
     *  eleId : "",
     *  eleValue:"",
     *  type,
     *  order
     *
     * }
     *
     */

    private ArrayList<JsonData> jsonDatas; //TODO must order data


    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getDataJsonStr() {
        return dataJsonStr;
    }

    public void setDataJsonStr(String dataJsonStr) {
        this.dataJsonStr = dataJsonStr;
    }

    public ArrayList<JsonData> getJsonDatas() {
        return jsonDatas;
    }

    private void setJsonDatas(ArrayList<JsonData> jsonDatas) {
        this.jsonDatas = jsonDatas;
    }
}
