package net.birelian.forecast.model.weather;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class Weather implements Serializable {

	@SerializedName("consolidated_weather")
	private List<WeatherDay> days;

}
