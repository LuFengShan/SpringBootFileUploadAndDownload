springboot文件的上传和下载的总结，以后就用这个了，方便
## 处理流程
### 上传文件
> UploadController
1. 配置上传文件的大小
2. 上传文件的对象创建
3. 上传文件的控制器
4. 页面整理

### 下载文件
> DownLoadController
1. 确定下载文件的方式(3种)
```java
// ResponseEntity <InputStreamResource>：编写一个返回ResponseEntity的方法。此对象包装一个  InputStreamResource对象（它是用户下载的文件的数据）。
// ResponseEntity <ByteArrayResource>：编写一个返回ResponseEntity的方法。此对象包装  ByteArrayResource对象（这是用户下载的文件的数据）。
// HttpServletRespone：将要下载的文件的数据直接写入HttpServletRespone。
```
2. header的描述
- Content-Disposition
- Content-Length
- Content-Type

