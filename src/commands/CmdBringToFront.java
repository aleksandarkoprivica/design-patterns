package commands;

import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingModel;

public class CmdBringToFront implements Command {
	
	private DrawingModel model;
	private Shape shape;
	private DrawingController controller;
	private int in;
	
	public CmdBringToFront(DrawingModel model, Shape shape, DrawingController controller, int in) {
		this.model = model;
		this.shape = shape;
		this.controller = controller;
		this.in = in;
	}
	

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		if(controller.getCurrent() != null) {
			for(int i=0; i<model.getShapes().size()-1; i++) {
				if (model.getShapes().get(i).equals(controller.getCurrent())) {
					if (i < model.getShapes().size()) {
						Shape temp = model.getShapes().get(i);
						model.getShapes().remove(i);
						model.getShapes().add(model.getShapes().size(), temp);
						controller.notifyAllObserversRedoUndo();
						controller.notifyAllObservers();
					}
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
					if (i < model.getShapes().size()) {
						Shape temp = model.getShapes().get(i);
						model.getShapes().remove(i);
						model.getShapes().add(controller.getPosition2(), temp);
						controller.notifyAllObserversRedoUndo();
						controller.notifyAllObservers();
					}
				}
			}
		}
		
	}
	
	public String toString(){
		return "Command:BringToFront;id:"+shape.getId() + ";index:" + in;
	}

}
