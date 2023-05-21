package com.vtes.service;

import java.util.List;

import com.vtes.model.navitime.CommuterPassDetail;
import com.vtes.model.navitime.Route;
import com.vtes.model.navitime.Station;
/*
 * @Author : ChienVti
 * Define methods of obtaining 3rd party information
 * */
public interface TransportInfomationService {
	
	public List<Route> searchRoutes(String start, String goal);
	
	public List<Station> searchStationsByWord(String word);
	
	public List<CommuterPassDetail> searchCommuterPassDetail(String start, String goal);


}
