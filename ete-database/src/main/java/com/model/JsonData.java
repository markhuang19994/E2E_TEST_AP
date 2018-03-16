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

    public static final String RADIO = "radio";
    public static final String TEXT = "text";
    public static final String SELECT = "select";
    public static final String CHECKBOX = "checkbox";

    private String id;
    private String value;
    private String dataType;
    private String beforeScript;

    //TODO json format

    /**
     * {
     * eleId : "",
     * eleValue:"",
     * <p>
     * }
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getBeforeScript() {
        return beforeScript;
    }

    public void setBeforeScript(String beforeScript) {
        this.beforeScript = beforeScript;
    }
}
