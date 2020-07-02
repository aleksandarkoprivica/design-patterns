package commands;

import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingModel;

public class CmdAddShape implements Command {

	private DrawingModel model = new DrawingModel();
	private Shape shape;
	private DrawingController controller;
	
	public CmdAddShape(DrawingModel model, Shape shape, DrawingController controller) {
		this.model = model;
		this.shape = shape;
		this.controller = controller;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		model.add(shape);
		controller.notifyAllObserversRedoUndo();
		
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		model.remove(shape);
		controller.notifyAllObserversRedoUndo();
		
	}
	
	public String toString() {
		return "Command:Add;"+shape.toString();
	}

}
