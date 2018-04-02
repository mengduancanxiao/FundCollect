package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Legend {

	private List<Object> data;

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}
	
	public Legend data(Object[] valuedata){
		if ((null == valuedata) || (valuedata.length == 0)) {
			return this;
		}
		if (null == this.data) {
			this.data = new ArrayList<Object>();
		}
		this.data.addAll(Arrays.asList(valuedata));
		return this;
	}
}
