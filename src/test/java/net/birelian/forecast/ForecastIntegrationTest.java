package net.birelian.forecast;

import static junit.framework.TestCase.assertTrue;
import static net.birelian.forecast.service.impl.WeatherServiceImpl.MAX_FUTURE_DAYS;
import static org.junit.Assert.assertFalse;

import com.google.inject.Guice;
import java.time.LocalDate;
import net.birelian.forecast.config.GuiceConfigurationModule;
import net.birelian.forecast.service.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

/**
 * Test services integration
 */
public class ForecastIntegrationTest {

	private static final String CITY_NAME = "Madrid";

	private static final boolean NO_WIND = false;
	private static final boolean WIND = true;

	private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
	private static final LocalDate NON_VALID_FUTURE_DATE = LocalDate.now().plusDays(MAX_FUTURE_DAYS + 1);
	private static final LocalDate VALID_FUTURE_DATE = LocalDate.now().plusDays(MAX_FUTURE_DAYS);

	@Test(expected = AssertionError.class)
	public void findTheWeatherWithANullDateShouldThrowException() {

		getForecastInstance().predict(CITY_NAME, null, NO_WIND);
	}

	@Test(expected = AssertionError.class)
	public void findTheWindWithANullDateShouldThrowException() {

		getForecastInstance().predict(CITY_NAME, null, WIND);
	}

	@Test
	public void findTheWeatherOfTodayShouldWork() {

		String prediction = getForecastInstance().predict(CITY_NAME, NO_WIND);

		assertTrue("Forecast should return a String when it works. Exception otherwise", StringUtils.isNotBlank(prediction));
		assertFalse("Forecast should not be a number when wind parameter is false", NumberUtils.isParsable(prediction));
	}

	@Test
	public void findTheWindOfTodayShouldWork() {

		String prediction = getForecastInstance().predict(CITY_NAME, WIND);

		assertTrue("Forecast should return a String when it works. Exception otherwise", StringUtils.isNotBlank(prediction));
		assertTrue("Forecast wind should be parsable as Double", NumberUtils.isParsable(prediction));
	}

	@Test(expected = ServiceException.class)
	public void findTheWeatherForAPastDateShouldThrowException() {

		getForecastInstance().predict(CITY_NAME, YESTERDAY, NO_WIND);
	}

	@Test(expected = ServiceException.class)
	public void findTheWindForAPastDateShouldThrowException() {

		getForecastInstance().predict(CITY_NAME, YESTERDAY, WIND);
	}

	@Test
	public void findTheWeatherForAValidFutureDateShouldReturnTheWeather() {

		String prediction = getForecastInstance().predict(CITY_NAME, VALID_FUTURE_DATE, NO_WIND);

		assertTrue("Forecast should return a String when it works. Exception otherwise", StringUtils.isNotBlank(prediction));
		assertFalse("Forecast should not be a number when wind parameter is false", NumberUtils.isParsable(prediction));
	}

	@Test
	public void findTheWindForAValidFutureDateShouldReturnTheWeather() {

		String prediction = getForecastInstance().predict(CITY_NAME, VALID_FUTURE_DATE, WIND);

		assertTrue("Forecast should return a String when it works. Exception otherwise", StringUtils.isNotBlank(prediction));
		assertTrue("Forecast wind should be parsable as Double", NumberUtils.isParsable(prediction));
	}

	@Test(expected = ServiceException.class)
	public void findTheWeatherForANonValidFutureDateShouldThrowException() {

		getForecastInstance().predict(CITY_NAME, NON_VALID_FUTURE_DATE, NO_WIND);
	}

	@Test(expected = ServiceException.class)
	public void findTheWindForANonValidFutureDateShouldThrowException() {

		getForecastInstance().predict(CITY_NAME, NON_VALID_FUTURE_DATE, WIND);
	}

	private Forecast getForecastInstance() {

		return Guice.createInjector(new GuiceConfigurationModule()).getInstance(Forecast.class);
	}
}
