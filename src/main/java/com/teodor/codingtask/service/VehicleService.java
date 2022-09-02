package com.teodor.codingtask.service;

import com.teodor.codingtask.model.Check;
import com.teodor.codingtask.model.CheckRequest;

import reactor.core.publisher.Mono;

public interface VehicleService {

	Mono<Check> checkVehicle(CheckRequest checkRequest);
}
