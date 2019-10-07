package net.birelian.forecast.service;

import java.time.LocalDate;
import net.birelian.forecast.model.weather.WeatherDay;

/**
 * Interface for services that provide forecast about a Woeid (for example, a city)
 */
public interface WeatherService {

	/**
	 * Get the forecast
	 *
	 * @param woeid The woeid
	 * @param date The desired date
	 *
	 * @return The forecast for the given day
	 */
	WeatherDay getForecast(String woeid, LocalDate date);

	/**
	 * Validate that a date is valid for getting the forecast
	 *
	 * @param date The date
	 */
	void validateDate(LocalDate date);
}
