package com.accenture.mobility.swv.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

import java.io.File;
import java.net.URL;
import java.text.MessageFormat;

@Configuration
@Import(value = SWVConfig.class)
public class WebDriverConfig {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(WebDriverConfig.class);

    @Autowired
    public SWVConfig swvConfig;

    @Autowired
    public Environment env;

    @Bean(name = "chromeDriver")
    @Lazy
    public WebDriver chromeDriver() throws Exception {
        System.setProperty("webdriver.chrome.driver", new File(getWebDriverChromePath()).getAbsolutePath());
        return new ChromeDriver();
    }

    @Bean(name = "firefoxDriver")
    @Lazy
    public WebDriver firefoxDriver() throws Exception {
        return new FirefoxDriver();
    }


    @Bean(name = "ieDriver")
    @Lazy
    public WebDriver ieDriver() throws Exception {

        System.setProperty("webdriver.ie.driver", new File(env.getProperty("webdriver.ie.win.path")).getAbsolutePath());
        return new ChromeDriver();
    }


    @Bean(name = "iosDriver")
    @Lazy
    public WebDriver iosDriver() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "iPhone Simulator");
//        capabilities.setCapability("deviceName", "iPhone");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "7.1");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
        return new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"),
                capabilities);

    }

    @Bean(name = "androidDriver")
    @Lazy
    public WebDriver androidDriver() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "4.4");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chromium");
        return new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"),
                capabilities);

    }

    @Bean(name = "saucelabDriver")
    @Lazy
    public WebDriver sauceLabDriver() throws Exception {
        String sauceUserName = "hex2023";
        String sauceAccessKey = "abd03339-3211-4407-93be-98b70f3272bb";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "iPhone Simulator");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "7.1");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
        capabilities.setCapability("app", "safari");
        return new RemoteWebDriver(new URL(MessageFormat.format("http://{0}:{1}@ondemand.saucelabs.com:80/wd/hub", sauceUserName, sauceAccessKey)),
                capabilities);
    }


    protected String getWebDriverChromePath() throws Exception {
        String retPath = null;
        if (swvConfig.getOsType().contains("win")) {
            retPath = env.getProperty("webdriver.chrome.win.path");
        } else if (swvConfig.getOsType().contains("mac")) {
            retPath = env.getProperty("webdriver.chrome.mac.path");
        } else if (swvConfig.getOsType().contains("nix") || swvConfig.getOsType().contains("nux") || swvConfig.getOsType().contains("aix")) {
            retPath = env.getProperty("webdriver.chrome.linux.path");
        } else if (swvConfig.getOsType().contains("sunos")) {
            retPath = env.getProperty("webdriver.chrome.sunos.path");
        }
        if (retPath == null) {
            throw new Exception("chrome webDriver is not detected!!");
        }
        return retPath;
    }

}
