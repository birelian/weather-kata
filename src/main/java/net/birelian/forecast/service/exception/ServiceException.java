package net.birelian.forecast.service.exception;

public class ServiceException extends RuntimeException {

	public ServiceException(String message) {

		super(message);
	}

	public ServiceException(String message, Throwable throwable) {

		super(message, throwable);
	}
}
