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
	static String geocodeUri = "https://geocoding.geo.census.gov/geocoder/locations/address?benchmark=9&format=json&";
	static String zoneUri = "https://api.weather.gov/points/";

	@RequestMapping("/")
	public String home() {
		return getDetailedForecast("201+E+Randolph+St", "Chicago", "IL");
	}

	private String getDetailedForecast(String street, String city, String state) {
		RestTemplate restTemplate = new RestTemplate();
		String forecastUri = getForecastUri(street, city, state);
		JsonNode forecast = restTemplate.getForObject(forecastUri, JsonNode.class);
		String detailedForecast = forecast.get("properties").get("periods").get(0).get("detailedForecast").toString();
		return detailedForecast;
	}

	/* Returns a comma-separated co-ordinate for a given address*/
	 public String geocode(String street, String city, String state) {
		String addressPararms = "street="+street+"&city="+city+"&state="+state;

		RestTemplate restTemplate = new RestTemplate();
		JsonNode geocode = restTemplate.getForObject(geocodeUri+addressPararms, JsonNode.class);
		JsonNode coordinates = geocode.get("result").get("addressMatches").get(0).get("coordinates");
		String yx= coordinates.get("y")+","+coordinates.get("x");
		return yx;
	}

	/* Returns The Zone URI for doing the forecast*/
	public  String getForecastUri(String street, String city, String state) {
		String yxCoordinates = geocode(street, city, state);
		RestTemplate restTemplate = new RestTemplate();
		JsonNode zone = restTemplate.getForObject(zoneUri+yxCoordinates, JsonNode.class);
		String forecastUri = zone.get("properties").get("forecast").toString();
		forecastUri = forecastUri.substring(1, forecastUri.length()-1);
		return forecastUri;
	}

	public  static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}


}

