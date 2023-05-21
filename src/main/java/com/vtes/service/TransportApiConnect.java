package com.vtes.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vtes.config.FeignClientConfig;

@FeignClient(name ="navitime-transport",url="https://navitime-transport.p.rapidapi.com", configuration = FeignClientConfig.class)
public interface TransportApiConnect {
	@GetMapping("/transport_node")
	public ResponseEntity<String> getStationDetail(@RequestParam(name ="word") String word);
}
