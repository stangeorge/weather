package com.stanleygeorge.weather;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherApplicationTests {

	@Autowired
	private  WeatherApplication weatherApplication;

	@Test
	public void geocode() throws Exception {
		String expected="41.884277,-87.62297";
		String actual = weatherApplication.geocode("201+E+Randolph+St", "Chicago", "IL");
		assert(expected).equals(actual);
	}

	@Test
	public void getForecastUri() throws Exception {
		String expected = "https://api.weather.gov/gridpoints/LOT/75,72/forecast";
		String actual = weatherApplication.getForecastUri("201+E+Randolph+St", "Chicago", "IL");
		assert(expected).equals(actual);
	}

}

