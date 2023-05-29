package com.vtes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vtes.exception.AccessKeyExpiredException;
import com.vtes.exception.BadRequestException;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

/*
 * @Author: chien.tranvan
 * @Date: 2023/05/26
 * @Description: Configuration class for Feign client.
 */

@Configuration
@Slf4j
public class FeignClientConfig {

	private final RapidAPIAccessKeyManager accessKeyManager;

	public FeignClientConfig(RapidAPIAccessKeyManager accessKeyManager) {
		this.accessKeyManager = accessKeyManager;
	}

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new FeignClientInterceptor(accessKeyManager);
	}

	@Bean
	public ErrorDecoder navitimeErrorDecoder() {
		return new NavitimeErrorDecoder(accessKeyManager);
	}

	// Interceptor for adding the API key to the request header
	public static class FeignClientInterceptor implements RequestInterceptor {

		private final RapidAPIAccessKeyManager accessKeyManager;

		public FeignClientInterceptor(RapidAPIAccessKeyManager accessKeyManager) {
			this.accessKeyManager = accessKeyManager;
		}

		@Override
		public void apply(RequestTemplate requestTemplate) {
			String apiKey = accessKeyManager.getCurrentAccessKey();
			requestTemplate.header("X-RapidAPI-Key", apiKey);
		}
	}

	// Error decoder for handling API response errors and rotate access key when
	// current key expired
	public static class NavitimeErrorDecoder implements ErrorDecoder {

		public final RapidAPIAccessKeyManager accessKeyManager;
		private static final Integer TOO_MANY_REQUEST = 429;
		private static final Integer BAD_REQUEST = 400;
		private static final Integer OK = 200;

		public NavitimeErrorDecoder(RapidAPIAccessKeyManager accessKeyManager) {
			this.accessKeyManager = accessKeyManager;
		}

		@Override
		public Exception decode(String methodKey, Response response) {
			if (response.status() == TOO_MANY_REQUEST) {
				String currentKey = accessKeyManager.getCurrentAccessKey();
				accessKeyManager.rotateAccesskey();
				return new AccessKeyExpiredException(currentKey);
			}

			if (response.status() == BAD_REQUEST) {
				return new BadRequestException("Bad Request");
			}

			if (response.status() != OK) {
				return new Exception("Error occurred while calling 3rd-party API.");
			}

			return null;
		}
	}
}
