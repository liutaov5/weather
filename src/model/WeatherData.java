package model;

public class WeatherData {

	String date;
	String weather;
	String wind;
	String temperature;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	@Override
	public String toString() {
		return " [date=" + date + ", weather=" + weather + ", wind="
				+ wind + ", temperature=" + temperature + "]";
	}
	
}
