package com.example.weather;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import util.GetCity;


import model.City;
import db.CoolWeatherDB;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	public static final int LEVEL_PROVINCE=0;
	public static final int LEVEL_CITY=1;
	
	private ProgressDialog progressDialog;
	private EditText editText;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private CoolWeatherDB coolWeatherDB;
	private List<String> dataList=new ArrayList<String>();
	
	private List<City> cityList;
	private List<String> provinceList;
	
	private int currentLevel;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		listView=(ListView)findViewById(R.id.list_view);
		editText=(EditText)findViewById(R.id.title_text);
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(adapter);
		coolWeatherDB=CoolWeatherDB.getInstance(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(currentLevel==LEVEL_PROVINCE){
					//获取当前点击的item内容
					String province=(String)parent.getItemAtPosition(position);
					//System.out.println(province);
					queryCities(province);
				}else if(currentLevel==LEVEL_CITY){
					String city=(String)parent.getItemAtPosition(position);
					intent=new Intent(getApplicationContext(), WeatherView.class);
					intent.putExtra("city", city);
					startActivity(intent);
				}
			}
		});
		queryProvinces();
	}
	//查询province
	private void queryProvinces() {
		// TODO Auto-generated method stub
		provinceList=coolWeatherDB.loadProvinces();
		if(provinceList.size()>0){
			dataList.clear();
			for(String province:provinceList){
				dataList.add(province);
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			editText.setText("中国");
			currentLevel=LEVEL_PROVINCE;
		}else{
			showProgressDialog();
			queryFromXml(null,"province");
		}
	}
	//查询city
	private void queryCities(String string){
		cityList=coolWeatherDB.loadCities(string);
		if(cityList.size()>0){
			dataList.clear();
			for(City city:cityList){
				dataList.add(city.getCityName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			editText.setText(string);
			currentLevel=LEVEL_CITY;
		}else{
			Toast.makeText(this, "出错了", Toast.LENGTH_SHORT);
		}
	}
	//从xml文件中读取城市信息，加载较慢，有待优化
	private void queryFromXml(final String string,final String type){
		InputStream xml = this.getClass().getClassLoader().getResourceAsStream("citylist.xml");
		List<City> cities;
		try {
			cities = GetCity.getCities(xml);
			for(City city : cities){
				coolWeatherDB.saveCity(city);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				closeProgressDialog();
				if("province".equals(type)){
					queryProvinces();
				}
			}
		});
		queryProvinces();
	}
	//显示进度条
	private void showProgressDialog(){
		if(progressDialog==null){
			progressDialog=new ProgressDialog(this);
			progressDialog.setMessage("加载中...");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}
	//关闭进度条
	private void closeProgressDialog(){
		if(progressDialog!=null){
			progressDialog.dismiss();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(currentLevel==LEVEL_CITY){
			queryProvinces();
		}else{
			finish();
		}
	}
	
	

}
