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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonData)) return false;
        JsonData jsonData = (JsonData) o;
        return (getId() != null ? getId().equals(jsonData.getId())
                : jsonData.getId() == null) && (getValue() != null ? getValue().equals(jsonData.getValue())
                : jsonData.getValue() == null) && (getDataType() != null ? getDataType().equals(jsonData.getDataType())
                : jsonData.getDataType() == null) && (getBeforeScript() != null ? getBeforeScript().equals(jsonData.getBeforeScript())
                : jsonData.getBeforeScript() == null);
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        result = 31 * result + (getDataType() != null ? getDataType().hashCode() : 0);
        result = 31 * result + (getBeforeScript() != null ? getBeforeScript().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JsonData{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", dataType='" + dataType + '\'' +
                ", beforeScript='" + beforeScript + '\'' +
                '}';
    }
}
