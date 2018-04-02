package model;


public class Feature {

	private MagicType magicType;
	private SaveAsImage saveAsImage;
	
	public MagicType getMagicType() {
		return magicType;
	}
	public void setMagicType(MagicType magicType) {
		this.magicType = magicType;
	}
	public MagicType magicType(){
		if(null == this.magicType){
			this.magicType = new MagicType();
		}
		return this.magicType;
	}
	
	
	public SaveAsImage getSaveAsImage() {
		return saveAsImage;
	}
	public void setSaveAsImage(SaveAsImage saveAsImage) {
		this.saveAsImage = saveAsImage;
	}
	public SaveAsImage saveAsImage(){
		if(null == this.saveAsImage){
			this.saveAsImage = new SaveAsImage();
		}
		return this.saveAsImage;
	}
	
}
