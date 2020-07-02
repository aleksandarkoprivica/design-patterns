package commands;

import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingModel;

public class CmdBringToBack implements Command {

	private DrawingModel model;
	private Shape shape;
	private DrawingController controller;
	private int in;
	
	public CmdBringToBack(DrawingModel model, Shape shape, DrawingController controller, int in) {
		this.model = model;
		this.shape = shape;
		this.controller = controller;
		this.in = in;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		if(controller.getCurrent() != null) {
			for(int i=0; i<model.getShapes().size(); i++) {
				if(model.getShapes().get(i).equals(controller.getCurrent())) {
					if (i>0) {
						Shape temp = model.getShapes().get(i);
						model.getShapes().remove(i);
						model.getShapes().add(0, temp);
						controller.notifyAllObservers();
						controller.notifyAllObserversRedoUndo();
					}
				}
			}
		}
		
	}

	@Override
	public void unexecute() {
		// TODO Auto-generated method stub
		int helper = 0;
		for(int n=0; n<model.getShapes().size()-1; n++) {
			if (model.getShapes().get(n).isSelected())
				helper=n;
		}
		if(controller.getCurrent() != null) {
			for(int i=0; i<model.getShapes().size()-1;i++) {
				if(model.getShapes().get(i).equals(controller.getCurrent())) {
					if(i < model.getShapes().size()) {
						Shape temp = model.getShapes().get(i);
						model.getShapes().remove(i);
						model.getShapes().add(controller.getPosition(), temp);
						controller.notifyAllObservers();
						controller.notifyAllObserversRedoUndo();
					}
				}
			}
		}
		
	}
	
	public String toString(){
		return "Command:BringToBack;id:"+shape.getId() + ";index:" + in;
	}

}
