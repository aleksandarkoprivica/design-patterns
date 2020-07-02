package geometry;

import java.awt.Color;
import java.awt.Graphics;

import hexagon.Hexagon;

public class HexagonAdapter extends AreaShape implements Moveable{

	private Hexagon hex;
	
	public HexagonAdapter() {
		
	}
	public HexagonAdapter(Hexagon hex) {
		this.hex = hex;
	}
	public HexagonAdapter(Hexagon hex, Color color) {
		this(hex);
		setColor(color);
	}
	public HexagonAdapter(Hexagon hex, Color color, Color areaColor) {
		this(hex, color);
		setAreaColor(areaColor);
	}
	
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof HexagonAdapter) {
			return hex.getR() - ((HexagonAdapter) o).getHex().getR();
		}
		return 0;
	}

	@Override
	public void moveTo(int x, int y) {
		// TODO Auto-generated method stub
		hex.setX(x);
		hex.setY(y);
	}

	@Override
	public void moveFor(int x, int y) {
		// TODO Auto-generated method stub
		hex.setX(hex.getX() + x);
		hex.setY(hex.getY() + y);
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(getAreaColor());
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		hex.paint(g);
		if(isSelected())
			selected(g);
	}

	@Override
	public void selected(Graphics g) {
		// TODO Auto-generated method stub
		int square = (int) (hex.getR()*Math.sqrt(3)/2);
		new Point(hex.getX()-hex.getR(), hex.getY()).selected(g);
		new Point(hex.getX()+hex.getR(), hex.getY()).selected(g);
		new Point(hex.getX()-hex.getR()/2, hex.getY()+square).selected(g);
		new Point(hex.getX()-hex.getR()/2, hex.getY()-square).selected(g);
		new Point(hex.getX()+hex.getR()/2, hex.getY()+square).selected(g);
		new Point(hex.getX()+hex.getR()/2, hex.getY()-square).selected(g);
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return hex.doesContain(x, y);
	}
	
	public String toString() {
		return "Shape:Hexagon;Center:("+hex.getX()+","+hex.getY()+");Radius:"+hex.getR()+";Edge color:"+hex.getBorderColor().getRed() + "," + hex.getBorderColor().getGreen() + "," + hex.getBorderColor().getBlue()+";"
				+ "Surface color:"+hex.getAreaColor().getRed()+"," + hex.getAreaColor().getGreen() + "," +hex.getAreaColor().getBlue()+";Id:"+this.getId();
	}
	
	public boolean isSelected() {
		return hex.isSelected();
	}
	
	public void setSelected(boolean selected) {
		hex.setSelected(selected);
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof HexagonAdapter) {
			Hexagon helper = ((HexagonAdapter) obj).getHex();
			return hex.getX() == helper.getX() && hex.getY() == helper.getY() && hex.getR() == helper.getR();
		}
		return false;
	}
	
	public Hexagon getHex() {
		return hex;
	}
	public void setHex(Hexagon hex) {
		this.hex = hex;
	}
	
	public void setColor(Color color) {
		hex.setBorderColor(color);
	}
	
	public void setAreaColor(Color color) {
		hex.setAreaColor(color);
	}
	
	public Color getColor() {
		return hex.getBorderColor();
	}
	
	public Color getAreaColor() {
		return hex.getAreaColor();
	}
	
	public int getX() {
		
		int x = hex.getX();
		return x;
	}
	
	public int getY() {
		return hex.getY();
	}
	
	public int getR() {
		return hex.getR();
	}
	
	public void setX(int x) {
		hex.setX(x);
	}
	
	public void setY(int y) {
		hex.setY(y);
	}
	
	public void setR(int r) {
		hex.setR(r);
	}

}
