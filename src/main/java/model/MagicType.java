package model;

public class MagicType {

	private boolean show;
	private Object[] type ;
	
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public MagicType show(boolean show){
		this.show = show;
		return this;
	}
	
	public Object[] getType() {
		if(null == this.type || this.type.length == 0){
			this.type = new Object[]{"line","bar","stack", "tiled"};
		}
		return type;
	}
	public void setType(Object[] type) {
		this.type = type;
	}
	public MagicType type(Object[] type){
		if(null == type || type.length == 0){
			this.type = new Object[]{"line","bar","stack", "tiled"};
		}else{
			this.type = type;
		}
		return this;
	}
	
	
}
