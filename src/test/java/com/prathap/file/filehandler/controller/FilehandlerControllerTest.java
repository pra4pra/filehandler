package com.prathap.file.filehandler.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.prathap.file.filehandler.common.FilehandlerRuntimeException;
import com.prathap.file.filehandler.service.FilehandlerServiceImpl;

@AutoConfigureMockMvc
@SpringBootTest
class FilehandlerControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	void testHealth() throws Exception {
		String HEALTH_SUCCESS_MSG = "fileHandler is working";
		this.mvc.perform(get("/health")).andExpect(status().isOk()).andReturn().equals(HEALTH_SUCCESS_MSG);
	}

	@Test
	void testUploadAndDownloadFile() throws Exception {

		String fileName = "test.txt";
		String fileContent = "filehandler App Test";
		
		MockMultipartFile multipartFile = new MockMultipartFile("file", fileName, "text/plain",
				fileContent.getBytes());

		this.mvc.perform(multipart("/uploadFile").file(multipartFile))
//				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());

		MvcResult result = this.mvc.perform(get("/downloadFile/" + multipartFile.getOriginalFilename())).andReturn();

		assertEquals(result.getResponse().getContentAsString(), fileContent);
		assertEquals(result.getResponse().getHeader(HttpHeaders.CONTENT_DISPOSITION), "attachment; filename=\"" + fileName + "\"");
	}

	@Test
	void testHandleFileUpload() throws Exception {

		String fileName = "test.txt";
		MockMultipartFile multipartFile = new MockMultipartFile("file", fileName, "text/plain",
				"Spring Framework".getBytes());

		MvcResult result = this.mvc.perform(multipart("/uploadFile").file(multipartFile)).andReturn();

		assertEquals(result.getResponse().getContentAsString(), "You successfully uploaded."+fileName);
		assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
	}

	@Test
	void testMissingFile() throws Exception {

		MvcResult result = this.mvc.perform(get("/downloadFile/unExists.txt")).andReturn();
		assertEquals(result.getResponse().getStatus(), HttpStatus.NOT_FOUND.value());

	}
}
