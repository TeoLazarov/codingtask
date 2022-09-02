package com.teodor.codingtask.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Maintenance {

	@JsonAlias("maintenance_frequency")
	private String maintenanceFrequency;

	public String getMaintenanceFrequency() {
		return maintenanceFrequency;
	}

	public void setMaintenanceFrequency(String maintenanceFrequency) {
		this.maintenanceFrequency = maintenanceFrequency;
	}

}
