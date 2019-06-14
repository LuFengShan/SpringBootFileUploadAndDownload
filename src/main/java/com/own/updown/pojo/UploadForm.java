package com.own.updown.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件的表单对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadForm {
	/** 文件描述 */
	private String description;

	/** 上传的文件 */
	private MultipartFile[] fileDatas;
}
