package com.accenture.mobility.swv.config;

import com.accenture.mobility.swv.sikuli.CustomScreen;
import org.sikuli.script.App;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = SWVConfig.class)
public class SikuliConfig implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SikuliConfig.class);


    @Autowired
    private SWVConfig swvConfig;

    @Bean
    @DependsOn(value = {"propertySourcesPlaceholderConfigurer"})
    public App customApp() {
        return new App(swvConfig.getAppPath());
    }

    @Bean
    @DependsOn(value = {"propertySourcesPlaceholderConfigurer"})
    public Screen customScreen() {
        return new CustomScreen("src/main/resources/" + swvConfig.getTargeScerario() + "/" + swvConfig.getTargetDevice() + "/");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("App path :[{}] is used!",swvConfig.getAppPath());

    }
}
