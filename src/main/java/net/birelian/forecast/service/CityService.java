package net.birelian.forecast.service;

import net.birelian.forecast.model.City;

/**
 * Interface for services that provide information about a given city
 */
public interface CityService {

	/**
	 * Get info about a city
	 *
	 * @param cityName The city name
	 *
	 * @return The city info
	 */
	City getCity(String cityName);
}
