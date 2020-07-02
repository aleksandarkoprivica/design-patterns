package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends AreaShape implements Moveable {

	private Point center;
	private int r;
	
	public Circle() {
		
	}
	public Circle(Point center, int r) {
		this.center = center;
		this.r = r;
	}
	public Circle(Point center, int r, String defColor) {
		this(center, r);
		setDefColor(defColor);
	}
	public Circle(Point center, int r, String defColor, String defAreaColor) {
		this(center, r, defColor);
		setDefAreaColor(defAreaColor);
	}
	public Circle(Point center, int r, Color color) {
		this(center, r);
		setColor(color);
	}
	public Circle(Point center, int r, Color color, Color areaColor) {
		this(center, r, color);
		setAreaColor(areaColor);
	}
	
	public double scope() {
		return 2 * r * Math.PI;
	}
	
	public double surface() {
		return r * r * Math.PI;
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof Circle) {
			Circle helper = (Circle) o;
			return (int) (this.r - helper.r);
		}
		else
			return 0;
	}

	@Override
	public void moveTo(int x, int y) {
		// TODO Auto-generated method stub
		center.moveTo(x, y);
	}

	@Override
	public void moveFor(int x, int y) {
		// TODO Auto-generated method stub
		center.moveFor(x, y);
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(getAreaColor());
		g.fillOval(center.getX() - r + 1, center.getY() - r + 1, 2*r-2, r+r-2);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(getColor());
		g.drawOval(center.getX()-r, center.getY()-r, 2*r, r+r);
		fill(g);
		if(isSelected())
			selected(g);
	}

	@Override
	public void selected(Graphics g) {
		// TODO Auto-generated method stub
		new Line(new Point(center.getX(), center.getY()-r), new Point(center.getX(), center.getY()+r)).selected(g);
		new Line(new Point(center.getX()-r, center.getY()), new Point(center.getX()+r, center.getY())).selected(g);
		setSelected(true);
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		Point clickOnCanvas = new Point(x, y);
		if(clickOnCanvas.distance(center) <= r)
			return true;
		else
			return false;
	}

	public String toString() {
		return "Shape:Circle;Center:("+center.getX()+","+center.getY()+");Radius:"+r+";Edge color:"+getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue()+";"
				+ "Surface color:"+getAreaColor().getRed()+"," + getAreaColor().getGreen() + "," + getAreaColor().getBlue()+";Id:"+this.getId();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Circle) {
			Circle helper = (Circle) obj;
			if(center.equals(helper.center) && r == helper.r)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	
	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

}
