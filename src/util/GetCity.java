package util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import model.City;

public class GetCity {

	public static List<City> getCities(InputStream xml) throws Exception{
		List<City> list=new ArrayList<City>();
		City city=null;
		XmlPullParser pullParser=Xml.newPullParser();
		pullParser.setInput(xml, "UTF-8");
		int event=pullParser.getEventType();
		while(event!=XmlPullParser.END_DOCUMENT){
			switch (event) {
			case XmlPullParser.START_TAG:
				if("d".equals(pullParser.getName())){
					city=new City();
					city.setCityCode(pullParser.getAttributeValue(0));
					city.setCityName(pullParser.getAttributeValue(1));
					city.setCityPin(pullParser.getAttributeValue(2));
					city.setProvinceName(pullParser.getAttributeValue(3));
					list.add(city);
					city=null;
				}
				break;
			}
			event=pullParser.next();
		}
		return list;
	}
}
