package com.stanleygeorge.weather;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherApplicationTests {
	@Autowired
	private  WeatherApplication weatherApplication;

	@Autowired
	private WebApplicationContext context;


	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

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

	@Test
	public void getOffice() throws Exception {
		String expected = weatherApplication.home();
		mockMvc.perform(get("/office?street={street}&city={city}&state={$state}",
				"201+E+Randolph", "Chicago", "IL"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"))
				.andExpect(content().string(expected));
	}

}

