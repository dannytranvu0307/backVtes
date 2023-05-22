package com.vtes.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtes.entity.Fare;
import com.vtes.exception.FareNotFoundException;
import com.vtes.model.FareDTO;
import com.vtes.repository.FareRepo;

@Service
public class FareService {

	@Autowired
	private FareRepo repo;

	public FareDTO saveFareRecord(FareDTO fareDTO) throws ParseException {
		Fare savedFare = repo.save(fareDTO.convertToFare());
		return convertFromFare(savedFare);

	}

	public void deleteFareRecord(Integer recordId) throws FareNotFoundException {
		repo.deleteById(recordId);
	}

	public boolean isExistFare(Integer id) {
		return repo.countById(id) > 0 ? true : false;
	}

	public FareDTO convertFromFare(Fare fare) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return FareDTO.builder()
				.id(fare.getId())
				.userId(fare.getUser().getId())
				.departure(fare.getDeparture())
				.visitLocation(fare.getVisitLocation())
				.destination(fare.getDestination())
				.fee(fare.getFee())
				.payMethod(fare.getPayMethod())
				.commuterPass(fare.getUseCommuterPass())
				.roundTrip(fare.getIsRoundTrip())
				.transportation(fare.getTransportation())
				.visitDate(dateFormat.format(fare.getVisitDate()))
				.build();

	}

}
