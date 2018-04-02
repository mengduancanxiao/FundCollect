package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeriesSubtype {

	private String name;
    private String type;
    private boolean smooth;
    private List<Object> data;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SeriesSubtype name(String name){
		this.name = name;
		return this;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public SeriesSubtype type(String type){
		this.type = type;
		return this;
	}
	
	public boolean getSmooth() {
		return smooth;
	}
	public void setSmooth(boolean smooth) {
		this.smooth = smooth;
	}
	public SeriesSubtype smooth(boolean smooth){
		this.smooth = smooth;
		return this;
	}
	
	public List<Object> getData() {
		return data;
	}
	public void setData(List<Object> data) {
		this.data = data;
	}
	public <T> SeriesSubtype data(T[] values){
		if(null == this.data){
			this.data = new ArrayList<Object>();
		}
		this.data.addAll(Arrays.asList(values));
		return this;
	}
    
    
}
