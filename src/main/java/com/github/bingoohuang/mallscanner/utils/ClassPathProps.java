package com.github.bingoohuang.mallscanner.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.startsWith;

public class ClassPathProps {
    public static String getConfig(String fileName, String propertyName) {
        try {
            ClassPathResource cpRes = new ClassPathResource(fileName);
            if (!cpRes.exists())
                throw new RuntimeException("unable to find classpath properties file " + fileName);

            Properties props = new Properties();
            props.load(cpRes.getInputStream());
            String property = props.getProperty(propertyName);
            return tryDecrypt(property);
        } catch (IOException e) {
            throw new RuntimeException("unable to load classpath properties file " + fileName, e);
        }
    }

    private static String tryDecrypt(String property) {
        if (StringUtils.isEmpty(property)) return property;
        if (!startsWith(property, "{PBE}")) return property;

        String pbeKey = System.getProperty("PbeKey", "dataplus");
        String prop = property.substring(5);

        return Pbe.decrypt(prop, pbeKey);
    }


}
