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
	 * 数据库名字
	 */
	public static final String DB_NAME="cool_weather";
	/**
	 * 版本号
	 */
	public static final int VERSION=1;

	private static CoolWeatherDB coolWeatherDB;
	
	private static SQLiteDatabase sqLiteDatabase;
	/**
	 * 私有化构造函数，这样就只能内部使用，外部无法用来创建对象。
	 * @param context
	 */
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbhelper=new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		//创建数据库并执行写入操作
		sqLiteDatabase=dbhelper.getWritableDatabase();
		
	}
	
	public synchronized static CoolWeatherDB getInstance(Context context){
		//这样就只会实例化一个对象，也就只会创建一次数据库。
		if(coolWeatherDB==null){
			coolWeatherDB=new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	/**
	 * 存储city信息
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
	 * 获取province内的city信息
	 * @param province的id
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
		//查询province并去重
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
