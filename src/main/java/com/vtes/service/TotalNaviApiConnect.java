package com.vtes.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vtes.config.FeignClientConfig;

@FeignClient(name = "navitime-totalnavi",url = "https://navitime-route-totalnavi.p.rapidapi.com",configuration = FeignClientConfig.class)
public interface TotalNaviApiConnect {
	@GetMapping("/route_transit")
	public ResponseEntity<String> searchRoutes(@RequestParam Map<String, Object> params);
}
