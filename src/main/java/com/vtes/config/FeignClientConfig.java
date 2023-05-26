package com.vtes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	private static class FeignClientInterceptor implements RequestInterceptor {

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

	// Error decoder for handling API response errors and rotate access key when current key expired
	private static class NavitimeErrorDecoder implements ErrorDecoder {

		private final RapidAPIAccessKeyManager accessKeyManager;
		private static final Integer TOO_MANY_REQUEST = 429;

		public NavitimeErrorDecoder(RapidAPIAccessKeyManager accessKeyManager) {
			this.accessKeyManager = accessKeyManager;
		}

		@Override
		public Exception decode(String methodKey, Response response) {
			if (response.status() == TOO_MANY_REQUEST) {
				log.error("Too many requests");
				accessKeyManager.rotateAccesskey();
				log.debug("Access key has changed. Current key is {}", accessKeyManager.getCurrentAccessKey());
			}

			if (response.status() == 400) {
				return new BadRequestException("Bad Request");
			}

			if (response.status() != 200) {
				return new Exception("Error occurred while calling 3rd-party API.");
			}

			return null;
		}
	}
}
