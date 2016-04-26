package com.github.bingoohuang.mallscannertest.tests;

import com.github.bingoohuang.mallscanner.Application;
import com.github.bingoohuang.mallscannertest.tests.cases.BarcodeApiTest;
import com.github.bingoohuang.mallscannertest.tests.cases.ChinaIdApiTest;
import com.mashape.unirest.http.Unirest;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BarcodeApiTest.class,
        ChinaIdApiTest.class
})
public class ScannerTestSuite {
    @ClassRule
    public static ExternalResource testRule = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            Application.main(new String[0]);

        }

        @Override
        protected void after() {
            try {
                Unirest.shutdown();
            } catch (Exception e) {
            }
            Application.shutdown();
        }
    };
}
