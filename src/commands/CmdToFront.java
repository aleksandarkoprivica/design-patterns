package commands;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingModel;

public class CmdToFront implements Command {
	
	private DrawingModel model;
	private Shape shape;
	private DrawingController controller;
	
	public CmdToFront(DrawingModel model, Shape shape, DrawingController controller) {
		this.model = model;
		this.shape = shape;
		this.controller = controller;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		if (controller.getCurrent() != null) {
			for (int i = 0; i < model.getShapes().size() - 1; i++) {
				if (model.getShapes().get(i).equals(controller.getCurrent())) {
					Collections.swap(model.getShapes(), i, i+1);
					controller.notifyAllObservers();
					break;
				}
			}
		}
		
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		if (controller.getCurrent() != null) {
			for (int i = 0; i < model.getShapes().size(); i++) {
				if (model.getShapes().get(i).equals(controller.getCurrent())) {
					if (i > 0) {
						Collections.swap(model.getShapes(), i-1, i);
						controller.notifyAllObservers();
					}
				}
			}
		}
		
	}
	
	public String toString() {
		return "Command:ToFront;id:"+shape.getId();
	}

}
