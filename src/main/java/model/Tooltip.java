package model;

public class Tooltip {

	private String trigger;

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
	
	public Tooltip trigger(String trigger){
		this.trigger = trigger;
		return this;
	}
	
}
