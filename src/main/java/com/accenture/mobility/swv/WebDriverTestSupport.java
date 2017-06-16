package com.accenture.mobility.swv;

import com.accenture.mobility.swv.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;


@ContextConfiguration(classes = WebDriverConfig.class, loader = AnnotationConfigContextLoader.class)
public class WebDriverTestSupport extends AbstractTestNGSpringContextTests {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(WebDriverTestSupport.class);

    public WebDriver driver;


    @Parameters(value = {"browserType"})

    @BeforeClass
    public void beforeClass(@Optional(value = "chrome") String browserType) throws Exception {

        driver = (WebDriver) applicationContext.getBean(browserType + "Driver");
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        LOGGER.info("invoked WebDriver is:[{}]",driver.toString());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }


    public void printLink() {
        java.util.List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println(links.size());
        for (WebElement element : links) {
            LOGGER.info(element.getText());
        }
    }

}
