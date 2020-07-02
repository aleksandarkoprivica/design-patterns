package commands;

import java.util.ArrayList;

import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingModel;

public class CmdRemoveShape implements Command {

	private DrawingModel model;
	private Shape shape;
	private DrawingController controller;
	private ArrayList<Shape> deletedShapes = new ArrayList<Shape>();
	
	public CmdRemoveShape(DrawingModel model, Shape shape, DrawingController controller) {
		this.model = model;
		this.shape = shape;
		this.controller = controller;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		model.remove(shape);
		controller.notifyAllObserversRedoUndo();
		
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		model.add(shape);
		controller.notifyAllObserversRedoUndo();
		
	}
	
	public String toString() {
		return "Command:Delete;"+shape.toString();
	}

}
