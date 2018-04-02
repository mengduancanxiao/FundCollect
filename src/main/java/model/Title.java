package model;

public class Title {

	private String text;
    private String subtext;
    
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Title text(String text) {
		this.text = text;
		return this;
	}
	public String getSubtext() {
		return subtext;
	}
	public void setSubtext(String subtext) {
		this.subtext = subtext;
	}
	public Title subtext(String subtext) {
		this.subtext = subtext;
		return this;
	}

}
