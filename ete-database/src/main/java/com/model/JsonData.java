package com.model;

import java.io.Serializable;

/**
 * @author AndyChen
 * @version <ul>
 * <li>2018/3/7 AndyChen,new
 * </ul>
 * @since 2018/3/7
 */
public class JsonData implements Serializable {

    public enum DataType{
        TEXT, RADIO, SELECT
    }

    private String id;
    private String value;
    private DataType dataType;
    private String order;
    private String beforeScript;

    //TODO json format

    /**
     * {
     *  eleId : "",
     *  eleValue:"",
     *
     * }
     *
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getBeforeScript() {
        return beforeScript;
    }

    public void setBeforeScript(String beforeScript) {
        this.beforeScript = beforeScript;
    }
}
