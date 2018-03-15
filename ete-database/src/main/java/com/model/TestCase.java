package com.model;

import java.util.ArrayList;

/**
 * @author AndyChen
 * @version <ul>
 *          <li>2018/3/8 AndyChen,new
 *          </ul>
 * @since 2018/3/8
 */
public class TestCase {

    private ArrayList<PageData> pageDatas;

    private Class[] pageServiceClasses;

    public ArrayList<PageData> getPageDatas() {
        return pageDatas;
    }

    public void setPageDatas(ArrayList<PageData> pageDatas) {
        this.pageDatas = pageDatas;
    }

    public Class[] getPageServiceClasses() {
        return pageServiceClasses;
    }

    public void setPageServiceClasses(Class[] pageServiceClasses) {
        this.pageServiceClasses = pageServiceClasses;
    }
}
