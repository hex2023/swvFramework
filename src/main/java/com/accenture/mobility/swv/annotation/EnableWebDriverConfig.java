package com.accenture.mobility.swv.annotation;

import com.accenture.mobility.swv.config.SWVConfig;
import com.accenture.mobility.swv.config.WebDriverConfig;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(value = WebDriverConfig.class)
public @interface EnableWebDriverConfig {
    String value() default "";

}
