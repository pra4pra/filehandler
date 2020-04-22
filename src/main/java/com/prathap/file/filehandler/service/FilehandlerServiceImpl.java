package com.prathap.file.filehandler.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.prathap.file.filehandler.common.FilehandlerRuntimeException;

@Service
public class FilehandlerServiceImpl implements FilehandlerService {

	private final Path rootLocation;

	private static final Logger logger = LoggerFactory.getLogger(FilehandlerServiceImpl.class);

	private static String FILE_PATH = "uploadDir";

	@Autowired
	public FilehandlerServiceImpl() {
		this.rootLocation = Paths.get(FILE_PATH);
	}

	//Store file and return boolean
	@Override
	public boolean storeFile(MultipartFile file) {
		
		logger.info("storeFile starts with :{}", file);
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			logger.debug("file validation starts");
			if (filename.contains("..")) {
				throw new FilehandlerRuntimeException("fileName is not valid or empty... " + filename);
			}

			if (file.isEmpty()) {
				throw new FilehandlerRuntimeException("file is not valid or empty... " + filename);
			}
			
			logger.debug("file validation compled");
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
				logger.debug("file stored in the location successfully");
			}
		} catch (IOException ioe) {
			logger.error("file stored in the location failed with exception:{}", ioe);
			throw new FilehandlerRuntimeException("Failed to persist file.. try again later " + filename, ioe);
		}
		return true;
	}

	//Get file by file name. return resource with file content
	@Override
	public Resource loadFile(String fileName) {
		logger.info("loadFile starts with fileName:{}", fileName);
		try {
			Path file = rootLocation.resolve(fileName);
			Resource resource = new UrlResource(file.toUri());
			
			logger.debug("file present with fileName:{}", fileName);
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				logger.error("file does not exists fileName:{}", fileName);
				throw new FilehandlerRuntimeException("resource not exists or not available to serve. check filename and try again " + fileName);

			}
		} catch (MalformedURLException e) {
			logger.error("file malformed url fileName:{}", fileName);
			throw new FilehandlerRuntimeException("with again with valid file name and path " + fileName, e);
		}
	}

	//create file directory to store files.
	@Override
	public void init() {
		
		logger.info("init start");
		
		try {
			Files.createDirectories(rootLocation);
			logger.debug("directory creation completed");
		}
		catch (IOException e) {
			logger.error("error in creating directory :{}", rootLocation);
			throw new FilehandlerRuntimeException("Not able to initialize directory", e);
		}
	}
	
}