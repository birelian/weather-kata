package net.birelian.forecast.service.impl;

import static junit.framework.TestCase.assertTrue;
import static net.birelian.forecast.service.impl.WeatherServiceImpl.MAX_FUTURE_DAYS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import net.birelian.forecast.model.weather.Weather;
import net.birelian.forecast.model.weather.WeatherDay;
import net.birelian.forecast.service.HttpService;
import net.birelian.forecast.service.exception.ServiceException;
import org.junit.Test;
import org.mockito.Mockito;

public class WeatherServiceImplTest {

	private static final String CITY = "Shadow Moses";
	private static final LocalDate DATE = LocalDate.of(2019,10, 1);
	private static final String DATE_AS_STRING = "2019-10-01";

	private static final String WEATHER = "Winterfell cold";
	private static final Double WIND = 80d; // Windy as hell

	@Test
	public void getForecastShouldReturnTheForecastWhenNoErrors() {

		// Given
		HttpService httpService = Mockito.mock(HttpServiceImpl.class);

		Mockito
			.when(httpService.get(anyString(), any()))
			.thenReturn(createWeather());

		// When
		Optional<WeatherDay> forecast = new WeatherServiceImpl(httpService).getForecast(CITY, DATE);

		// Then
		assertTrue(forecast.isPresent());
		assertEquals(WEATHER,forecast.get().getWeather());
		assertEquals(WIND,forecast.get().getWind());
		assertEquals(DATE_AS_STRING,forecast.get().getDate());

	}

	@Test
	public void getForecastShouldReturnEmptyWhenForecastIsNotFound() {

		// Given
		HttpService httpService = Mockito.mock(HttpServiceImpl.class);

		Mockito
			.when(httpService.get(anyString(), any()))
			.thenReturn(createWeather());

		// When
		Optional<WeatherDay> forecast = new WeatherServiceImpl(httpService).getForecast(CITY, DATE.plusDays(1));

		assertFalse(forecast.isPresent());
	}

	@Test
	public void validateDateShouldNotThrowExceptionWhenDateIsInRage() {

		HttpService httpService = Mockito.mock(HttpServiceImpl.class);

		new WeatherServiceImpl(httpService).validateDate(LocalDate.now());

		// Nothing to assert
	}

	@Test(expected = ServiceException.class)
	public void validateDateShouldThrowExceptionForPastDate() {

		HttpService httpService = Mockito.mock(HttpServiceImpl.class);

		new WeatherServiceImpl(httpService).validateDate(LocalDate.now().minusDays(1));
	}

	@Test(expected = ServiceException.class)
	public void validateDateShouldThrowExceptionForAFutureDaysThatExceedsMaxNumberOfFutureDays() {

		HttpService httpService = Mockito.mock(HttpServiceImpl.class);

		new WeatherServiceImpl(httpService).validateDate(LocalDate.now().plusDays(MAX_FUTURE_DAYS + 1));
	}

	private Weather createWeather() {

		Weather weather = new Weather();

		weather.setDays(new ArrayList<>());

		weather.getDays().add(new WeatherDay(WEATHER, WIND, DATE_AS_STRING));

		return weather;
	}
}