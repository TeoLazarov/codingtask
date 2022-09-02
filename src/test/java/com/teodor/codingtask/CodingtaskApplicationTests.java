package com.teodor.codingtask;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.UrlMatchingStrategy;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.teodor.codingtask.model.CheckRequest;
import com.teodor.codingtask.service.DataProvidersService;
import com.teodor.codingtask.service.VehicleService;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CodingtaskApplicationTests {
	
	private WireMockServer server;
	
	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	private VehicleService vehicleService;
	@MockBean
	private DataProvidersService dataProvidersService;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void when_check_withAllCommands_thenReturnResult() {
		UrlMatchingStrategy url = new UrlMatchingStrategy();
		url.contributeTo(new RequestPattern(RequestMethod.GET, "/check"));
		MappingBuilder m = new MappingBuilder(RequestMethod.GET, url); 
		server.stubFor(post(urlEqualTo("/check")).withRequestBody(equalToJson("{\n"
				+ "    \"vin\": \"1\",\n"
				+ "    \"features\": [\n"
				+ "        \"accident_free\",\n"
				+ "        \"maintenance\"\n"
				+ "    ]\n"
				+ "}")).willReturn(aResponse().withStatus(200).withBody("{\n"
						+ "    \"requestId\": \"asd\",\n"
						+ "    \"vin\": \"1\",\n"
						+ "    \"accidentFree\": false,\n"
						+ "    \"maintenanceScore\": \"poor\"\n"
						+ "}")));
		
		
	}
	
	@Test
	public void when_check_withAllCommands2_thenReturnResult() {
		CheckRequest req = new CheckRequest();
		List<String> cmds = new ArrayList<>();
		cmds.add("insurance");
		cmds.add("maintenance");
		
		req.setFeatures(cmds);
		req.setVin("1");
		
		webTestClient.post().uri("check")
		.bodyValue(req)
		.exchange()
		.expectStatus()
		.isOk()
		.expectBody()
		.jsonPath("$.vin").isEqualTo(req.getVin())
		.jsonPath("$.accidentFree").isNotEmpty()
		.jsonPath("$.maintenanceScore").isNotEmpty();
		
	}
	
}

