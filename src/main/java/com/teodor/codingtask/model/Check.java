package com.teodor.codingtask.model;

public class Check {

	private String requestId;
	private String vin;
	private Boolean accidentFree;
	private String maintenanceScore;

	public Check() {
	}
	
	public Check(String requestId, String vin, Boolean accidentFree, String maintenanceScore) {
		this.requestId = requestId;
		this.vin = vin;
		this.accidentFree = accidentFree;
		this.setMaintenanceScore(maintenanceScore);
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Boolean getAccidentFree() {
		return accidentFree;
	}

	public void setAccidentFree(Boolean accidentFree) {
		this.accidentFree = accidentFree;
	}

	public String getMaintenanceScore() {
		return maintenanceScore;
	}

	public void setMaintenanceScore(String maintenanceScore) {
		if (maintenanceScore != null) {
			switch (maintenanceScore) {
			case "very-low":
			case "low":
				this.maintenanceScore = "poor";
				break;
			case "medium":
				this.maintenanceScore = "average";
				break;
			case "high":
				this.maintenanceScore = "good";
				break;
			}
		} else {
			this.maintenanceScore = maintenanceScore;
		}
	}

}
