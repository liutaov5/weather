package com.example.weather;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import model.JSONDataType;
import model.Results;
import model.Weather;
import model.WeatherData;
import util.HttpCallbackListener;
import util.HttpUtil;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class WeatherView extends Activity {

	private TextView weather1;
	private TextView weather2;
	private TextView weather3;
	private TextView weather4;
	private TextView weatherText;
	private String city;
	private List<Weather> weathers;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_view);
		weather1=(TextView)findViewById(R.id.weather1);
		weather2=(TextView)findViewById(R.id.weather2);
		weather3=(TextView)findViewById(R.id.weather3);
		weather4=(TextView)findViewById(R.id.weather4);
		weatherText=(TextView)findViewById(R.id.weather_text);
		
		Intent intent=getIntent();
		city=intent.getStringExtra("city");
		weatherText.setText(city);
		getWeather(city);
	}
	/**
	 * 获取天气信息
	 * @param city
	 */
	public void getWeather(String city){
		try {
			//要对汉字进行字符编码
			city=URLEncoder.encode(city,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//百度天气api地址
		String address="http://api.map.baidu.com/telematics/v3/weather?location="+city+
				"&output=json&ak=KNCgGjkUCfNdV5ip9jjsOqmi";
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				//用GSON解析JSON数据
				Gson gson=new Gson();
				JSONDataType jsonDataType=gson.fromJson(response, JSONDataType.class);
				List<Results> results=jsonDataType.getResults();
				Results result= results.get(0);
				weathers=result.getWeather_data();
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						int i=0;
						for(Weather weather:weathers){
							++i;
							WeatherData weatherData=new WeatherData();
							weatherData.setDate(weather.getDate());
							weatherData.setTemperature(weather.getTemperature());
							weatherData.setWeather(weather.getWeather());
							weatherData.setWind(weather.getWind());
							//System.out.println("11"+weatherData.toString());
							switch(i){
								case 1:
									weather1.setText(weatherData.toString());
									break;
								case 2:
									weather2.setText(weatherData.toString());
									break;
								case 3:
									weather3.setText(weatherData.toString());
									break;
								case 4:
									weather4.setText(weatherData.toString());
									break;
							}
						}
					}
				});
			}	
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				System.out.println(e);
			}
		});
	}
}
