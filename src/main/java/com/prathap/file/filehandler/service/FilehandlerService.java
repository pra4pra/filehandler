package com.prathap.file.filehandler.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilehandlerService {

	void init();

	boolean storeFile(MultipartFile file);

	Resource loadFile(String filename);
	

}
