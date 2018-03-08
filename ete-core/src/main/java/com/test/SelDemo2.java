package com.test;

import com.driver.WebDriverUtil;
import com.util.DataUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author MarkHuang
 * @version <ul>
 * <li>2018/2/26, MarkHuang,new
 * </ul>
 * @since 2018/2/26
 */
@Lazy
@Service
public class SelDemo2 {
    private final
    WebDriver driver;

    private final
    JavascriptExecutor js;

    @Autowired
    public SelDemo2(WebDriver driver, @Qualifier("Script") JavascriptExecutor js) {
        this.driver = driver;
        this.js = js;
    }

    public void test001() {
        String loginHref = indexPage();
        loginPage(loginHref);
        String projectHref = indexPageWithLogin("Citibank PCL2 for UPL");
        projectPage(projectHref);
    }

    private String indexPage() {
        driver.get("http://macaque:6080/jenkins/");
        WebDriverUtil.loadPage(driver, "http://macaque:6080/jenkins/");
        WebElement login = driver.findElement(By.cssSelector(".login a"));
        return login.getAttribute("href");
    }

    private void loginPage(String loginHref) {
        driver.get(loginHref);
        WebDriverUtil.loadPage(driver, loginHref);
        driver.findElement(By.id("j_username")).sendKeys("markhuang");
        driver.findElement(By.cssSelector("input[type=password]")).sendKeys("markhuang@iisi");
        driver.findElement(By.id("yui-gen1-button")).click();
    }

    private String indexPageWithLogin(String projectName) {
        WebDriverUtil.loadPage(driver, "http://macaque:6080/jenkins/");
        WebElement project = driver.findElement(By.linkText(projectName));
        return project.getAttribute("href");
    }

    private void projectPage(String projectHref) {
        driver.get(projectHref);
        WebDriverUtil.loadPage(driver, projectHref);
        String firstTr = "";
        int trLength = 0;
        List<WebElement> trs = driver.findElements(By.cssSelector(".stripped  tr"));
        trLength = trs.size();
        for (WebElement tr : trs) {
            if (tr.getText().contains("#")) {
                firstTr = tr.getText();
                break;
            }
        }
        driver.findElement(By.linkText("馬上建置")).click();
        String finalFirstTr = firstTr;
        new WebDriverWait(driver, 85, 20).until((ExpectedCondition<Boolean>) (WebDriver driver) -> {
            List<WebElement> trs1 = null;
            if (driver != null) {
                trs1 = driver.findElements(By.cssSelector(".stripped  tr"));
            }
            if (trs1 != null) {
                for (WebElement tr : trs1) {
                    String text;
                    try {
                        text = tr.getText();
                    } catch (Exception e) {
                        continue;
                    }
                    if (text.contains("#")) {
                        if (!finalFirstTr.equals(text)) {
                            try {
                                return tr.findElement(By.cssSelector("img")).getAttribute("alt").contains("成功");
                            } catch (Exception e) {
                                //The dom is always reload but frequency is not height so if element get error we try catch
                                //exception and get element second time immediately
                            }
                        }
                        break;
                    }
                }
            }
            return false;
        });

        WebDriverUtil.sleep(3);
        driver.navigate().refresh();
        WebDriverUtil.loadPage(driver, projectHref);
        WebElement fileList = driver.findElement(By.className("fileList"));
        String cbol = fileList.findElement(By.partialLinkText("CBOL.zip")).getAttribute("href");
        String d = fileList.findElement(By.partialLinkText("D.zip")).getAttribute("href");
        String war = fileList.findElement(By.partialLinkText("war")).getAttribute("href");
        System.err.println("File url already get!");
        WebDriverUtil.sleep(1);
        driver.navigate().to(cbol);
        WebDriverUtil.sleep(1);
        driver.navigate().to(d);
        WebDriverUtil.sleep(1);
        driver.navigate().to(war);
    }


    private void genZipFile(){
        File file = new File(System.getProperty("user.home")
                + File.separator + "Desktop" + File.separator + "chrome download");
//        DataUtil.createZipFile();
    }


}
