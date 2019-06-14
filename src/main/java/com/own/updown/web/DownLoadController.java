package com.own.updown.web;

import com.own.updown.unit.MediaTypeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 下载示例
 * ResponseEntity <InputStreamResource>：编写一个返回ResponseEntity的方法。此对象包装一个  InputStreamResource对象（它是用户下载的文件的数据）。
 * ResponseEntity <ByteArrayResource>：编写一个返回ResponseEntity的方法。此对象包装  ByteArrayResource对象（这是用户下载的文件的数据）。
 * HttpServletRespone：将要下载的文件的数据直接写入HttpServletRespone。
 */
@Slf4j
@RestController
public class DownLoadController {
	private static final String DIRECTORY = "D:/PDF";
	private static final String DEFAULT_FILE_NAME = "spring-boot-reference.pdf";

	@Autowired
	private ServletContext servletContext;

	/**
	 * http://localhost:8080/download1?fileName=abc.zip
	 * Using ResponseEntity<InputStreamResource>
	 *
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/download1")
	public ResponseEntity<InputStreamResource> downloadFile1(
			@RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {
		// 1. 获取文件的mime格式
		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
		log.info("fileName: " + fileName);
		log.info("mediaType: " + mediaType);
		// 2. 获取文件的路径
		File file = new File(DIRECTORY + "/" + fileName);
		// 3. 文件输入源
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok()
				// Content-Disposition
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
				// Content-Type
				.contentType(mediaType)
				// Contet-Length
				.contentLength(file.length()) // 文件的大小
				.body(resource);
	}


	/**
	 * http://localhost:8080/download1?fileName=abc.zip
	 * 使用ResponseEntity<ByteArrayResource>
	 *
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/download2")
	public ResponseEntity<ByteArrayResource> downloadFile2(
			@RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {

		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
		log.info("fileName: " + fileName);
		log.info("mediaType: " + mediaType);

		Path path = Paths.get(DIRECTORY + "/" + DEFAULT_FILE_NAME);
		byte[] data = Files.readAllBytes(path);
		ByteArrayResource resource = new ByteArrayResource(data);

		return ResponseEntity.ok()
				// Content-Disposition
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
				// Content-Type
				.contentType(mediaType) //
				// Content-Lengh
				.contentLength(data.length) //
				.body(resource);
	}

	/**
	 * http://localhost:8080/download3?fileName=abc.zip
	 * Using HttpServletResponse
	 *
	 * @param resonse
	 * @param fileName
	 * @throws IOException
	 */
	@GetMapping("/download3")
	public void downloadFile3(HttpServletResponse resonse,
	                          @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {

		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
		log.info("fileName: " + fileName);
		log.info("mediaType: " + mediaType);

		File file = new File(DIRECTORY + "/" + fileName);

		// Content-Type
		// application/pdf
		resonse.setContentType(mediaType.getType());

		// Content-Disposition
		resonse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName());

		// Content-Length
		resonse.setContentLength((int) file.length());

		BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream outStream = new BufferedOutputStream(resonse.getOutputStream());

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		outStream.flush();
		inStream.close();
	}
}
