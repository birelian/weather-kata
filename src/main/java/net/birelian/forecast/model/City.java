package net.birelian.forecast.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class City implements Serializable {

	private String title;
	private String woeid;

}
