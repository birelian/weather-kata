package net.birelian.forecast;

import java.time.LocalDate;
import net.birelian.forecast.model.weather.WeatherDay;
import net.birelian.forecast.service.CityService;
import net.birelian.forecast.service.WeatherService;
import net.birelian.forecast.service.impl.CityServiceImpl;
import net.birelian.forecast.service.impl.WeatherServiceImpl;

class Forecast {

	private final CityService cityService = new CityServiceImpl();
	private final WeatherService weatherService = new WeatherServiceImpl();

	/**
	 * Get the weather prediction for today
	 *
	 * @param cityName The city name
	 * @param wind If true, get wind speed. Get weather otherwise
	 *
	 * @return The weather prediction for today
	 */
	String predict(final String cityName, final boolean wind) {

		return predict(cityName, LocalDate.now(), wind);
	}

	/**
	 * Get the weather prediction for a given day between today and the next 5 days
	 *
	 * @param cityName The city name
	 * @param date The date
	 * @param wind If true, get wind speed. Get weather otherwise
	 *
	 * @return The weather prediction for today
	 */
	String predict(final String cityName, LocalDate date, final boolean wind) {

		weatherService.validateDate(date);

		// Get the weather for the given day
		final WeatherDay weatherDay = weatherService.getForecast(cityService.getCity(cityName)
			.getWoeid(), date, wind);

		return wind ?
			weatherDay.getWind().toString() :
			weatherDay.getWeather();

	}
}
