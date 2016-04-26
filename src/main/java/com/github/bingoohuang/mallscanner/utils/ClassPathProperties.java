package com.github.bingoohuang.mallscanner.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

public class ClassPathProperties {
    public static String getConfig(String fileName, String propertyName) {
        try {
            ClassPathResource cpRes = new ClassPathResource(fileName);
            if (!cpRes.exists()) {
                throw new RuntimeException("unable to find classpath properties file " + fileName);
            }

                Properties properties = new Properties();
                properties.load(cpRes.getInputStream());
                return properties.getProperty(propertyName);
        } catch (IOException e) {
            throw new RuntimeException("unable to load classpath properties file " + fileName, e);
        }
    }
}
