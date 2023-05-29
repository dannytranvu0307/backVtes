package com.vtes.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtes.model.navitime.CommuterPassDetail;
import com.vtes.model.navitime.CommuterPassRoute;
import com.vtes.model.navitime.Link;
import com.vtes.model.navitime.Route;
import com.vtes.model.navitime.RouteSectionItem;
import com.vtes.model.navitime.RouteSummary;
import com.vtes.model.navitime.Station;
import com.vtes.model.navitime.SubRoute;

import lombok.extern.slf4j.Slf4j;

/*
 * Author :chien.tranvan
 * Date: 2023/05/21
 * 
 * This get params from client and call third-part API then convert response data to navitime model
 * */

@Service
@Slf4j
public class TransportInfomationServiceImpl implements TransportInfomationService {
	private static final String STATION = "station";
	private static final String STATION_JA = "駅";
	private static final String ITEMS = "items";
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	private static final String POINT = "point";
	private static final String MOVE = "move";
	private static final Integer RESULT_LIMIT = 100;

	@Autowired
	private TotalNaviApiConnect totalnavi;

	@Autowired
	private TransportApiConnect transport;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	public List<Route> searchRoutes(Map<String, Object> params) {
		List<Route> items = null;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String formattedDateTime = sdf.format(new Date());

		params.put("start_time", formattedDateTime);

		ResponseEntity<String> json = totalnavi.searchRoutes(params);
		String jsonString = json.getBody();

		try {
			JsonNode node = objectMapper.readTree(jsonString);
			JsonNode itemsNode = node.get(ITEMS);

			items = objectMapper.readValue(itemsNode.toString(), new TypeReference<List<Route>>() {
			});
		} catch (JsonProcessingException e) {
			log.error("Has error when call 3rd API");
		}

		return items;
	}

	// Call the api to a 3rd party and filter out the points that are train stations
	public List<Station> searchStationsByWord(Map<String, Object> params) {

		List<Station> responseData = null;
		params.put("limit", RESULT_LIMIT);
		String jsonString = transport.getStationDetail(params).getBody();

		try {
			JsonNode node = objectMapper.readTree(jsonString);
			JsonNode itemsNode = node.get(ITEMS);
			responseData = objectMapper.readValue(itemsNode.toString(), new TypeReference<List<Station>>() {
			});
		} catch (JsonProcessingException e) {
			log.error("Has error when call 3rd API");
		}
		List<Station> stations = new ArrayList<>();
		if (responseData != null) {
			stations = responseData.stream()
					.peek(s -> s.setName(s.getName() + STATION_JA))
					.filter(s -> s.getTypes()
							.contains(STATION)).collect(Collectors.toList());
		}
		return stations;
	}

	private List<Station> getStations(Map<String, Object> params) {
		params.put("limit", RESULT_LIMIT);
		String jsonString = transport.getStationDetail(params).getBody();

		List<Station> responseData = null;
		try {
			JsonNode node = objectMapper.readTree(jsonString);
			JsonNode itemsNode = node.get(ITEMS);
			responseData = objectMapper.readValue(itemsNode.toString(), new TypeReference<List<Station>>() {
			});
		} catch (JsonProcessingException e) {
			log.error("Has error when call 3rd API");
		}
		List<Station> stations = new ArrayList<>();
		if (responseData != null) {
			stations = responseData.stream().peek(s -> s.setName(s.getName() + STATION_JA))
					.filter(s -> s.getTypes().contains(STATION)).collect(Collectors.toList());
		}
		return stations;

	}

	public List<CommuterPassRoute> searchCommuterPassDetail(Map<String, Object> params) {
		List<Route> routes = searchRoutes(params);
		return convertCommuterPass(routes);

	}

	// Get route details and convert to a commuter pass used for next request
	private List<CommuterPassRoute> convertCommuterPass(List<Route> routes) {
	    List<CommuterPassRoute> cpDetails = routes.stream().map(route -> {
	        CommuterPassRoute cpRoute = new CommuterPassRoute();
	        cpRoute.setSummary(route.getSummary());
	        cpRoute.setSections(route.getSections());

	        List<String> cpLink = route.getSections().stream()
	                .filter(sc -> MOVE.equals(sc.getType()) && sc.getTransport() != null)
	                .flatMap(sc -> Optional.ofNullable(sc.getTransport().getLinks())
	                        .orElse(Collections.emptyList())
	                        .stream()
	                        .map(Link::generateViaJson))
	                .collect(Collectors.toList());
	        cpRoute.setCommuterPassLink(cpLink);

	        return cpRoute;
	    }).collect(Collectors.toList());

	    return cpDetails;
	}


}
