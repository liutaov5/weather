package model;

import java.util.List;

public class Results {

	String currentCity;
	String pm25;
	List<Index> index;
	List<Weather> weather_data;
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public String getPm25() {
		return pm25;
	}
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	public List<Index> getIndex() {
		return index;
	}
	public void setIndex(List<Index> index) {
		this.index = index;
	}
	public List<Weather> getWeather_data() {
		return weather_data;
	}
	public void setWeather_data(List<Weather> weather_data) {
		this.weather_data = weather_data;
	}

	
}
