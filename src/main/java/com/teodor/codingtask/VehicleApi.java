package com.teodor.codingtask;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teodor.codingtask.model.Check;
import com.teodor.codingtask.model.CheckRequest;
import com.teodor.codingtask.model.InsuranceCheck;
import com.teodor.codingtask.model.Maintenance;

import reactor.core.publisher.Mono;

@RequestMapping("/")
public interface VehicleApi {

	@PostMapping("check")
	Mono<Check> checkVehicle(@Valid @RequestBody CheckRequest checkRequest);
	
	@GetMapping("/cars/{vin}")
	ResponseEntity<Maintenance> getMaintenance(@PathVariable String vin);
	
	@GetMapping("/accidents/report")
	ResponseEntity<InsuranceCheck> getInsurance(@RequestParam String vin);
}
