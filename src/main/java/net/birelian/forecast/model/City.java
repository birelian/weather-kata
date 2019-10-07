package net.birelian.forecast.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class City implements Serializable {

	private String title;
	private String woeid;

}
