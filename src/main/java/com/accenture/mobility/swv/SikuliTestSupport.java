package com.accenture.mobility.swv;

import com.accenture.mobility.swv.config.SikuliConfig;
import org.sikuli.script.App;
import org.sikuli.script.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterTest;

@ContextConfiguration(classes = SikuliConfig.class, loader = AnnotationConfigContextLoader.class)
public class SikuliTestSupport extends AbstractTestNGSpringContextTests {

    @Autowired
    public Screen screen;

    @Autowired
    public Environment env;

    @Autowired
    public App app;


    @AfterTest
    public void after() {

        if (app != null) {
            app.close();
        }
    }

}
