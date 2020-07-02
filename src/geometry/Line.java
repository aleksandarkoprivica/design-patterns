package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape{
	
	private Point pBegin;
	private Point pEnd;
	
	public Line() {
		
	}
	public Line(Point pBegin, Point pEnd) {
		this.pBegin = pBegin;
		this.pEnd = pEnd;
	}
	public Line(Point pBegin, Point pEnd, String color) {
		this(pBegin,pEnd);
		setDefColor(color);
	}
	public Line(Point pBegin, Point pEnd, Color color) {
		this(pBegin, pEnd);
		setColor(color);
	}
	
	public double lenght() {
		return pBegin.distance(pEnd);
	}
	
	public void moveFor(int x, int y) {
		pBegin.setX(pBegin.getX() + x);
		pBegin.setY(pBegin.getY() + y);
		pEnd.setX(pEnd.getX() + x);
		pEnd.setY(pEnd.getY() + y);
	}
	
	public Point lineMiddle() {
		int middleX = (pBegin.getX() + pEnd.getX()) / 2;
		int middleY = (pBegin.getY() + pEnd.getY()) / 2;
		return new Point(middleX, middleY);
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof Line) {
			Line helper = (Line) o;
			return (int) (this.lenght() - helper.lenght());
		}
		else return 0;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(getColor());
		g.drawLine(pBegin.getX(), pBegin.getY(), pEnd.getX(), pEnd.getY());
		if(isSelected())
			selected(g);
	}

	@Override
	public void selected(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLUE);
		pBegin.selected(g);
		pEnd.selected(g);
		lineMiddle().selected(g);
		setSelected(true);
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		Point clickOnCanvas = new Point(x, y);
		if(clickOnCanvas.distance(pBegin)+clickOnCanvas.distance(pEnd)-this.lenght()<0.5)
			return true;
		else
			return false;
	}
	
	public String toString() {
		return "Shape:Line;Start:("+pBegin.getX()+","+pBegin.getY()+");End:("+pEnd.getX()+","+pEnd.getY()+");Edge color:"+ getColor().getRed() +","+getColor().getGreen() + "," + getColor().getBlue()+";Id:"+this.getId();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Line) {
			Line helper = (Line) obj;
			if(pBegin.equals(helper.getpBegin()) && pEnd.equals(helper.getpEnd()))
				return true;
			else
				return false;
		}
		else
			return false;
	}

	public Point getpBegin() {
		return pBegin;
	}

	public void setpBegin(Point pBegin) {
		this.pBegin = pBegin;
	}

	public Point getpEnd() {
		return pEnd;
	}

	public void setpEnd(Point pEnd) {
		this.pEnd = pEnd;
	}

}
