package com.vtes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public class VtesController {

	@GetMapping("/")
	public String view() {
		return "OK";
	}
}
