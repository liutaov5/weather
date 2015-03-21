/**
 * 百度天气api返回的json数据格式解析后的数据类型（用GSON解析）
 */
package model;

import java.util.List;


public class JSONDataType {

	int error;
	String status;
	String date;
	List<Results> results;
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<Results> getResults() {
		return results;
	}
	public void setResults(List<Results> results) {
		this.results = results;
	}
}
