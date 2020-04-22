package com.prathap.file.filehandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilehandlerApplicationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;
	
	//added only positive test cases here. Negative cases are added in respective controller and service
	@Test
	void testHealth() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/health", String.class))
				.contains("fileHandler is working");

	}

	@Test
	void testFileUpload() throws Exception {

		String filePath = "./src/test/resource/";
		String fileName = "testfile.txt";
		String uri = "http://localhost:" + port + "/uploadFile";
		
		Resource resource = new FileSystemResource(filePath + fileName);

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("file", resource);

		ResponseEntity<String> response = this.restTemplate.postForEntity(uri, map, String.class);

		assertEquals(response.getStatusCodeValue(), HttpStatus.OK.value());
		assertEquals(response.getBody(), "You successfully uploaded." + fileName);

	}

}
