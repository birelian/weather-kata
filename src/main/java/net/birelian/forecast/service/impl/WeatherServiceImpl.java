package net.birelian.forecast.service.impl;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.Optional;
import javax.inject.Inject;
import net.birelian.forecast.model.weather.Weather;
import net.birelian.forecast.model.weather.WeatherDay;
import net.birelian.forecast.service.HttpService;
import net.birelian.forecast.service.WeatherService;
import net.birelian.forecast.service.exception.ServiceException;

public class WeatherServiceImpl implements WeatherService {

	public static final int MAX_FUTURE_DAYS = 5;
	private static final String SERVICE_URL = "https://www.metaweather.com/api/location/";

	// Injected dependencies
	private final HttpService httpService;

	@Inject
	WeatherServiceImpl(final HttpService httpService) {

		this.httpService = httpService;
	}

	@Override
	public Optional<WeatherDay> getForecast(final String woeid, final LocalDate date) {

		final Weather weather = httpService.get(SERVICE_URL + woeid, Weather.class);

		return weather.getDays()
			.stream()
			.filter(weatherDay -> weatherDay.getDate().equals(date.toString()))
			.findFirst();

	}

	@Override
	public void validateDate(final LocalDate date) {

		assertNotNull("Please avoid null params. Use predict(cityName, wind) for today's forecast", date);

		if (date.isBefore(LocalDate.now())
			|| date.isAfter(LocalDate.now().plusDays(MAX_FUTURE_DAYS))) { //

			throw new ServiceException("Date is not valid");
		}
	}
}
