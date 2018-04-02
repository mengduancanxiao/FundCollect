package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Axis {

	private String type;
	private boolean boundaryGap;
	private List<Object> data;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Axis type(String type){
		this.type = type;
		return this;
	}
	
	public boolean isBoundaryGap() {
		return boundaryGap;
	}
	public void setBoundaryGap(boolean boundaryGap) {
		this.boundaryGap = boundaryGap;
	}
	public Axis boundaryGap(boolean boundaryGap){
		this.boundaryGap = boundaryGap;
		return this;
	}
	
	public List<Object> getData() {
		return data;
	}
	public void setData(List<Object> data) {
		this.data = data;
	}
	public <T> Axis data(T[] values){
		if (null == values || values.length == 0) {
			return this;
		}
		if(null == this.data){
			this.data = new ArrayList<Object>();
		}
		this.data.addAll(Arrays.asList(values));
		return this;
	}
	
}
