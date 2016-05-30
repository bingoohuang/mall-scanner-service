# mall-scanner-service
some image scanner service for mall

## Install and Startup
### Install
mvn package -DPbeKey={your pbe key}

### Startup
java -DPbeKey={your pbe key}  -jar mall-scanner-service-0.0.1.jar

## China ID scan
Please keep the network access ready for `https://shujuapi.aliyun.com/dataplus_57525/ocr/ocr_idcard`
You can also test ID image scan manually online by [aliyun data](https://data.aliyun.com/demo/ai/ocr?spm=a2c0j.7918055.banner.2.726Az2)

```
~/Downloads > curl -i -F "backImage=@/Users/bingoohuang/Downloads/back.jpg" "http://localhost:8367/chinaid/scan/back"
HTTP/1.1 100 Continue

HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 26 Apr 2016 09:57:46 GMT

{"state":0,"message":"OK","name":null,"num":null,"address":null,"startDate":"20060215","endDate":"20160215"}

```

```
~/Downloads > curl -i -F "faceImage=@/Users/bingoohuang/Downloads/face.jpg" "http://localhost:8367/chinaid/scan/face"
HTTP/1.1 100 Continue

HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 26 Apr 2016 09:57:50 GMT

{"state":0,"message":"OK","name":"韦小宝","num":"11201116511220213X","address":"北京市东城区景山前街4号紫禁城敬事房","startDate":null,"endDate":null}
```

## Barcode scan

```
~/Downloads > curl -i -F "barcodeImage=@/Users/bingoohuang/Downloads/iccid.jpg" "http://localhost:8367/barcode/scan"
HTTP/1.1 100 Continue

HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 26 Apr 2016 09:58:06 GMT

{"state":0,"message":"OK","codeText":"8986011471110101394"}⏎                                                                                                                         ~/Downloads >
```
