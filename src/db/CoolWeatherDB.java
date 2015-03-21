package db;

import java.util.ArrayList;
import java.util.List;

import model.City;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {

	/**
	 * ���ݿ�����
	 */
	public static final String DB_NAME="cool_weather";
	/**
	 * �汾��
	 */
	public static final int VERSION=1;

	private static CoolWeatherDB coolWeatherDB;
	
	private static SQLiteDatabase sqLiteDatabase;
	/**
	 * ˽�л����캯����������ֻ���ڲ�ʹ�ã��ⲿ�޷�������������
	 * @param context
	 */
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbhelper=new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		//�������ݿⲢִ��д�����
		sqLiteDatabase=dbhelper.getWritableDatabase();
		
	}
	
	public synchronized static CoolWeatherDB getInstance(Context context){
		//������ֻ��ʵ����һ������Ҳ��ֻ�ᴴ��һ�����ݿ⡣
		if(coolWeatherDB==null){
			coolWeatherDB=new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	/**
	 * �洢city��Ϣ
	 * @param city
	 */
	public void saveCity(City city){
		if(city!=null){
			ContentValues values=new ContentValues();
			values.put("city_code", city.getCityCode());
			values.put("city_pin", city.getCityPin());
			values.put("city_name", city.getCityName());
			values.put("province_name", city.getProvinceName());
			sqLiteDatabase.insert("city", null, values);
		}
	}
	/**
	 * ��ȡprovince�ڵ�city��Ϣ
	 * @param province��id
	 * @return
	 */
	public List<City> loadCities(String provinceName){
		
		List<City> list=new ArrayList<City>();
		Cursor cursor=sqLiteDatabase.query("City", null, "province_name=?", 
				new String[]{String.valueOf(provinceName)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city=new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setCityPin(cursor.getString(cursor.getColumnIndex("city_pin")));
				city.setProvinceName(provinceName);
				list.add(city);
			}while(cursor.moveToNext());
		}
		return list;
	}
	
	public List<String> loadProvinces(){
		List<String> list=new ArrayList<String>();
		//��ѯprovince��ȥ��
		Cursor cursor=sqLiteDatabase.query(true, "City", new String[]{"province_name"},
				null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				String string=cursor.getString(cursor.getColumnIndex("province_name"));
				list.add(string);
			}while(cursor.moveToNext());
		}
		return list;
	}
}
