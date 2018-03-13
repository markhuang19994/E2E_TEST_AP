package com.model;

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
 * <li>2018/3/7 AndyChen,new
 * </ul>
 * @since 2018/3/7
 */
public class PageData implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(PageData.class);

    private String pageUrl;

    private String dataJsonStr;

    private ArrayList<JsonData> jsonDatas; //TODOed must order data


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
        JsonDatas datas = null;
        //TODO 解析
        ObjectMapper mapper = new ObjectMapper();
        try {
//            List<JsonData> jsonDatas = DataUtil.parseJsonToObjectList(dataJsonStr, JsonData.class);
            datas = mapper.readValue(dataJsonStr, JsonDatas.class);
            List<JsonData> jsonDatas = datas.getDatas();
            Collections.sort(jsonDatas, new Comparator<JsonData>() {
                @Override
                public int compare(JsonData o1, JsonData o2) {
                    int oint1 = -1;
                    int oint2 = -1;
                    try {
                        oint1 = Integer.parseInt(o1.getOrder());
                        oint2 = Integer.parseInt(o2.getOrder());
                    } catch (NumberFormatException e) {
                        if(oint1 == -1)
                            return -1;
                        return 1;
                    }
                    return oint1 - oint2;
                }
            });
            ArrayList<JsonData> resultDatas = new ArrayList<>();
            resultDatas.addAll(jsonDatas);
            this.setJsonDatas(resultDatas);
        } catch (IOException e) {
            logger.debug("");
        }
    }

    public ArrayList<JsonData> getJsonDatas() {
        return jsonDatas;
    }

    private void setJsonDatas(ArrayList<JsonData> jsonDatas) {
        this.jsonDatas = jsonDatas;
    }

    private static class JsonDatas {

        private List<JsonData> datas;

        public List<JsonData> getDatas() {
            return datas;
        }

        public void setDatas(List<JsonData> datas) {
            this.datas = datas;
        }
    }

}
