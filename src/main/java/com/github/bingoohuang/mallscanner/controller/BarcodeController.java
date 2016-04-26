package com.github.bingoohuang.mallscanner.controller;

import com.aspose.barcoderecognition.BarCodeReader;
import com.github.bingoohuang.mallscanner.domain.Barcode;
import com.github.bingoohuang.springrest.boot.annotations.RestfulSign;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import static com.github.bingoohuang.mallscanner.domain.Barcode.newBarCode;


@RestController
@RestfulSign(ignore = true)
public class BarcodeController {
    /*
    ~/Downloads > curl -i -F "barcodeImage=@/Users/bingoohuang/Downloads/iccid.jpg" "http://localhost:8367/barcode/scan"
    HTTP/1.1 100 Continue

    HTTP/1.1 200 OK
    Server: Apache-Coyote/1.1
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Tue, 26 Apr 2016 02:20:39 GMT

    {"state":0,"message":"OK","codeText":"8986011471110101394"}

    ~/Downloads > curl -i -F "barcodeImage=@/Users/bingoohuang/Downloads/wh.jpg" "http://localhost:8367/barcode/scan"
    HTTP/1.1 100 Continue

    HTTP/1.1 200 OK
    Server: Apache-Coyote/1.1
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Tue, 26 Apr 2016 02:20:53 GMT

    {"state":-2,"message":"No barcode recognized","codeText":null}

    ~/Downloads > curl -i -F "barcodeImage=/Users/bingoohuang/Downloads/wh.jpg" "http://localhost:8367/barcode/scan"
    HTTP/1.1 100 Continue

    HTTP/1.1 400 Bad Request
    Server: Apache-Coyote/1.1
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Tue, 26 Apr 2016 02:21:04 GMT
    Connection: close

    {"timestamp":1461637264847,"status":400,"error":"Bad Request","exception":"org.springframework.web.bind.MissingServletRequestParameterException","message":"Required MultipartFile parameter 'barcodeImage' is not present","path":"/barcode/scan"}⏎
     */
    @RequestMapping(value = "/barcode/scan", method = RequestMethod.POST)
    public Barcode barcodeScan(@RequestParam("barcodeImage") MultipartFile barcodeImage) {
        try {
            InputStream is = barcodeImage.getInputStream();
            BufferedImage img = ImageIO.read(is);
            BarCodeReader rd = new BarCodeReader(img);

            return rd.read()
                    ? newBarCode(0, "OK", rd.getCodeText())
                    : newBarCode(-2, "No barcode recognized", "NoBarcode");
        } catch (Exception ex) {
            return newBarCode(-1, ex.getMessage(), "Exception");
        }
    }
}