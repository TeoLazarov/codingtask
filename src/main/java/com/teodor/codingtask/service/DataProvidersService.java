package com.teodor.codingtask.service;

import com.teodor.codingtask.model.InsuranceCheck;
import com.teodor.codingtask.model.Maintenance;

public interface DataProvidersService {

	Maintenance getMaintenance(String vin);
	
	InsuranceCheck getInsurance(String vin);
}
