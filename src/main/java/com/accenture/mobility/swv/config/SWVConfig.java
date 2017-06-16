package com.accenture.mobility.swv.config;

import com.accenture.mobility.swv.sikuli.CustomScreen;
import org.sikuli.script.App;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

@Configuration
@PropertySource(value = {"classpath:${target.scenario.name:default}/${target.scenario.name:default}.properties",
        "classpath:${target.scenario.name:default}/${spring.profiles.active:default}.properties", "classpath:swv.properties"}, ignoreResourceNotFound = true)
public class SWVConfig implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SWVConfig.class);
    @Autowired
    private Environment env;

    @Autowired
    private ApplicationContext context;

    private String targetDevice;

    private String targeScerario;

    private String appPath;

    private String osType;

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public String getTargeScerario() {
        return targeScerario;
    }

    public void setTargeScerario(String targeScerario) {
        this.targeScerario = targeScerario;
    }

    public String getTargetDevice() {
        return targetDevice;
    }

    public void setTargetDevice(String targetDevice) {
        this.targetDevice = targetDevice;
    }

    @Bean(name = "propertySourcesPlaceholderConfigurer")
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
//        configurer.setIgnoreResourceNotFound(true);
//        configurer.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath:swv-mac.properties"));
        return configurer;

    }


    public void afterPropertiesSet() throws Exception {
        if (env.getProperty("app.path") == null) {
            LOGGER.error("program path is not defined!!");
        } else {
            setAppPath(env.getProperty("app.path"));
        }

        if (env.getActiveProfiles().length == 0) {
            LOGGER.warn("active Profile does not exist!!! Please set JVM argument like -Dspring.profiles.active=[device folder name]");
            LOGGER.warn("default Profile is activated!!!");
            setTargetDevice("default");
        } else {


            setTargetDevice(env.getActiveProfiles()[0]);
        }

        setTargeScerario(System.getProperty("target.scenario.name"));
        if (getTargeScerario() == null) {
            LOGGER.warn("active scenario does not exist!!! Please set JVM argument like -Dtarget.scenario.name=[scenario folder name]");
            LOGGER.warn("default scenario is activated!!!");
            setTargeScerario("default");
        }
        setOsType(System.getProperty("os.name").toLowerCase());

        System.out.println("######################## ACN Mobility SWV INFO #############################");
        System.out.println("  + OS:             [" + getOsType() + "]");
        System.out.println("  + target Scenario:[" + getTargeScerario() + "]");
        System.out.println("  + target device:  [" + getTargetDevice() + "]");
        System.out.println("############################################################################");
    }
}
