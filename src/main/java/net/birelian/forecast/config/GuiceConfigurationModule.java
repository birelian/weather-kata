package net.birelian.forecast.config;

import com.google.inject.AbstractModule;
import net.birelian.forecast.service.CityService;
import net.birelian.forecast.service.HttpService;
import net.birelian.forecast.service.WeatherService;
import net.birelian.forecast.service.impl.CityServiceImpl;
import net.birelian.forecast.service.impl.HttpServiceImpl;
import net.birelian.forecast.service.impl.WeatherServiceImpl;

/**
 * Guice dependency injection configuration module
 */
public class GuiceConfigurationModule extends AbstractModule {

	@Override
	protected void configure() {

		// Bindings
		bind(HttpService.class).to(HttpServiceImpl.class);
		bind(CityService.class).to(CityServiceImpl.class);
		bind(WeatherService.class).to(WeatherServiceImpl.class);

	}
}

