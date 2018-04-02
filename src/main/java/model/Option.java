package model;

import java.util.ArrayList;
import java.util.List;

public class Option {

	private Title title;
	private Tooltip tooltip;
	private Legend legend;
	private Toolbox toolbox;
	private Axis xAxis;
	private YAxis yAxis;
	private List<SeriesSubtype> series;
	
	public Title getTitle() {
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public Option title(String text) {
		if(null == this.title){
			this.title = new Title();
		}
		this.title.text(text);
		return this;
	}
	public Option title(String text, String subtext) {
		if(null == this.title){
			this.title = new Title();
		}
		this.title.text(text).setSubtext(subtext);
		return this;
	}
	
	public Tooltip getTooltip() {
		return tooltip;
	}
	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}
	public Tooltip tooltip(){
		if(null == this.tooltip){
			this.tooltip = new Tooltip();
		}
		return this.tooltip;
	}
	
	public Legend getLegend() {
		return legend;
	}
	public void setLegend(Legend legend) {
		this.legend = legend;
	}
	public Legend legend(){
		if(null == this.legend){
			this.legend = new Legend();
		}
		return this.legend;
	}
	
	public Toolbox getToolbox() {
		return toolbox;
	}
	public void setToolbox(Toolbox toolbox) {
		this.toolbox = toolbox;
	}
	public Toolbox toolbox(){
		if(null == this.toolbox){
			this.toolbox = new Toolbox();
		}
		return this.toolbox;
	}
	
	public Axis getxAxis() {
		return xAxis;
	}
	public void setxAxis(Axis xAxis) {
		this.xAxis = xAxis;
	}
	public Axis xAxis(){
		if(null == this.xAxis){
			this.xAxis = new Axis();
		}
		return this.xAxis;
	}
	
	public YAxis getyAxis() {
		return yAxis;
	}
	public void setyAxis(YAxis yAxis) {
		this.yAxis = yAxis;
	}
	public YAxis yAxis(){
		if(null == this.yAxis){
			this.yAxis = new YAxis();
		}
		return this.yAxis;
	}
	
	public List<SeriesSubtype> getSeries() {
		return series;
	}
	public void setSeries(List<SeriesSubtype> series) {
		this.series = series;
	}
	public List<SeriesSubtype> series(List<SeriesSubtype> series){
		if(null == this.series){
			this.series = new ArrayList<SeriesSubtype>();
		}
		this.series.addAll(series);
		return this.series;
	}
	
}
