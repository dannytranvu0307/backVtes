package com.vtes.service;

import java.util.List;
import java.util.Map;

import com.vtes.model.navitime.CommuterPassDetail;
import com.vtes.model.navitime.Route;
import com.vtes.model.navitime.Station;
/*
 * @Author : ChienVti
 * Define methods of obtaining 3rd party information
 * */
public interface TransportInfomationService {
	
	public List<Route> searchRoutes(Map<String, Object> params);
	
	public List<Station> searchStationsByWord(String word);
	
	public List<CommuterPassDetail> searchCommuterPassDetail(Map<String, Object> params);


}
