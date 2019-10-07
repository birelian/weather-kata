package net.birelian.forecast.service.impl;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import java.util.Optional;
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

		Optional<City> city = new CityServiceImpl(httpService).getCity(CITY);

		assertTrue(city.isPresent());
		assertEquals(CITY,city.get().getTitle());
		assertEquals(WOEID,city.get().getWoeid());

	}

	@Test(expected = ServiceException.class)
	public void getCityShouldThrowExceptionWhenNumberOfCitiesIsGreaterThanOne() {

		HttpService httpService = Mockito.mock(HttpServiceImpl.class);

		Mockito
			.when(httpService.get(anyString(), any()))
			.thenReturn(createCities(2));

		new CityServiceImpl(httpService).getCity(CITY);

	}

	@Test
	public void getCityShouldReturnEmptyWhenNumberOfCitiesIsZero() {

		HttpService httpService = Mockito.mock(HttpServiceImpl.class);

		Mockito
			.when(httpService.get(anyString(), any()))
			.thenReturn(createCities(0));

		Optional<City> city = new CityServiceImpl(httpService).getCity(CITY);

		assertFalse(city.isPresent());

	}

	private City[] createCities(int quantity) {

		City[] cities = new City[quantity];

		for (int i = 0; i < quantity; i++) {
			cities[i] = new City(CITY, WOEID);
		}

		return cities;
	}
}