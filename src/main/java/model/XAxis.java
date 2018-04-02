package model;

import net.sf.json.JSONArray;

public class XAxis {
	private String type;
	private boolean boundaryGap;
	private JSONArray data;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isBoundaryGap() {
		return boundaryGap;
	}
	public void setBoundaryGap(boolean boundaryGap) {
		this.boundaryGap = boundaryGap;
	}
	public JSONArray getData() {
		return data;
	}
	public void setData(JSONArray data) {
		this.data = data;
	}
	
	
}
