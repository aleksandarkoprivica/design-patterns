package mvc;

import java.util.ArrayList;

import geometry.Shape;

public class DrawingModel {
	
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private ArrayList<Shape> deletedShapes = new ArrayList<Shape>();
	
	private static int counter = 1;

	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	public void add(Shape shape) {
		if(shape.getId() == 0)
			shape.setId(counter++);
		else
			counter++;
		shapes.add(shape);
		deletedShapes.remove(shape);
	}
	
	public boolean remove(Shape shape) {
		boolean check = false;
		deletedShapes.add(shape);
		if(shapes.remove(shape))
			check = true;
		return check;
	}
	
	public Shape getShape(int i) {
		return shapes.get(i);
	}

	public ArrayList<Shape> getDeletedShapes() {
		return deletedShapes;
	}

	public void setDeletedShapes(ArrayList<Shape> deletedShapes) {
		this.deletedShapes = deletedShapes;
	}

	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		DrawingModel.counter = counter;
	}

}
