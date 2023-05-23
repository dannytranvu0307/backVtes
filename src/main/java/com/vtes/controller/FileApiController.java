package com.vtes.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vtes.model.ResponseData;
import com.vtes.model.ResponseData.ResponseType;
import com.vtes.service.FileDataService;

@RestController
@RequestMapping("/api/v1")
public class FileApiController {

	@Autowired
	private FileDataService fileService;
	
	@PostMapping(value = "/files",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException{
		//Get user id;
		Integer userId = 1;
		fileService.uploadFileToS3(userId, file);

		return ResponseEntity.ok()
				.body(ResponseData.builder()
						.code("200")
						.type(ResponseType.INFO)
						.message("uploaded")
						.build()
						);
		
	}
	
	@GetMapping(value = "/files/{fileId}")
	public ResponseEntity<?> downloadFile(@PathVariable(value = "fileId",required = true) Integer fileId,
			HttpServletRequest request) throws IOException{
		
			Integer userId = 1;
		
		   byte[] data = fileService.download(fileId,userId);
	        final ByteArrayResource resource = new ByteArrayResource(data);
	        String fileName = fileService.getFileNameById(fileId);
	        String encodedFilename = URLEncoder.encode(fileName, "UTF-8");
	        String contentDiposition = "attachment; filename=\"" + encodedFilename + "\"";
	        System.out.println(fileName);
	        
	        return ResponseEntity
	                .ok()
	                .contentLength(data.length)
	                .header(HttpHeaders.ACCEPT_CHARSET, "UTF-8")
	                .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	                .header(HttpHeaders.CONTENT_DISPOSITION, contentDiposition)
	                .body(resource);
		
	}
}
