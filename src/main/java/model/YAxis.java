package model;

import java.util.List;

public class YAxis extends Axis{
	private String min;

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public YAxis min(String min){
		this.min = min;
		return this;
	}
}
