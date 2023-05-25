package com.vtes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtes.entity.CommuterPass;
import com.vtes.exception.CommuterPassNotFound;
import com.vtes.exception.ParameterInvalidException;
import com.vtes.model.navitime.CommuterPassDetail;
import com.vtes.model.navitime.Node;
import com.vtes.model.navitime.Route;
import com.vtes.model.navitime.Station;
import com.vtes.payload.response.ResponseData;
import com.vtes.payload.response.ResponseData.ResponseType;
import com.vtes.repository.CommuterPassRepo;
import com.vtes.sercurity.services.UserDetailsImpl;
import com.vtes.service.TransportInfomationServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class TransportAPIController {

	@Autowired
	private TransportInfomationServiceImpl transportService;

	@Autowired
	private CommuterPassRepo repo;

	@GetMapping(value = "/routes")
	public ResponseEntity<?> getRouteDetails(@RequestParam(name = "start", required = true) String start,
			@RequestParam(name = "goal", required = true) String goal,
			@RequestParam(name = "via", required = false) String[] via,
			@RequestParam(name = "commuterPass", required = false) boolean cpActive)
			throws ParameterInvalidException, CommuterPassNotFound {

		List<Route> routes = null;
		Map<String, Object> params = new HashMap<>();
		params.put("start", start);
		params.put("goal", goal);
		params.put("via", viaNodeJson(via));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Integer userId = userDetails.getId();
		if (cpActive) {
				if(!repo.findByUserId(userId).isPresent()) {
					log.warn("Not found commuter pass with user id {}", userId);
					throw new CommuterPassNotFound(userId);
				}
				CommuterPass commuterPass = repo.findByUserId(userId).get();

				params.put("commuter_pass", commuterPass.getViaDetail());
				routes = transportService.searchRoutes(params);

		} else {
			routes = transportService.searchRoutes(params);
		}

		return ResponseEntity.ok()
				.body(ResponseData.builder().code("").message("Success").type(ResponseType.INFO).data(routes).build());

	}

	@GetMapping("/stations")
	public ResponseEntity<?> getStationsByWord(@RequestParam(name = "stationName", required = true) String word)
			throws ParameterInvalidException {
		List<Station> stations = transportService.searchStationsByWord(word);
		return ResponseEntity.ok().body(
				ResponseData.builder().code("").message("Success").type(ResponseType.INFO).data(stations).build());
	}

	@GetMapping("/cp-routes")
	public ResponseEntity<?> getCommuterPass(@RequestParam(name = "start", required = true) String start,
			@RequestParam(name = "goal", required = true) String goal) throws ParameterInvalidException {

		Map<String, Object> params = new HashMap<>();
		params.put("start", start);
		params.put("goal", goal);

		List<CommuterPassDetail> cpRoutes = transportService.searchCommuterPassDetail(params);

		return ResponseEntity.ok().body(
				ResponseData.builder().code("").message("Success").type(ResponseType.INFO).data(cpRoutes).build());

	}

	private String viaNodeJson(String[] vias) {
		if (vias == null)
			return null;
		List<Node> nodes = new ArrayList<>();
		for (int i = 0; i < vias.length; i++) {
			nodes.add(new Node(vias[i]));
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(nodes);
		} catch (JsonProcessingException e) {
		}
		return null;
	}
}
