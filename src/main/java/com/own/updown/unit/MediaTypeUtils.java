package com.own.updown.unit;


import javax.servlet.ServletContext;

import org.springframework.http.MediaType;

/**
 * 获取文件的格式 内容类型
 * 此信息有助于浏览器了解哪些应用程序可以打开此内容，并建议用户在成功下载内容时通过其计算机上可用的程序打开。 如果您希望浏览器立即下载此类内容而不询问用户，请设置  Content-Type = application / octet-stream。
 */
public class MediaTypeUtils {

	/**
	 * {@link ServletContext#getMimeType(String)}:返回指定文件的MIME类型，如果MIME类型未知，则返回null。
	 * MIME类型由servlet容器的配置确定，可以在Web应用程序部署描述符中指定。 常见的MIME类型是“text / html”和“image / gif”。
	 * @param servletContext
	 * @param fileName 文件的名称
	 * @return
	 */
	public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
		// application/pdf
		// application/xml
		// image/gif, ...
		String mineType = servletContext.getMimeType(fileName);
		try {
			MediaType mediaType = MediaType.parseMediaType(mineType);
			return mediaType;
		} catch (Exception e) {
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}

}
