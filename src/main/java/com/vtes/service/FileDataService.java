package com.vtes.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.vtes.entity.FileData;
import com.vtes.entity.User;
import com.vtes.exception.UploadFileException;
import com.vtes.repository.FileDataRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileDataService {

	@Autowired
	private AmazonS3ServiceImpl amazonS3ServiceImpl;

	@Autowired
	private FileDataRepo fileDataRepo;
	
	@Autowired
	private FareService fareService;

	@Value("${aws.s3.bucket.name}")
	private String bucketName;

	@Async
	public void uploadFileToS3(Integer userId, MultipartFile file) throws IOException, UploadFileException {

		Map<String, String> metadata = new HashMap<>();
		metadata.put("Content-Type", file.getContentType());
		metadata.put("Content-Length", String.valueOf(file.getSize()));

		String path = String.format("%s/%s", bucketName, UUID.randomUUID());
		String fileName = String.format("%s", file.getOriginalFilename());

		// Uploading file to s3
		PutObjectResult putObjectResult = amazonS3ServiceImpl.upload(path, fileName, Optional.of(metadata),
				file.getInputStream());
		
		if(putObjectResult != null){
			log.info("{} uploaded file.",userId);
			fareService.deleteByUserId(userId);
			
			log.info("{} fare detail deleted.",userId);
			// Saving metadata to db
			fileDataRepo.save((new FileData(new User(userId), fileName, path, new Date())));
		
		}else {
			log.debug("Can not upload file with user {}",userId);
			throw new UploadFileException(fileName);
		}

		

	}

	@Async
	public byte[] download(Integer id, Integer userId) {
		FileData fileMeta = fileDataRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("File not found"));
		S3Object s3Object = amazonS3ServiceImpl.download(fileMeta.getFilePath(), fileMeta.getFileName());
		log.info("Downloading an object with key= " + fileMeta.getFileName());

		byte[] content = null;
		final S3ObjectInputStream stream = s3Object.getObjectContent();
		try {
			content = IOUtils.toByteArray(stream);
			log.info("User {} is downloaded file {}successfully.", userId,id);
			s3Object.close();
		} catch (final IOException ex) {
			log.debug("{} download file failed",userId);
			log.debug("IO Error Message= " + ex.getMessage());
		}
		return content;
	}

	public List<FileData> findByUserId(Integer userId) {
		return fileDataRepo.findByUserId(userId).get();

	}

	public String getFileNameById(Integer id) {
		return fileDataRepo.findFileNameById(id);
	}
}
