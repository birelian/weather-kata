package net.birelian.forecast.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import net.birelian.forecast.model.City;
import net.birelian.forecast.service.HttpService;
import net.birelian.forecast.service.exception.ServiceException;
import org.junit.Test;
import org.mockito.Mockito;

public class CityServiceImplTest {

	private static final String CITY = "Shadow Moses";
	private static final String WOEID = "Woeid";

	@Test
	public void getCityShouldGetTheCityWhenNoErrors() {

		HttpService httpService = Mockito.mock(HttpServiceImpl.class);

		Mockito
			.when(httpService.get(anyString(), any()))
			.thenReturn(createCities(1));

		City city = new CityServiceImpl(httpService).getCity(CITY);

		assertEquals(CITY, city.getTitle());
		assertEquals(WOEID, city.getWoeid());

	}

	@Test(expected = ServiceException.class)
	public void getCityShouldThrowExceptionWhenNumberOfCitiesIsGreaterThanOne() {

		HttpService httpService = Mockito.mock(HttpServiceImpl.class);

		Mockito
			.when(httpService.get(anyString(), any()))
			.thenReturn(createCities(2));

		new CityServiceImpl(httpService).getCity(CITY);

	}

	@Test(expected = ServiceException.class)
	public void getCityShouldThrowExceptionWhenNumberOfCitiesIsZero() {

		HttpService httpService = Mockito.mock(HttpServiceImpl.class);

		Mockito
			.when(httpService.get(anyString(), any()))
			.thenReturn(createCities(0));

		new CityServiceImpl(httpService).getCity(CITY);

	}

	private City[] createCities(int quantity) {

		City[] cities = new City[quantity];

		for (int i = 0; i < quantity; i++) {
			cities[i] = new City(CITY, WOEID);
		}

		return cities;
	}
}