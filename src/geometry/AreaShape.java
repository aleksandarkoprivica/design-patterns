package geometry;

import java.awt.Color;
import java.awt.Graphics;

public abstract class AreaShape extends Shape {
	
	private String defAreaColor = "white";
	private Color areaColor = Color.WHITE;
	
	public abstract void fill(Graphics g);

	
	public String getDefAreaColor() {
		return defAreaColor;
	}

	public void setDefAreaColor(String defAreaColor) {
		this.defAreaColor = defAreaColor;
	}

	public Color getAreaColor() {
		return areaColor;
	}

	public void setAreaColor(Color areaColor) {
		this.areaColor = areaColor;
	}
	

}
