package com.vtes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vtes.exception.BadRequestException;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignClientConfig {
	@Value("${feign.client.api.key}")
    private String apiKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignClientInterceptor(apiKey);
    }

    @Bean
    public ErrorDecoder navitimeErrorDecoder() {
    	return new NavitimeErrorDecoder();
    }
    
    private static class FeignClientInterceptor implements RequestInterceptor{

        private final String apiKey;

        public FeignClientInterceptor(String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public void apply(RequestTemplate requestTemplate) {
            requestTemplate.header("X-RapidAPI-Key", apiKey);
            
            
            
        }

    }
    private static class NavitimeErrorDecoder implements ErrorDecoder{

		@Override
		public Exception decode(String methodKey, Response response) {
			if(response.status() == 400) {
				return new BadRequestException("Bad Request");
			}
			if(response.status() != 200) {
				return new Exception("Server Error");
			}
			return null;
		}

	
    }
    
}

