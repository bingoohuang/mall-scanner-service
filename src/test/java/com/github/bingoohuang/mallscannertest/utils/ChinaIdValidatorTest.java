package com.github.bingoohuang.mallscannertest.utils;

import org.junit.Test;

public class ChinaIdValidatorTest {
    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            String s = ChinaIdValidator.randomGenerate();
            System.out.println(s);
            ChinaIdValidator.validate(s);
        }
    }
}
