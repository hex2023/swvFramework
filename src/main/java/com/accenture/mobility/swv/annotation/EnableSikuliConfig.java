package com.accenture.mobility.swv.annotation;

import com.accenture.mobility.swv.config.SWVConfig;
import com.accenture.mobility.swv.config.SikuliConfig;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(value = SikuliConfig.class)
public @interface EnableSikuliConfig {
    String value() default "";

}
