package com.teodor.codingtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.teodor.codingtask.VehicleApi;
import com.teodor.codingtask.model.Check;
import com.teodor.codingtask.model.CheckRequest;
import com.teodor.codingtask.model.InsuranceCheck;
import com.teodor.codingtask.model.Maintenance;
import com.teodor.codingtask.service.DataProvidersService;
import com.teodor.codingtask.service.VehicleService;

import reactor.core.publisher.Mono;

@RestController
public class VehicleController implements VehicleApi {

	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private DataProvidersService dataProvidersService;
	
	@Override
	public Mono<Check> checkVehicle(CheckRequest checkRequest) {
		return this.vehicleService.checkVehicle(checkRequest);
	}

	@Override
	public ResponseEntity<Maintenance> getMaintenance(String vin) {
		Maintenance result = this.dataProvidersService.getMaintenance(vin);
		
		if (result == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Maintenance>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<InsuranceCheck> getInsurance(String vin) {
		InsuranceCheck result = this.dataProvidersService.getInsurance(vin);
		
		if (result == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<InsuranceCheck>(result, HttpStatus.OK);
	}


}
