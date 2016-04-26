package com.github.bingoohuang.mallscannertest.api;

import com.github.bingoohuang.mallscannertest.apibeans.Barcode;
import com.github.bingoohuang.springrestclient.annotations.SpringRestClientEnabled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/barcode")
@SpringRestClientEnabled(baseUrl = "http://localhost:8367")
public interface BarcodeApi {
    @RequestMapping(value = "/scan", method = RequestMethod.POST)
    Barcode barcodeScan(@RequestParam("barcodeImage") MultipartFile barcodeImage);
}
