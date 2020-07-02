package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shape implements Comparable, Serializable {
	
	private String defColor = "black";
	private Color color = Color.BLACK;
	private boolean selected;
	private int id;
	
	public Shape() {
		
	}
	public Shape(String defColor) {
		this.defColor = defColor;
	}
	public Shape(Color color) {
		this.color = color;
	}
	
	public abstract void paint(Graphics g);
	public abstract void selected(Graphics g);
	public abstract boolean contains(int x, int y);
	
	public static Color findColor(String color) {
		if(color.equalsIgnoreCase("black"))
			return Color.BLACK;
		else if(color.equalsIgnoreCase("white"))
			return Color.WHITE;
		else if(color.equalsIgnoreCase("blue"))
			return Color.BLUE;
		else if(color.equalsIgnoreCase("red"))
			return Color.RED;
		else if(color.equalsIgnoreCase("green"))
			return Color.GREEN;
		else if(color.equalsIgnoreCase("yellow"))
			return Color.YELLOW;
		else if(color.equalsIgnoreCase("pink"))
			return Color.PINK;
		else
			return Color.BLACK;
			
	}
	
	
	public String getDefColor() {
		return defColor;
	}
	public void setDefColor(String defColor) {
		this.defColor = defColor;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
