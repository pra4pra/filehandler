package com.prathap.file.filehandler.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.prathap.file.filehandler.common.FilehandlerRuntimeException;

class FilehandlerServiceImplTest {
	
	private FilehandlerServiceImpl service;
	
	//Assumption: adding only few test cases. But many other +, -ve test cases need to add.
	
	@BeforeEach
	public void init() {
		service = new FilehandlerServiceImpl();
		service.init();
	}

	@Test
	void testStoreFile() {
		
		assertTrue(service.storeFile(new MockMultipartFile("sampleText", "sample.txt", MediaType.TEXT_PLAIN_VALUE,
				"Sample Text content".getBytes())));
		
	}

	@Test
	void testStoreAndLoadFile() {
		
		service.storeFile(new MockMultipartFile("sampleText", "sample.txt", MediaType.TEXT_PLAIN_VALUE,
				"Sample Text content".getBytes()));
		assertThat(service.loadFile("foo.txt").exists());
	}

	@Test
	void testNonExistentStoreFile() {
		assertThrows(FilehandlerRuntimeException.class, () -> {
			service.storeFile(new MockMultipartFile("file", "../nonExists.txt",
			MediaType.TEXT_PLAIN_VALUE, "Hello, World".getBytes()));
		});
	}
	
	@Test
	void testNonExistentLoadFile() {
		assertThrows(FilehandlerRuntimeException.class, () -> {
			service.loadFile( "../nonExists.txt");
		});
	}

}
