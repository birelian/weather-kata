package net.birelian.forecast.service.impl;

import java.util.Optional;
import javax.inject.Inject;
import net.birelian.forecast.model.City;
import net.birelian.forecast.service.CityService;
import net.birelian.forecast.service.HttpService;
import net.birelian.forecast.service.exception.ServiceException;

/**
 * City service implementation that uses Metaweather as a source of information
 */
public class CityServiceImpl implements CityService {

	private static final String SERVICE_URL = "https://www.metaweather.com/api/location/search/?query=";

	// Injected dependencies
	private final HttpService httpService;

	@Inject
	CityServiceImpl(final HttpService httpService) {

		this.httpService = httpService;
	}

	@Override
	public Optional<City> getCity(final String cityName) {

		final City[] cities = httpService.get(SERVICE_URL + cityName, City[].class);

		if (cities.length == 0) {
			return Optional.empty(); // City not found

		} else if (cities.length > 1) {
			throw new ServiceException("Several cities match the name " + cityName);

		} else  {
			return Optional.of(cities[0]);
		}
	}
}
