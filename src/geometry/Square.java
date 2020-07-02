package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Square extends AreaShape implements Moveable {
	
	protected Point upperLeft;
	protected int n;
	
	public Square() {
		
	}
	public Square(Point upperLeft, int n) {
		this.upperLeft = upperLeft;
		this.n = n;
	}
	public Square(Point upperLeft, int n,String defColor) {
		this(upperLeft, n);
		setDefColor(defColor);
	}
	public Square(Point upperLeft, int n,String defColor, String defAreaColor) {
		this(upperLeft, n, defColor);
		setDefAreaColor(defAreaColor);
	}
	public Square(Point upperLeft, int n,Color color) {
		this(upperLeft, n);
		setColor(color);
	}
	public Square(Point upperLeft, int n,Color color, Color areaColor) {
		this(upperLeft, n, color);
		setAreaColor(areaColor);
	}

	public int scope() {
		return 4 * n;
	}
	
	public int surface() {
		return n * n;
	}
	
	public Line diagonal() {
		return new Line(upperLeft, new Point(upperLeft.getX() + n, upperLeft.getY() + n));
	}

	public Point center() {
		return diagonal().lineMiddle();
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof Square) {
			Square helper = (Square) o;
			return (int) (this.surface() - helper.surface());
		}
		else
			return 0;
	}

	@Override
	public void moveTo(int x, int y) {
		// TODO Auto-generated method stub
		upperLeft.setX(x);
		upperLeft.setY(y);
	}

	@Override
	public void moveFor(int x, int y) {
		// TODO Auto-generated method stub
		upperLeft.setX(upperLeft.getX() + x);
		upperLeft.setY(upperLeft.getY() + y);
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(getAreaColor());
		g.fillRect(upperLeft.getX()+1, upperLeft.getY()+1, n-1, n-1);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(getColor());
		g.drawRect(upperLeft.getX(), upperLeft.getY(), n, n);
		fill(g);
		if(isSelected())
			selected(g);
	}

	@Override
	public void selected(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(findColor("blue"));
		new Line(getUpperLeft(), new Point(getUpperLeft().getX()+n, getUpperLeft().getY())).selected(g);
		new Line(getUpperLeft(), new Point(getUpperLeft().getX(), getUpperLeft().getY()+n)).selected(g);
		new Line(new Point(getUpperLeft().getX()+n, getUpperLeft().getY()), diagonal().getpEnd()).selected(g);
		new Line(new Point(getUpperLeft().getX(), getUpperLeft().getY()+n), diagonal().getpEnd()).selected(g);
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		if(this.getUpperLeft().getX() <= x
			&& x <= (this.getUpperLeft().getX() + n)
			&& this.getUpperLeft().getY() <= y
			&& y <= (this.getUpperLeft().getY() + n))
			return true;
		else
			return false;
	}
	
	public String toString() {
		return "Shape:Square;UpperLeft:("+upperLeft.getX()+ ","+upperLeft.getY()+");Side Length:"+n+";Edge color:"+getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue()+";"
				+ "Surface color:"+getAreaColor().getRed()+"," + getAreaColor().getGreen() + "," + getAreaColor().getBlue()+";Id:"+this.getId();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Square) {
			Square helper = (Square) obj;
			if(upperLeft.equals(helper.upperLeft) && n == helper.n)
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	public Point getUpperLeft() {
		return upperLeft;
	}
	public void setUpperLeft(Point upperLeft) {
		this.upperLeft = upperLeft;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}

}
