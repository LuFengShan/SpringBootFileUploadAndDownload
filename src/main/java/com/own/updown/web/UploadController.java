package com.own.updown.web;

import com.own.updown.pojo.UploadForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传示例，单个上传，多个上传
 */
@Slf4j
@Controller
public class UploadController {

	@RequestMapping(value = "/")
	public String homePage() {
		return "index";
	}

	// GET: Show upload form page.
	@GetMapping("/uploadOneFile")
	public String uploadOneFileHandler(Model model) {

		UploadForm myUploadForm = new UploadForm();
		model.addAttribute("myUploadForm", myUploadForm);

		return "uploadOneFile";
	}

	// POST: Do Upload
	@PostMapping("/uploadOneFile")
	public String uploadOneFileHandlerPOST(HttpServletRequest request, //
	                                       Model model, //
	                                       @ModelAttribute("myUploadForm") UploadForm myUploadForm) {

		return this.doUpload(request, model, myUploadForm);

	}

	// GET: Show upload form page.
	@GetMapping("/uploadMultiFile")
	public String uploadMultiFileHandler(Model model) {

		UploadForm myUploadForm = new UploadForm();
		model.addAttribute("myUploadForm", myUploadForm);

		return "uploadMultiFile";
	}

	// POST: Do Upload
	@PostMapping("/uploadMultiFile")
	public String uploadMultiFileHandlerPOST(HttpServletRequest request, //
	                                         Model model, //
	                                         @ModelAttribute("myUploadForm") UploadForm myUploadForm) {

		return this.doUpload(request, model, myUploadForm);

	}

	private String doUpload(HttpServletRequest request, Model model, UploadForm myUploadForm) {

		String description = myUploadForm.getDescription();
		log.info("Description: " + description);

		// Root Directory.
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		log.info("uploadRootPath=" + uploadRootPath);

		File uploadRootDir = new File(uploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		MultipartFile[] fileDatas = myUploadForm.getFileDatas();
		//
		List<File> uploadedFiles = new ArrayList<>();
		List<String> failedFiles = new ArrayList<>();

		for (MultipartFile fileData : fileDatas) {

			// Client File Name
			String name = fileData.getOriginalFilename();
			log.info("Client File Name = " + name);

			if (name != null && name.length() > 0) {
				try {
					// Create the file at server
					File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					//
					uploadedFiles.add(serverFile);
					log.info("Write file: " + serverFile);
				} catch (Exception e) {
					log.info("Error Write file: " + name);
					failedFiles.add(name);
				}
			}
		}
		model.addAttribute("description", description);
		model.addAttribute("uploadedFiles", uploadedFiles);
		model.addAttribute("failedFiles", failedFiles);
		return "uploadResult";
	}

}