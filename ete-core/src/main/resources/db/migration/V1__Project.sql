DROP TABLE IF EXISTS XCOLA.dbo.E2E_PROJECT;

CREATE TABLE XCOLA.dbo.E2E_PROJECT (
  PROJECT_NAME     VARCHAR(30)  NOT NULL PRIMARY KEY,
  TEST_CLASS_NAMES VARCHAR(500) NOT NULL,
  URL_COLLECTION   VARCHAR(200) NOT NULL
);

INSERT INTO XCOLA.dbo.E2E_PROJECT (PROJECT_NAME, TEST_CLASS_NAMES, URL_COLLECTION) VALUES ('PCL', 'com.project.pcl2.PageIndex,com.project.pcl2.PageStep1,com.project.pcl2.PageStep2,com.project.pcl2.PageStep3,com.project.pcl2.PageStep3-1,com.project.pcl2.PageStep4,com.project.pcl2.PageStep5', '/index,/step1?_ar=1,/step2?_ar=1,/step3?_ar=1,/step3-1?_ar=1,/step4?_ar=1,/step5?_ar=1');
