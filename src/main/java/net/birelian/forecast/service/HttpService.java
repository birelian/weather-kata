package net.birelian.forecast.service;

/**
 * Interface for services that provide HTTP functionality
 */
public interface HttpService {

	/**
	 * Get an URL
	 *
	 * @param url The url
	 * @param classOfT The class of the response
	 * @param <T> The generic type of the response
	 *
	 * @return The fetched response
	 */
	<T> T get(String url, Class<T> classOfT);
}
