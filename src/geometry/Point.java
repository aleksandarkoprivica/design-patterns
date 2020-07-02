package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape implements Moveable {

	private int x;
	private int y;
	
	public Point() {
		
	}
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Point(int x, int y, String defColor) {
		this(x,y);
		setDefColor(defColor);
	}
	public Point(int x, int y, Color color) {
		this(x,y);
		setColor(color);
	}
	
	
	public double distance(Point p2) {
		double dx = x - p2.getX();
		double dy = y - p2.getY();
		double result = Math.sqrt(dx*dx + dy*dy);
		
		return result;
	}
	
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof Point) {
			Point helper = (Point) o;
			return (int) this.distance(new Point(0,0)) - (int) helper.distance(new Point(0,0));
		}
		else
			return 0;
	}

	@Override
	public void moveTo(int x, int y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
	}

	@Override
	public void moveFor(int x, int y) {
		// TODO Auto-generated method stub
		this.x = this.x + x;
		this.y = this.y + y;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(getColor());
		g.drawLine(x+2, y, x-2, y);
		g.drawLine(x, y-2, x, y+2);
		if(isSelected())
			selected(g);
	}

	@Override
	public void selected(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLUE);
		g.drawRect(x-3, y-3, 6, 6);
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		Point clickOnCanvas = new Point(x, y);
		if(clickOnCanvas.distance(this) <= 2)
			return true;
		else
			return false;
	}
	
	public String toString() {
		return "Shape:Point;Coordinates:(" + x + "," + y + ");"+"Edge Color:"+getColor().getRed()+"," + getColor().getGreen()+ "," + getColor().getBlue()+";Id:" + this.getId(); 
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point helper = (Point) obj;
			if(x == helper.x && y == helper.y)
				return true;
			else
				return false;
		}
		return false;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

}
