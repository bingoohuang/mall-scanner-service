package com.github.bingoohuang.mallscannertest.api;

import com.github.bingoohuang.mallscannertest.apibeans.ChinaId;
import com.github.bingoohuang.springrestclient.annotations.SpringRestClientEnabled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/chinaid/scan")
@SpringRestClientEnabled(baseUrl = "http://localhost:8367")
public interface ChinaIdApi {
    @RequestMapping(value = "/face", method = RequestMethod.POST)
    ChinaId faceScan(@RequestParam("faceImage") MultipartFile faceImage);

    @RequestMapping(value = "/back", method = RequestMethod.POST)
    ChinaId backScan(@RequestParam("backImage") MultipartFile backImage);
}
