package ru.wtf.objects;

public enum WallType {
	GREY("lightWallPart.png",0.01f),
	SOFT("greyWallPart.png",0.5f),
	HARD("blackWallPart.png", 1.0f), 
	FIRE("redWallPart.png", 1.5f);
	
	private float density;

	WallType(String string, float density){
		this.png = string;
		this.density = density;
	}
	
	public String png;
	
	public String getPath(){
		return png;
	}

	public float getDensity() {
		return density;
	}
	
}
