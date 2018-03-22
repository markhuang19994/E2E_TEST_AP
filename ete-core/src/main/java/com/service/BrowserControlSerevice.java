package com.service;

import com.model.TestCase;

/**
 * @author AndyChen
 * @version <ul>
 *          <li>2018/3/22 AndyChen,new
 *          </ul>
 * @since 2018/3/22
 */
public interface BrowserControlSerevice {

    public static String SELENIUM = "Selenium";
    public static String PUPPETEER = "Puppeteer";

    void startTestProcedureWithSelenium(TestCase testCase);

    void startTestProcedure(TestCase testCase, String virtualType);

}
