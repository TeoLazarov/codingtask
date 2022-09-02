package com.teodor.codingtask.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.teodor.codingtask.model.Check;
import com.teodor.codingtask.model.CheckRequest;
import com.teodor.codingtask.model.InsuranceCheck;
import com.teodor.codingtask.model.Maintenance;

import reactor.core.publisher.Mono;

@Service
public class VehicleServiceImpl implements VehicleService {
	
	@Autowired
	private WebClient webClient;
	
	@Override
	public Mono<Check> checkVehicle(CheckRequest checkRequest) {
		Mono<InsuranceCheck> insurance = null;
		Mono<Maintenance> maintenance = null;

		for (String feature : checkRequest.getFeatures()) {
			switch (feature) {
			case "accident_free":
				if (insurance == null) {
					Mono<ResponseEntity<InsuranceCheck>> check = webClient.get()
							.uri(String.format("http://localhost:8080/accidents/report?vin=%s", checkRequest.getVin()))
							.retrieve()
							.onStatus(HttpStatus::isError, res -> {
								return Mono.just(new ResponseStatusException(400, "Data not found", null));
							})
							.toEntity(InsuranceCheck.class)
							.retry(3);
					
					insurance = check.map(o -> o.getBody());
				}
				break;
			case "maintenance":
				if (maintenance == null) {
					Mono<ResponseEntity<Maintenance>> check = webClient.get()
							.uri(String.format("http://localhost:8080/cars/%s", checkRequest.getVin()))
							.retrieve()
							.onStatus(HttpStatus::isError, res -> {
								return Mono.error(new ResponseStatusException(400, "Data not found", null));
							})
							.toEntity(Maintenance.class)
							.retry(3);
					
					maintenance = check.map(o -> o.getBody());
				}
				break;
				
				default:
					return Mono.error(new ResponseStatusException(400, "Command not supported", null));
			}
		}
		
		Mono<Check> result;
		if (insurance != null && maintenance != null) {
			result = insurance.zipWith(maintenance)
					.map(t -> new Check(UUID.randomUUID().toString(), checkRequest.getVin(), t.getT1().getReport().getClaims() > 0 ? false : true, t.getT2().getMaintenanceFrequency()));
		} else if (insurance != null) {
			result = insurance
					.map(t -> new Check(UUID.randomUUID().toString(), checkRequest.getVin(), t.getReport().getClaims() > 0 ? false : true, null));
			
		} else if (maintenance != null) {
			result = maintenance
					.map(t -> new Check(UUID.randomUUID().toString(), checkRequest.getVin(), null, t.getMaintenanceFrequency()));
			
		} else {
			return Mono.error(new ResponseStatusException(400, "Something went wrong", null));
		}

		return result;
	}

}
