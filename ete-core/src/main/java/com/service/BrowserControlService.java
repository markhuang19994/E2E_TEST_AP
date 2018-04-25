package com.service;

import com.model.TestCase;

/**
 * @author AndyChen
 * @version <ul>
 *          <li>2018/3/22 AndyChen,new
 *          </ul>
 * @since 2018/3/22
 */
public interface BrowserControlService {

    public static final String SELENIUM = "Selenium";
    public static final String PUPPETEER = "Puppeteer";

    void startTestProcedureWithSelenium(TestCase testCase, String hostUrl);

    void startTestProcedure(TestCase testCase, String virtualType, String hostUrl);

}
