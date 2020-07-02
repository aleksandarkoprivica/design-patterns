package commands;

import java.util.ArrayList;

import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingModel;

public class CmdDeselectShape implements Command {
	
	private DrawingModel model = new DrawingModel();
	private Shape shape;
	private DrawingController controller;
	
	private ArrayList<Shape> selectedShapes;
	
	public CmdDeselectShape(DrawingModel model, Shape shape, DrawingController controller, ArrayList<Shape> selectedShapes) {
		this.model = model;
		this.shape = shape;
		this.controller = controller;
		this.selectedShapes = selectedShapes;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		selectedShapes.add(shape);
		shape.setSelected(false);
		controller.notifyAllObservers();
		controller.notifyAllObserversRedoUndo();
		
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		selectedShapes.remove(shape);
		shape.setSelected(true);
		controller.notifyAllObservers();
		controller.notifyAllObserversRedoUndo();
		
	}
	
	public String toString() {
		return "Command:Deselect;"+shape.toString();
	}

}
