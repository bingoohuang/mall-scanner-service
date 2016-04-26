package com.github.bingoohuang.mallscannertest.tests.cases;

import com.github.bingoohuang.mallscannertest.apibeans.Barcode;
import com.github.bingoohuang.mallscannertest.api.BarcodeApi;
import com.github.bingoohuang.mallscannertest.api.SpringRestClientConfig;
import com.google.common.io.ByteStreams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;

import static com.google.common.truth.Truth.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringRestClientConfig.class)
public class BarcodeApiTest {
    @Autowired BarcodeApi barcodeApi;

    @Test
    public void barcode() throws IOException {
        ClassPathResource iccidRes = new ClassPathResource("barcode/iccid.jpg");
        InputStream iccidIs = iccidRes.getInputStream();
        byte[] iccidBytes = ByteStreams.toByteArray(iccidIs);

        MockMultipartFile barcodeImage = new MockMultipartFile("barcodeImage",
                "barcodeImage", null, iccidBytes);
        Barcode barcode = barcodeApi.barcodeScan(barcodeImage);
        assertThat(barcode.getState()).isEqualTo(0);
        assertThat(barcode.getCodeText()).isEqualTo("8986011471110101394");
    }

    @Test
    public void qrcode() throws IOException {
        ClassPathResource wechatRes = new ClassPathResource("barcode/wechat.jpg");
        InputStream iccidIs = wechatRes.getInputStream();
        byte[] iccidBytes = ByteStreams.toByteArray(iccidIs);

        MockMultipartFile wechatImage = new MockMultipartFile("barcodeImage",
                "barcodeImage", null, iccidBytes);
        Barcode barcode = barcodeApi.barcodeScan(wechatImage);
        assertThat(barcode.getState()).isEqualTo(0);
        assertThat(barcode.getCodeText()).isEqualTo("http://weixin.qq.com/r/ufsIEOTE_7mEraBz966r");
    }

    @Test
    public void none() throws IOException {
        ClassPathResource res = new ClassPathResource("barcode/none.png");
        InputStream is = res.getInputStream();
        byte[] bytes  = ByteStreams.toByteArray(is);

        MockMultipartFile wechatImage = new MockMultipartFile("barcodeImage",
                "barcodeImage", null, bytes);
        Barcode barcode = barcodeApi.barcodeScan(wechatImage);
        assertThat(barcode.getState()).isEqualTo(-2);
        assertThat(barcode.getMessage()).isEqualTo("No barcode recognized");
    }
}
