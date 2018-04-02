package model;

public class Toolbox {

	private boolean show;
	private Feature feature;
	
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public Toolbox show(boolean show){
		this.show = show;
		return this;
	}
	
	public Feature getFeature() {
		return feature;
	}
	public void setFeature(Feature feature) {
		this.feature = feature;
	}
	public Toolbox feature(Feature feature){
		if (null == this.feature) {
			this.feature = new Feature();
		}
		this.feature = feature;
		return this;
	}
}
