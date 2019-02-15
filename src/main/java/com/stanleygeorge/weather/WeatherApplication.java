package com.stanleygeorge.weather;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class WeatherApplication {
	static String street = "";
	static String city = "Chicago";
	static String state = "IL";
	static String address = "street="+street+"&city="+city+"&state="+state;
	static String geocodeUri = "https://geocoding.geo.census.gov/geocoder/locations/address?benchmark=9&format=json&"
			+address;
	static String zoneUri = "https://api.weather.gov/points/";

	@RequestMapping("/")
	public String home() {
		RestTemplate restTemplate = new RestTemplate();
		//String result = restTemplate.getForObject(geocodeUri, String.class);
		JsonNode geocode = restTemplate.getForObject(geocodeUri, JsonNode.class);
		JsonNode coordinates = geocode.get("result").get("addressMatches").get(0).get("coordinates");
		String yx= coordinates.get("y")+","+coordinates.get("x");

		JsonNode zone = restTemplate.getForObject(zoneUri+yx, JsonNode.class);
		String forecastUri = zone.get("properties").get("forecast").toString();
		forecastUri = forecastUri.substring(1, forecastUri.length()-1);

		JsonNode forecast = restTemplate.getForObject(forecastUri, JsonNode.class);
		String detailedForecast = forecast.get("properties").get("periods").get(0).get("detailedForecast").toString();
		return detailedForecast;
	}


	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

}

