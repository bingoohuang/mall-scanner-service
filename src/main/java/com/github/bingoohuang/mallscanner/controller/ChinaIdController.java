package com.github.bingoohuang.mallscanner.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.bingoohuang.mallscanner.domain.ChinaId;
import com.github.bingoohuang.mallscanner.utils.ClassPathProperties;
import com.github.bingoohuang.mallscanner.utils.DataPlusParams;
import com.github.bingoohuang.mallscanner.utils.DataPlusSender;
import com.github.bingoohuang.springrest.boot.annotations.RestfulSign;
import com.google.common.io.ByteStreams;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/chinaid/scan")
@RestfulSign(ignore = true)
public class ChinaIdController {
    static String service_url = "https://shujuapi.aliyun.com/dataplus_57525/ocr/ocr_idcard";

    static String ak_id = ClassPathProperties.getConfig("dataplus.properties", "AccessKeyID");
    static String ak_secret = ClassPathProperties.getConfig("dataplus.properties", "AccessKeySecret");

    /*
    ~/Downloads > curl -i -F "faceImage=@/Users/bingoohuang/Downloads/face.jpg" "http://localhost:8367/chinaid/scan/face"
    HTTP/1.1 100 Continue

    HTTP/1.1 200 OK
    Server: Apache-Coyote/1.1
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Tue, 26 Apr 2016 09:41:40 GMT

    {"state":0,"message":"OK","name":"韦小宝","num":"11201116511220213X","address":"北京市东城区景山前街4号紫禁城敬事房","startDate":null,"endDate":null}⏎
     */
    @RequestMapping(value = "/face", method = RequestMethod.POST)
    public ChinaId faceScan(@RequestParam("faceImage") MultipartFile faceImage) throws IOException {
        String body = createRequestBody(faceImage, "face");
        String result = DataPlusSender.sendPost(service_url, body, ak_id, ak_secret);
        JSONObject out = parseResponse(result);
        if (out.getBoolean("success")) {
            String name = out.getString("name");
            String num = out.getString("num");
            String address = out.getString("address");

            return ChinaId.newChinaIdFace(name, num, address);
        }

        return ChinaId.newChinaId(-1, result);
    }

    /*
    ~/Downloads > curl -i -F "backImage=@/Users/bingoohuang/Downloads/back.jpg" "http://localhost:8367/chinaid/scan/back"
    HTTP/1.1 100 Continue

    HTTP/1.1 200 OK
    Server: Apache-Coyote/1.1
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Tue, 26 Apr 2016 09:43:48 GMT

    {"state":0,"message":"OK","name":null,"num":null,"address":null,"startDate":"20060215","endDate":"20160215"}
     */
    @RequestMapping(value = "/back", method = RequestMethod.POST)
    public ChinaId backScan(@RequestParam("backImage") MultipartFile backImage) throws IOException {
        String body = createRequestBody(backImage, "back");
        String result = DataPlusSender.sendPost(service_url, body, ak_id, ak_secret);
        JSONObject out = parseResponse(result);
        if (out.getBoolean("success")) {
            String startDate = out.getString("start_date");
            String endDate = out.getString("end_date");
            return ChinaId.newChinaIdBack(startDate, endDate);
        }

        return ChinaId.newChinaId(-1, result);
    }

    private JSONObject parseResponse(String result) {
        JSONObject resultObj = JSON.parseObject(result);
        JSONArray outputs = resultObj.getJSONArray("outputs");
        JSONObject jsonObject = outputs.getJSONObject(0);
        String output = jsonObject.getJSONObject("outputValue").getString("dataValue");
        return JSON.parseObject(output);
    }

    private String createRequestBody(MultipartFile faceImage, String side) throws IOException {
        InputStream is = faceImage.getInputStream();
        byte[] bytes = ByteStreams.toByteArray(is);
        String base64 = DatatypeConverter.printBase64Binary(bytes);

        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        JSONObject configure = new JSONObject();
        configure.put("side", side);

        JSONObject input = new JSONObject();
        JSONArray inputs = new JSONArray();

        input.put("image", DataPlusParams.getParam(50, base64));
        input.put("configure", DataPlusParams.getParam(50, configure.toString()));
        inputs.add(input);
        requestObj.put("inputs", inputs);

        return requestObj.toString();
    }
}