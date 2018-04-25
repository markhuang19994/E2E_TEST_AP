DROP TABLE IF EXISTS XCOLA.dbo.E2E_PAGE_DATA;

CREATE TABLE XCOLA.dbo.E2E_PAGE_DATA(
  ID             NUMERIC(18) IDENTITY PRIMARY KEY,
  TEST_CASE_NAME VARCHAR(50)  NOT NULL  REFERENCES E2E_TEST_CASE,
  PAGE_URL       VARCHAR(200) NOT NULL,
  PAGE_SRV_CLASS VARCHAR(20),
  DATA_JSON_STR  VARCHAR(MAX) NOT NULL
);

INSERT INTO XCOLA.dbo.E2E_PAGE_DATA (TEST_CASE_NAME, PAGE_URL, PAGE_SRV_CLASS, DATA_JSON_STR) VALUES ('PCL-Index-page-test01', '/index', null, '[{"id":"ino","value":"A117463321","dataType":"text","beforeScript":"$(''#ino'').val('''');console.log(''123'')"},{"id":"cno","value":"4563180400000002","dataType":"text","beforeScript":"$(''#cno'').val('''');"}]');
INSERT INTO XCOLA.dbo.E2E_PAGE_DATA (TEST_CASE_NAME, PAGE_URL, PAGE_SRV_CLASS, DATA_JSON_STR) VALUES ('PCL-Index-page-test01', '/step1?_ar=1', null, '[{"id":"agr","value":"","dataType":"radio","beforeScript":""},{"id":"otp","value":"111111","dataType":"text","beforeScript":""}]');
INSERT INTO XCOLA.dbo.E2E_PAGE_DATA (TEST_CASE_NAME, PAGE_URL, PAGE_SRV_CLASS, DATA_JSON_STR) VALUES ('PCL-Index-page-test01', '/step2?_ar=1', null, '[{"id":"ftenor","value":"36","dataType":"text","beforeScript":""}]');
INSERT INTO XCOLA.dbo.E2E_PAGE_DATA (TEST_CASE_NAME, PAGE_URL, PAGE_SRV_CLASS, DATA_JSON_STR) VALUES ('PCL-Index-page-test01', '/step3?_ar=1', null, '[{"id":"tuition","value":"","dataType":"radio","beforeScript":""},{"id":"jobNC","value":"","dataType":"radio","beforeScript":""}]');
INSERT INTO XCOLA.dbo.E2E_PAGE_DATA (TEST_CASE_NAME, PAGE_URL, PAGE_SRV_CLASS, DATA_JSON_STR) VALUES ('PCL-Index-page-test01', '/step3-1?_ar=1', null, '[{"id":"agrBox","value":"","dataType":"checkbox","beforeScript":""}]');
INSERT INTO XCOLA.dbo.E2E_PAGE_DATA (TEST_CASE_NAME, PAGE_URL, PAGE_SRV_CLASS, DATA_JSON_STR) VALUES ('PCL-Index-page-test01', '/step4?_ar=1', null, '[{"id":"","value":"","dataType":"text","beforeScript":"$(''input[type=\\\"checkbox\\\"]'').click()"},{"id":"check_year","value":"106","dataType":"select","beforeScript":""},{"id":"check_month","value":"1","dataType":"select","beforeScript":""},{"id":"check_day","value":"1","dataType":"select","beforeScript":""}]');
INSERT INTO XCOLA.dbo.E2E_PAGE_DATA (TEST_CASE_NAME, PAGE_URL, PAGE_SRV_CLASS, DATA_JSON_STR) VALUES ('PCL-Index-page-test01', '/step5?_ar=1', null, '[{"id":"otp","value":"111111","dataType":"text","beforeScript":""}]');
