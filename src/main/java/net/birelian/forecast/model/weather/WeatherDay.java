package net.birelian.forecast.model.weather;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import lombok.Data;

@Data
public class WeatherDay implements Serializable {

	@SerializedName("weather_state_name")
	private String weather;

	@SerializedName("wind_speed")
	private Double wind;

	@SerializedName("applicable_date")
	private String date;

}
