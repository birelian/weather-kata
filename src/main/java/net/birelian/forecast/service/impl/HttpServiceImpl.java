package net.birelian.forecast.service.impl;

import com.google.gson.Gson;
import java.io.IOException;
import net.birelian.forecast.service.HttpService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.ResponseBody;
import net.birelian.forecast.service.exception.ServiceException;

/**
 * Service for HTTP requests
 */
public class HttpServiceImpl implements HttpService {

	private static final OkHttpClient HTTP_CLIENT = new OkHttpClient(); // Thread-safe. May be shared

	// Request.Builder may not be thread-safe. Use a ThreadLocal to avoid concurrency problems
	private ThreadLocal<Request.Builder> requestBuilder = ThreadLocal.withInitial(Builder::new);

	// GSON not be thread-safe. Use a ThreadLocal to avoid concurrency problems
	private ThreadLocal<Gson> gson = ThreadLocal.withInitial(Gson::new);

	@Override
	public <T> T get(String url, Class<T> classOfT) {

		Request request = createRequest(url);

		return convertResponse(classOfT, sendRequest(request));
	}

	private Request createRequest(String url) {

		return requestBuilder.get()
			.url(url)
			.build();
	}

	private ResponseBody sendRequest(Request request) {

		try {

			return HTTP_CLIENT.newCall(request)
				.execute()
				.body();

		} catch (IOException exception) {
			throw new ServiceException("Error while sending request", exception);
		}
	}

	private <T> T convertResponse(Class<T> classOfT, ResponseBody responseBody) {

		if (responseBody == null) {
			throw new ServiceException("Response is null");
		}

		try {
			return gson.get().fromJson(responseBody.string(), classOfT);

		} catch (Exception e) {
			throw new ServiceException("Unable to convert response");
		}
	}
}
