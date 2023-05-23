package com.vtes.service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {
	private Logger LOGGER = LoggerFactory.getLogger(AmazonS3ServiceImpl.class);

	@Autowired
	private AmazonS3 amazonS3;

	@Override
	public PutObjectResult upload(String path, String fileName, Optional<Map<String, String>> optionalMetaData,
			InputStream inputStream) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		optionalMetaData.ifPresent(map -> {
			if (!map.isEmpty()) {
				map.forEach(objectMetadata::addUserMetadata);
			}
		});
		LOGGER.debug("Path: " + path + ", FileName:" + fileName);
		return amazonS3.putObject(path, fileName, inputStream, objectMetadata);
	}

	public S3Object download(String path, String fileName) {
		return amazonS3.getObject(path, fileName);
	}
}
