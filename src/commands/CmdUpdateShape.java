package commands;

import geometry.Circle;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Square;
import hexagon.Hexagon;
import mvc.DrawingController;

public class CmdUpdateShape implements Command {
	private Shape original;
	private Shape newState;
	private Shape oldState = null;
	private int check = 0;
	
	private DrawingController controller;
	
	public CmdUpdateShape(Shape original, Shape newState, DrawingController controller) {
		
		if(original instanceof Point) {
			this.original = (Point)original;
			this.newState = (Point)newState;
			this.controller = controller;
			check = 1;
		} else if (original instanceof Line) {
			this.original = (Line)original;
			this.newState = (Line)newState;
			this.controller = controller;
			check = 2;
		} else if (original instanceof Circle) {
			this.original = (Circle)original;
			this.newState = (Circle)newState;
			this.controller = controller;
			check = 3;
		} else if (original instanceof Square && !(original instanceof Rectangle)) {
			this.original = (Square)original;
			this.newState = (Square)newState;
			this.controller = controller;
			check = 4;
		} else if (original instanceof Rectangle) {
			this.original = (Rectangle)original;
			this.newState = (Rectangle)newState;
			this.controller = controller;
			check = 5;
		} else if (original instanceof HexagonAdapter) {
			this.original = (HexagonAdapter)original;
			this.newState = (HexagonAdapter)newState;
			this.controller = controller;
			check = 6;
		} 
	}

	@Override
	public void execute() {
		if(check == 1) {
			Point oldState = new Point();
			Point original = (Point)this.original;
			Point newState = (Point)this.newState;
			oldState.setX(original.getX());
			oldState.setY(original.getY());
			oldState.setColor(original.getColor());
			oldState.setId(original.getId());
			
			original.setX(newState.getX());
			original.setY(newState.getY());
			original.setColor(newState.getColor());
			original.setSelected(true);
			newState.setId(original.getId());
			this.oldState = oldState;
			
		} else if(check == 2) {
			Line oldState = new Line();
			Line original = (Line)this.original;
			Line newState = (Line)this.newState;
			oldState.setpBegin(original.getpBegin());
			oldState.setpEnd(original.getpEnd());
			oldState.setColor(original.getColor());
			oldState.setId(original.getId());
			
			original.setpBegin(newState.getpBegin());
			original.setpEnd(newState.getpEnd());
			original.setColor(newState.getColor());
			original.setSelected(true);
			newState.setId(original.getId());
			this.oldState = oldState;
			
		} else if(check == 3) {
			Circle oldState = new Circle();
			Circle original = (Circle)this.original;
			Circle newState = (Circle)this.newState;
			oldState.setCenter(original.getCenter());
			oldState.setR(original.getR());
			oldState.setColor(original.getColor());
			oldState.setAreaColor(original.getAreaColor());
			oldState.setId(original.getId());
			
			original.setCenter(newState.getCenter());
			original.setR(newState.getR());
			original.setColor(newState.getColor());
			original.setAreaColor(newState.getAreaColor());
			original.setSelected(true);
			newState.setId(original.getId());
			this.oldState = oldState;
			
		} else if(check == 4) {
			Square oldState = new Square();
			Square original = (Square)this.original;
			Square newState = (Square)this.newState;
			oldState.setUpperLeft(original.getUpperLeft());
			oldState.setN(original.getN());
			oldState.setColor(original.getColor());
			oldState.setAreaColor(original.getAreaColor());
			oldState.setId(original.getId());
			
			original.setUpperLeft(newState.getUpperLeft());
			original.setN(newState.getN());
			original.setColor(newState.getColor());
			original.setAreaColor(newState.getAreaColor());
			original.setSelected(true);
			newState.setId(original.getId());
			this.oldState = oldState;
			
		} else if(check == 5) {
			Rectangle oldState = new Rectangle();
			Rectangle original = (Rectangle)this.original;
			Rectangle newState = (Rectangle)this.newState;
			oldState.setUpperLeft(original.getUpperLeft());
			oldState.setN(original.getN());
			oldState.setHeight(original.getHeight());
			oldState.setColor(original.getColor());
			oldState.setAreaColor(original.getAreaColor());
			oldState.setId(original.getId());
			
			original.setUpperLeft(newState.getUpperLeft());
			original.setN(newState.getN());
			original.setHeight(newState.getHeight());
			original.setColor(newState.getColor());
			original.setAreaColor(newState.getAreaColor());
			original.setSelected(true);
			newState.setId(original.getId());
			this.oldState = oldState;
			
		} else if(check == 6) {
			HexagonAdapter oldState = new HexagonAdapter(new Hexagon(0,0,0),null,null);
			HexagonAdapter original = (HexagonAdapter)this.original;
			HexagonAdapter newState = (HexagonAdapter)this.newState;
			oldState.setX(original.getX());
			oldState.setY(original.getY());
			oldState.setR(original.getR());
			oldState.setColor(original.getColor());
			oldState.setAreaColor(original.getAreaColor());
			oldState.setId(original.getId());
			
			original.setX(newState.getX());
			original.setY(newState.getY());
			original.setR(newState.getR());
			original.setColor(newState.getColor());
			original.setAreaColor(newState.getAreaColor());
			original.setSelected(true);
			newState.setId(original.getId());
			this.oldState = oldState;
			
		}
		
		controller.notifyAllObserversRedoUndo();
	}

	@Override
	public void unexecute() {
		
		if(check == 1) {
			Point original = (Point)this.original;
			Point newState = (Point)this.newState;
			Point oldState = (Point)this.oldState;
			if (oldState == null) {
				newState.setX(original.getX());
				newState.setY(original.getY());
				newState.setColor(original.getColor());
				//newState.setId(original.getId());
				return;
			}
			original.setX(oldState.getX());
			original.setY(oldState.getY());
			original.setColor(oldState.getColor());
			original.setId(oldState.getId());
		} else if(check == 2) {
			Line original = (Line)this.original;
			Line newState = (Line)this.newState;
			Line oldState = (Line)this.oldState;
			if(oldState == null) {
				newState.setpBegin(original.getpBegin());
				newState.setpEnd(original.getpEnd());
				newState.setColor(original.getColor());
				return;
			}
			
			original.setpBegin(oldState.getpBegin());
			original.setpEnd(oldState.getpEnd());
			original.setColor(oldState.getColor());
		} else if(check == 3) {
			Circle original = (Circle)this.original;
			Circle newState = (Circle)this.newState;
			Circle oldState = (Circle)this.oldState;
			if (oldState == null) {
				newState.setCenter(original.getCenter());
				newState.setColor(original.getColor());
				newState.setAreaColor(original.getAreaColor());
				newState.setR(original.getR());
				newState.setId(original.getId());
				return;
			}
			original.setCenter(oldState.getCenter());
			original.setR(oldState.getR());
			original.setColor(oldState.getColor());
			original.setAreaColor(oldState.getAreaColor());
			original.setId(oldState.getId());
			
		} else if(check == 4) {
			Square original = (Square)this.original;
			Square newState = (Square)this.newState;
			Square oldState = (Square)this.oldState;
			if (oldState == null) {
				newState.setUpperLeft(original.getUpperLeft());
				newState.setColor(original.getColor());
				newState.setAreaColor(original.getAreaColor());
				newState.setN(original.getN());
				newState.setId(original.getId());
				return;
			}
			original.setUpperLeft(oldState.getUpperLeft());
			original.setN(oldState.getN());
			original.setColor(oldState.getColor());
			original.setAreaColor(oldState.getAreaColor());
			original.setId(oldState.getId());
		} else if(check == 5) {
			Rectangle original = (Rectangle)this.original;
			Rectangle newState = (Rectangle)this.newState;
			Rectangle oldState = (Rectangle)this.oldState;
			if (oldState == null) {
				newState.setUpperLeft(original.getUpperLeft());
				newState.setColor(original.getColor());
				newState.setAreaColor(original.getAreaColor());
				newState.setN(original.getN());
				newState.setHeight(original.getHeight());
				newState.setId(original.getId());
				return;
			}
			original.setUpperLeft(oldState.getUpperLeft());
			original.setN(oldState.getN());
			original.setHeight(oldState.getHeight());
			original.setColor(oldState.getColor());
			original.setAreaColor(oldState.getAreaColor());
			original.setId(oldState.getId());
			
		} else if(check == 6) {
			HexagonAdapter original = (HexagonAdapter)this.original;
			HexagonAdapter newState = (HexagonAdapter)this.newState;
			HexagonAdapter oldState = (HexagonAdapter)this.oldState;
			if (oldState == null) {
				newState.setX(original.getX());
				newState.setY(original.getY());
				newState.setColor(original.getColor());
				newState.setAreaColor(original.getAreaColor());
				newState.setR(original.getR());
				newState.setId(original.getId());
				return;
			}
			original.setX(oldState.getX());
			original.setY(oldState.getY());
			original.setR(oldState.getR());
			original.setColor(oldState.getColor());
			original.setAreaColor(oldState.getAreaColor());
			original.setId(oldState.getId());
		}
		
		controller.notifyAllObserversRedoUndo();
		
	}
	
	public String toString() {
		return "Command:Update;NewState:["+newState.toString()+"];OldState:["+oldState.toString()+"]";
	}

}
