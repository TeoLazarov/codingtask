package com.teodor.codingtask.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.teodor.codingtask.model.InsuranceCheck;
import com.teodor.codingtask.model.Maintenance;
import com.teodor.codingtask.model.Report;

import reactor.core.publisher.Mono;

@Service
public class DataProvidersServiceImpl implements DataProvidersService {


    @Value("${my.insurance.provider}")
    String insuranceUrl;
    @Value("${my.maintenance.provider}")
    String maintenanceUrl;
    
    @Autowired
    private Random rand;

	@Override
	public Maintenance getMaintenance(String vin) {
		String[] values = {"very-low", "low", "medium", "high"};
		String value = values[rand.nextInt(values.length)];
		
		if (vin.equals("2")) {
			return null;
		}
		
		Maintenance maintenance = new Maintenance();
		maintenance.setMaintenanceFrequency(value);
		
		return maintenance;
	}

	@Override
	public InsuranceCheck getInsurance(String vin) {
		int claims = rand.nextInt(5 - 0) + 0;
		
		if (vin.equals("2")) {
			return null;
		}
		
		InsuranceCheck check = new InsuranceCheck();
		Report report = new Report();
		report.setClaims(claims);
		check.setReport(report);
		
		return check;
	}	
	
}
