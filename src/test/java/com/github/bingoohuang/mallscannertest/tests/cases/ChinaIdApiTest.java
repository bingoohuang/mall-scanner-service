package com.github.bingoohuang.mallscannertest.tests.cases;

import com.github.bingoohuang.mallscannertest.api.ChinaIdApi;
import com.github.bingoohuang.mallscannertest.api.SpringRestClientConfig;
import com.github.bingoohuang.mallscannertest.apibeans.ChinaId;
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
public class ChinaIdApiTest {
    @Autowired ChinaIdApi chinaIdApi;

    @Test
    public void face() throws IOException {
        ClassPathResource cpRes = new ClassPathResource("chinaid/face.jpg");
        InputStream is = cpRes.getInputStream();
        byte[] bytes = ByteStreams.toByteArray(is);

        MockMultipartFile faceImage = new MockMultipartFile("faceImage",
                "faceImage", null, bytes);

        ChinaId chinaId = chinaIdApi.faceScan(faceImage);

        assertThat(chinaId.getState()).isEqualTo(0);
        assertThat(chinaId.getName()).isEqualTo("韦小宝");
        assertThat(chinaId.getNum()).isEqualTo("11201116511220213X");
        assertThat(chinaId.getAddress()).isEqualTo("北京市东城区景山前街4号紫禁城敬事房");
    }

    @Test
    public void back() throws IOException {
        ClassPathResource cpRes = new ClassPathResource("chinaid/back.jpg");
        InputStream is = cpRes.getInputStream();
        byte[] bytes = ByteStreams.toByteArray(is);

        MockMultipartFile backImage = new MockMultipartFile("backImage",
                "backImage", null, bytes);

        ChinaId chinaId = chinaIdApi.backScan(backImage);

        assertThat(chinaId.getState()).isEqualTo(0);
        assertThat(chinaId.getStartDate()).isEqualTo("20060215");
        assertThat(chinaId.getEndDate()).isEqualTo("20160215");
    }
}
