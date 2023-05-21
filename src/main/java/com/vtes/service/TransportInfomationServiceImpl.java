package com.vtes.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtes.model.navitime.CommuterPassDetail;
import com.vtes.model.navitime.Route;
import com.vtes.model.navitime.RouteSectionItem;
import com.vtes.model.navitime.RouteSummary;
import com.vtes.model.navitime.Station;
import com.vtes.model.navitime.SubRoute;

@Service
public class TransportInfomationServiceImpl implements TransportInfomationService {
	private Logger LOGGER = LoggerFactory.getLogger(TransportInfomationServiceImpl.class);

	@Autowired
	private TotalNaviApiConnect totalnavi;

	@Autowired
	private TransportApiConnect transport;

	@Autowired
	private ObjectMapper objectMapper;

	public List<Route> searchRoutes(String start, String goal) {
		List<Route> items = null;
		Date currentDate = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String formattedDateTime = sdf.format(currentDate);

		ResponseEntity<String> json = totalnavi.getTrainFare(start, goal, formattedDateTime);
		String jsonString = json.getBody();

		try {
			JsonNode node = objectMapper.readTree(jsonString);
			JsonNode itemsNode = node.get("items");

			items = objectMapper.readValue(itemsNode.toString(), new TypeReference<List<Route>>() {
			});
		} catch (JsonProcessingException e) {
			LOGGER.error("Has error when call 3rd API");
		}

		return items;
	}

	public List<Station> searchStationsByWord(String word) {

		List<Station> responseData = null;

		String jsonString = transport.getStationDetail(word).getBody();

		try {
			JsonNode node = objectMapper.readTree(jsonString);
			JsonNode itemsNode = node.get("items");
			responseData = objectMapper.readValue(itemsNode.toString(), new TypeReference<List<Station>>() {
			});
		} catch (JsonProcessingException e) {
			LOGGER.error("Has error when call 3rd API");
		}
		List<Station> stations = new ArrayList<>();
		if (responseData != null) {
			stations = responseData.stream().filter(s -> s.getTypes().contains("station")).collect(Collectors.toList());
		}

		return stations;
	}

	public List<CommuterPassDetail> searchCommuterPassDetail(String start, String goal) {
		List<Route> routes = searchRoutes(start, goal);
		return convertCommuterPass(routes);
		
	}

	private List<CommuterPassDetail> convertCommuterPass(List<Route> routes) {
		AtomicInteger counter = new AtomicInteger(1);
		List<CommuterPassDetail> cpDetails = routes.stream().map(route -> {
			CommuterPassDetail cp = new CommuterPassDetail();

			RouteSummary summary = route.getSummary();
			cp.setStart(summary.getStart());
			cp.setGoal(summary.getGoal());
			cp.setNo(counter.getAndIncrement());
			
			
			Set<String> viaStations = new HashSet<>();
			List<String> symbols = new ArrayList<String>();
			int index = 0;
			List<RouteSectionItem> sections = route.getSections();
			for (int i = 0; i< sections.size(); i++) {	
				RouteSectionItem sc = sections.get(i);
				if(sc.getType().equals("point")) {
					viaStations.add(sc.getName());
					if(index % 2 == 0) {
						if (sc.getNumbering().getDeparture().size() == 1) {
							symbols.add(sc.getNumbering().getDeparture().get(1).getSymbol());	
						}						
					}else
					if (sc.getNumbering().getArrival().size() == 1) {
						symbols.add(sc.getNumbering().getArrival().get(1).getSymbol());
					}
				}
			}
			cp.setViaStations(viaStations);
			List<SubRoute> subRoutes = route.getSections().stream().map(sc -> {
				SubRoute subRoute = new SubRoute();
				if ("move".equals(sc.getType()) && sc.getTransport() != null) {
					subRoute.setLineColor(sc.getTransport().getLineColor());

					List<String> linkJson = Optional.ofNullable(sc.getTransport().getLinks()).map(
							links -> links.stream().map(link -> link.generateViaJson()).collect(Collectors.toList()))
							.orElse(Collections.emptyList());

					subRoute.setLinks(linkJson);
				}
				return subRoute;
			}).collect(Collectors.toList());
			List<SubRoute> filteredSubRoutes = new ArrayList<>();
			for(int i = 0 ; i < subRoutes.size(); i++) {
				SubRoute rt = subRoutes.get(i);
				if(rt.getLinks() != null) {
					rt.setLineSymbol(symbols.get(i));
					filteredSubRoutes.add(rt);
				}
			}
			
			cp.setViaRoutes(filteredSubRoutes);

			return cp;
		}).collect(Collectors.toList());
		
		return cpDetails;
	}

}
