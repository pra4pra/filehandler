package com.prathap.file.filehandler.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.prathap.file.filehandler.common.FilehandlerHealthInfo;
import com.prathap.file.filehandler.common.FilehandlerRuntimeException;
import com.prathap.file.filehandler.common.FilehandlerUploadFileInfo;
import com.prathap.file.filehandler.service.FilehandlerService;

@Controller
public class FilehandlerController {

	private static final Logger logger = LoggerFactory.getLogger(FilehandlerController.class);

	private final String HEALTH_SUCCESS_MESSAGE = "fileHandler is working";
	private final String UPLOAD_SUCCESS_MESSAGE = "You successfully uploaded.";

	private final FilehandlerService filehandlerService;

	@Autowired
	public FilehandlerController(FilehandlerService filehandlerService) {
		this.filehandlerService = filehandlerService;
	}

	// Health check service
	@GetMapping("/health")
	public ResponseEntity<FilehandlerHealthInfo> health() {
		logger.debug("health start");

		return ResponseEntity.ok(new FilehandlerHealthInfo(LocalDateTime.now().toString(), HEALTH_SUCCESS_MESSAGE));
	}

	// File download.
	@GetMapping("/downloadFile/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

		logger.info("downloadFile start");
		Resource file = filehandlerService.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PostMapping("/uploadFile")
	public ResponseEntity<FilehandlerUploadFileInfo> uploadFile(@RequestParam("file") MultipartFile file) {

		logger.info("handleFileUpload start");
		boolean status = filehandlerService.storeFile(file);
		logger.debug("file store is completed {}", status);

		FilehandlerUploadFileInfo upload = new FilehandlerUploadFileInfo.Builder(LocalDateTime.now().toString(),
				UPLOAD_SUCCESS_MESSAGE).setFileName(file.getOriginalFilename()).build();

		return ResponseEntity.ok(upload);
	}

	@ExceptionHandler(FilehandlerRuntimeException.class)
	public ResponseEntity<?> handleAllExceptions(FilehandlerRuntimeException ex) {
		logger.error("handleAllExceptions start exception :{}", ex);
		return ResponseEntity.notFound().build();
	}

}
