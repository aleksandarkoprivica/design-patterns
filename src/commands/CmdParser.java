package commands;

import java.awt.Color;
import java.util.ArrayList;

import geometry.Circle;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Square;
import hexagon.Hexagon;
import mvc.DrawingController;
import mvc.DrawingModel;

public class CmdParser {
	
	public static Command createCmd(String cmdString, DrawingModel model, CommandList cmdList, DrawingController controller, ArrayList<Shape> selectedShapes) {
		
		String[] cmdProperties = cmdString.split(";");
		String cmdType = cmdProperties[0];
		
		String[] operation = cmdProperties[1].split(":");
		String cmdOperation = operation[1];
		
		String[] shapes = cmdProperties[2].split(":");
		String cmdShape = shapes[1];
		
		if(cmdProperties[0].equals("[New Command]") || cmdProperties[0].equals("[Redo]")) {
			
			if(cmdOperation.equals("BringToFront")) {
				Shape shape = new Point();
				shape.setId(parseValue(cmdProperties[2].split(":")));
				for(int i=0; i < model.getShapes().size(); i++) {
					if(model.getShape(i).getId() == shape.getId()) {
						shape = model.getShape(i);
						break;
					}
				}
				Command cmdBringToFront = new CmdBringToFront(model, shape, controller, controller.getPosition2());
				
				return cmdBringToFront;
			} else if(cmdOperation.equals("BringToBack")) {
				Shape shape = new Point();
				shape.setId(parseValue(cmdProperties[2].split(":")));
				for (int i = 0; i < model.getShapes().size();i++) {
					if (model.getShape(i).getId() == shape.getId()) {
						shape = model.getShape(i);
						break;
					}
				}
				Command cmdBringToBack = new CmdBringToBack(model, shape, controller, controller.getPosition());
				
				return cmdBringToBack;
			} else if(cmdOperation.equals("ToFront")){
				Shape shape = new Point();
				shape.setId(parseValue(cmdProperties[2].split(":")));
				for (int i = 0; i < model.getShapes().size();i++) {
					if (model.getShape(i).getId() == shape.getId()) {
						shape = model.getShape(i);
						break;
					}
				}
				Command cmdToFront = new CmdToFront(model, shape, controller);
				
				return cmdToFront;
			} else if (cmdOperation.equals("ToBack")){
				Shape shape = new Point();
				shape.setId(parseValue(cmdProperties[2].split(":")));
				for (int i = 0; i < model.getShapes().size();i++) {
					if (model.getShape(i).getId() == shape.getId()) {
						shape = model.getShape(i);
						break;
					}
				}
				Command cmdToBack = new CmdToBack(model, shape, controller);
				
				return cmdToBack;
			} else if(cmdOperation.equals("Add")) {
				Shape shape=null;
				if(cmdShape.equals("Point")) {
					String[] coordinates = cmdProperties[3].split(":");
					shape = parsePoint(coordinates[1]);
					String[] colorString = cmdProperties[4].split(":");
					shape.setColor(parseColor(colorString[1]));
					int id = Integer.parseInt(cmdProperties[5].split(":")[1]);
					shape.setId(id);
					Command cmdAdd = new CmdAddShape(model, shape,controller);
					controller.setHelper(controller.getHelper()+1);
					
					return cmdAdd;
				} else if(cmdShape.equals("Line")) {
					shape = parseLine(cmdProperties,3);
					Command cmdAdd = new CmdAddShape(model, shape,controller);
					controller.setHelper(controller.getHelper()+1);
					
					return cmdAdd;
				} else if(cmdShape.equals("Circle")) {
					shape = parseCircle(cmdProperties,3);
					Command cmdAdd = new CmdAddShape(model, shape,controller);
					controller.setHelper(controller.getHelper()+1);
					
					return cmdAdd;
				} else if(cmdShape.equals("Square")) {
					shape = parseSquare(cmdProperties,3);
					Command cmdAdd = new CmdAddShape(model, shape,controller);
					controller.setHelper(controller.getHelper()+1);
					
					return cmdAdd;
				} else if(cmdShape.equals("Rectangle")) {
					shape = parseRectangle(cmdProperties,3);
					Command cmdAdd = new CmdAddShape(model, shape,controller);
					controller.setHelper(controller.getHelper()+1);
					
					return cmdAdd;
				} else if(cmdShape.equals("Hexagon")) {
					shape = parseHexagon(cmdProperties,3);
					Command cmdAdd = new CmdAddShape(model, shape,controller);
					controller.setHelper(controller.getHelper()+1);
					
					return cmdAdd;
				}
			} else if(cmdOperation.equals("Update")) {
				Shape shape = null;
				int cmdTypeEnd = cmdString.indexOf("]");
				cmdString = cmdString.substring(cmdTypeEnd+2);
				
				int startOfOldState = cmdString.lastIndexOf("[");
				int endOfOldState = cmdString.lastIndexOf("]");
				String oldState = cmdString.substring(startOfOldState, endOfOldState);
				oldState = oldState.replace("[", "");
				oldState = oldState.replace("]", "");
				
				int startOfNewState = cmdString.indexOf("[");
				int endOfNewState = cmdString.indexOf("]");
				String newState = cmdString.substring(startOfNewState, endOfNewState);
				newState = newState.replace("[", "");
				newState = newState.replace("]", "");
				
				String[] oldStateProperties = oldState.split(";");
				String[] newStateProperties = newState.split(";");
				cmdShape = oldStateProperties[0].split(":")[1];
				
				if(cmdShape.equals("Point")) {
					
					Shape shapeOldState = parsePoint(oldStateProperties[1].split(":")[1]);
					shapeOldState.setColor(parseColor(oldStateProperties[2].split(":")[1]));
					shapeOldState.setId(parseValue(oldStateProperties[3].split(":")));
					
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShape(i).getId() == shapeOldState.getId()) {
							
							shapeOldState = model.getShape(i);
							break;
						}
					}
					
					Shape shapeNewState = parsePoint(newStateProperties[1].split(":")[1]);
					shapeNewState.setColor(parseColor(newStateProperties[2].split(":")[1]));
					shapeNewState.setId(parseValue(newStateProperties[3].split(":")));
					
					Command cmdModify = new CmdUpdateShape(shapeOldState, shapeNewState, controller);
					
					return cmdModify;
				} else if(cmdShape.equals("Line")) {
					
					Shape shapeOldState = parseLine(oldStateProperties, 1);
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShape(i).getId() == shapeOldState.getId()) {
							shapeOldState = model.getShape(i);
							break;
						}
					}
					Shape shapeNewState = parseLine(newStateProperties, 1);
					Command cmdModify = new CmdUpdateShape(shapeOldState, shapeNewState,controller); 
					
					return cmdModify;
				} else if(cmdShape.equals("Circle")) {
					
					Shape shapeOldState = parseCircle(oldStateProperties, 1);
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShape(i).getId() == shapeOldState.getId()) {
							shapeOldState = model.getShape(i);
							break;
						}
					}
					Shape shapeNewState = parseCircle(newStateProperties, 1);
					Command cmdModify = new CmdUpdateShape(shapeOldState, shapeNewState,controller); 
					
					return cmdModify;
				} else if(cmdShape.equals("Square")) {
					
					Shape shapeOldState = parseSquare(oldStateProperties, 1);
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShape(i).getId() == shapeOldState.getId()) {
							shapeOldState = model.getShape(i);
							break;
						}
					}
					Shape shapeNewState = parseSquare(newStateProperties, 1);
					Command cmdModify = new CmdUpdateShape(shapeOldState, shapeNewState,controller); 
					
					return cmdModify;
				} else if(cmdShape.equals("Rectangle")) {
					
					Shape shapeOldState = parseRectangle(oldStateProperties, 1);
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShape(i).getId() == shapeOldState.getId()) {
							shapeOldState = model.getShape(i);
							break;
						}
					}
					Shape shapeNewState = parseRectangle(newStateProperties, 1);
					Command cmdModify = new CmdUpdateShape(shapeOldState, shapeNewState,controller); 
					
					return cmdModify;
				} else if(cmdShape.equals("Hexagon")) {
					
					Shape shapeOldState = parseHexagon(oldStateProperties, 1);
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShape(i).getId() == shapeOldState.getId()) {
							shapeOldState = model.getShape(i);
							break;
						}
					}
					Shape shapeNewState = parseHexagon(newStateProperties, 1);
					Command cmdModify = new CmdUpdateShape(shapeOldState, shapeNewState,controller);
					
					return cmdModify;
				}
				
			} else if(cmdOperation.equals("Select")) {
				
				Shape shape = null;
				if(cmdShape.equals("Point")) {
					
					String[] coord = cmdProperties[3].split(":");
					Shape point = parsePoint(coord[1]);
					String[] colorString = cmdProperties[4].split(":");
					point.setColor(parseColor(colorString[1]));
					int id = Integer.parseInt(cmdProperties[5].split(":")[1]);
					point.setId(id);
					return helpSelect(point, shape, model, controller, selectedShapes);
				} else if(cmdShape.equals("Line")) {
					
					Shape line = parseLine(cmdProperties,3);

					return helpSelect(line, shape, model, controller, selectedShapes);
				} else if(cmdShape.equals("Circle")) {
					Shape circle = parseCircle(cmdProperties,3);
					
					return helpSelect(circle,shape,model,controller,selectedShapes);
				} else if(cmdShape.equals("Square")) {
					Shape square = parseSquare(cmdProperties,3);
					controller.setCurrent(square);
					return helpSelect(square, shape,model,controller,selectedShapes);
				} else if(cmdShape.equals("Rectangle")) {
					Shape rectangle = parseRectangle(cmdProperties,3);
					
					return helpSelect(rectangle, shape, model, controller, selectedShapes);
				} else if(cmdShape.equals("Hexagon")) {
					Shape hexagon = parseHexagon(cmdProperties,3);
					
					return helpSelect(hexagon, shape, model, controller, selectedShapes);
				}
			} else if(cmdOperation.equals("Deselect")) {
				Shape shape = null;
				if(cmdShape.equals("Point")) {
					String[] coord = cmdProperties[3].split(":");
					Shape point = parsePoint(coord[1]);
					String[] colorString = cmdProperties[4].split(":");
					point.setColor(parseColor(colorString[1]));
					int id = Integer.parseInt(cmdProperties[5].split(":")[1]);
					point.setId(id);
					
					return helpDeselect(point, shape, model, controller, selectedShapes);
				} else if(cmdShape.equals("Line")) {
					
					Shape line = parseLine(cmdProperties,3);

					return helpDeselect(line, shape, model, controller, selectedShapes);
				} else if(cmdShape.equals("Circle")) {
					Shape circle = parseCircle(cmdProperties,3);
					
					return helpDeselect(circle,shape,model,controller,selectedShapes);
				} else if(cmdShape.equals("Square")) {
					Shape square = parseSquare(cmdProperties,3);
					
					return helpDeselect(square, shape,model,controller,selectedShapes);
				} else if(cmdShape.equals("Rectangle")) {
					Shape rectangle = parseRectangle(cmdProperties,3);
					
					return helpDeselect(rectangle, shape, model, controller, selectedShapes);
				} else if(cmdShape.equals("Hexagon")) {
					Shape hexagon = parseHexagon(cmdProperties,3);
					
					return helpDeselect(hexagon, shape, model, controller, selectedShapes);
				}
			} else if(cmdOperation.equals("Delete")) {
				Shape shape = null;
				
				if(cmdShape.equals("Point")) {
					String[] coord = cmdProperties[3].split(":");
					Shape point = parsePoint(coord[1]);
					String[] colorString = cmdProperties[4].split(":");
					point.setColor(parseColor(colorString[1]));
					int id = Integer.parseInt(cmdProperties[5].split(":")[1]);
					point.setId(id);
					
					return helpDelete(point, shape, model, controller);
				} else if(cmdShape.equals("Line")) {
					
					Shape line = parseLine(cmdProperties,3);
					
					return helpDelete(line, shape, model, controller);
				} else if(cmdShape.equals("Circle")) {
					
					Shape circle = parseCircle(cmdProperties,3);
					
					return helpDelete(circle, shape, model, controller);
				} else if(cmdShape.equals("Square")) {
					
					Shape square = parseSquare(cmdProperties, 3);
					
					return helpDelete(square, shape, model, controller);
				} else if(cmdShape.equals("Rectangle")) {
					
					Shape rectangle = parseRectangle(cmdProperties, 3);
					
					return helpDelete(rectangle, shape, model, controller);
				} else if(cmdShape.equals("Hexagon")) {
					
					Shape hexagon = parseHexagon(cmdProperties, 3);
					
					return helpDelete(hexagon, shape, model, controller);
				}
			}
			
		} else if(cmdProperties[0].equals("[Undo]")) {
			
			if(cmdOperation.equals("BringToFront")) {
				Shape shape = new Point();
				shape.setId(parseValue(cmdProperties[2].split(":")));
				for(int i=0; i < model.getShapes().size(); i++) {
					if(model.getShape(i).getId() == shape.getId()) {
						shape = model.getShape(i);
						break;
					}
				}
				Command cmdBringToFront = new CmdBringToFront(model, shape, controller, controller.getPosition2());
				
				return cmdBringToFront;
			} else if(cmdOperation.equals("BringToBack")) {
				Shape shape = new Point();
				shape.setId(parseValue(cmdProperties[2].split(":")));
				for (int i = 0; i < model.getShapes().size();i++) {
					if (model.getShape(i).getId() == shape.getId()) {
						shape = model.getShape(i);
						break;
					}
				}
				Command cmdBringToBack = new CmdBringToBack(model, shape, controller, controller.getPosition());
				
				return cmdBringToBack;
			} else if(cmdOperation.equals("ToFront")){
				Shape shape = new Point();
				shape.setId(parseValue(cmdProperties[2].split(":")));
				for (int i = 0; i < model.getShapes().size();i++) {
					if (model.getShape(i).getId() == shape.getId()) {
						shape = model.getShape(i);
						break;
					}
				}
				Command cmdToFront = new CmdToFront(model, shape, controller);
				
				return cmdToFront;
			} else if (cmdOperation.equals("ToBack")){
				Shape shape = new Point();
				shape.setId(parseValue(cmdProperties[2].split(":")));
				for (int i = 0; i < model.getShapes().size();i++) {
					if (model.getShape(i).getId() == shape.getId()) {
						shape = model.getShape(i);
						break;
					}
				}
				Command cmdToBack = new CmdToBack(model, shape, controller);
				
				return cmdToBack;
			} else if(cmdOperation.equals("Add")) {
				Shape shape = null;
				if(cmdShape.equals("Point")) {
					String[] coordinates = cmdProperties[3].split(":");
					Shape point = parsePoint(coordinates[1]);
					String[] colorString = cmdProperties[4].split(":");
					shape.setColor(parseColor(colorString[1]));
					int id = Integer.parseInt(cmdProperties[5].split(":")[1]);
					point.setId(id);
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShape(i).getId()==point.getId()) {
							shape = model.getShape(i);
							break;
						}
					}
					controller.setHelper(controller.getHelper()-1);
					Command cmdAdd = new CmdAddShape(model, shape,controller);
					
					return cmdAdd;
				} else if(cmdShape.equals("Line")) {
					Shape line = parseLine(cmdProperties,3);
					
					return helpAdd(line, shape, model, controller);
					
				} else if(cmdShape.equals("Circle")) {
					Shape circle = parseCircle(cmdProperties,3);
					
					return helpAdd(circle, shape, model, controller);
					
				} else if(cmdShape.equals("Square")) {
					Shape square = parseSquare(cmdProperties,3);
					
					return helpAdd(square, shape, model, controller);
					
				} else if(cmdShape.equals("Rectangle")) {
					Shape rectangle = parseRectangle(cmdProperties,3);

					return helpAdd(rectangle, shape, model, controller);
					
				} else if(cmdShape.equals("Hexagon")) {
					Shape hexagon = parseHexagon(cmdProperties,3);

					return helpAdd(hexagon, shape, model, controller);
				}
			} else if(cmdOperation.equals("Deselect")) {
				Shape shape = null;
				if(cmdShape.equals("Point")) {
					String[] coord = cmdProperties[3].split(":");
					Shape point = parsePoint(coord[1]);
					String[] colorString = cmdProperties[4].split(":");
					point.setColor(parseColor(colorString[1]));
					int id = Integer.parseInt(cmdProperties[5].split(":")[1]);
					point.setId(id);
					
					return helpDeselect(point, shape, model, controller, selectedShapes);
				} else if(cmdShape.equals("Line")) {
					
					Shape line = parseLine(cmdProperties,3);

					return helpDeselect(line, shape, model, controller, selectedShapes);
				} else if(cmdShape.equals("Circle")) {
					Shape circle = parseCircle(cmdProperties,3);
					
					return helpDeselect(circle,shape,model,controller,selectedShapes);
				} else if(cmdShape.equals("Square")) {
					Shape square = parseSquare(cmdProperties,3);
					
					return helpDeselect(square, shape,model,controller,selectedShapes);
				} else if(cmdShape.equals("Rectangle")) {
					Shape rectangle = parseRectangle(cmdProperties,3);
					
					return helpDeselect(rectangle, shape, model, controller, selectedShapes);
				} else if(cmdShape.equals("Hexagon")) {
					Shape hexagon = parseHexagon(cmdProperties,3);
					
					return helpDeselect(hexagon, shape, model, controller, selectedShapes);
				}
			} else if(cmdOperation.equals("Select")) {
				Shape shape = null;
				if(cmdShape.equals("Point")) {
					
					String[] coord = cmdProperties[3].split(":");
					Shape point = parsePoint(coord[1]);
					String[] colorString = cmdProperties[4].split(":");
					point.setColor(parseColor(colorString[1]));
					int id = Integer.parseInt(cmdProperties[5].split(":")[1]);
					point.setId(id);
					
					return helpSelect(point, shape, model, controller, selectedShapes);
				} else if(cmdShape.equals("Line")) {
					
					Shape line = parseLine(cmdProperties,3);

					return helpSelect(line, shape, model, controller, selectedShapes);
				} else if(cmdShape.equals("Circle")) {
					Shape circle = parseCircle(cmdProperties,3);
					
					return helpSelect(circle,shape,model,controller,selectedShapes);
				} else if(cmdShape.equals("Square")) {
					Shape square = parseSquare(cmdProperties,3);
					
					return helpSelect(square, shape,model,controller,selectedShapes);
				} else if(cmdShape.equals("Rectangle")) {
					Shape rectangle = parseRectangle(cmdProperties,3);
					
					return helpSelect(rectangle, shape, model, controller, selectedShapes);
				} else if(cmdShape.equals("Hexagon")) {
					Shape hexagon = parseHexagon(cmdProperties,3);
					
					return helpSelect(hexagon, shape, model, controller, selectedShapes);
				}
			} else if(cmdOperation.equals("Delete")) {
				Shape shape = null;
				
				if(cmdShape.equals("Point")) {
					String[] coord = cmdProperties[3].split(":");
					Shape point = parsePoint(coord[1]);
					String[] colorString = cmdProperties[4].split(":");
					point.setColor(parseColor(colorString[1]));
					int id = Integer.parseInt(cmdProperties[5].split(":")[1]);
					point.setId(id);
					
					return helpDeleteUndo(point, shape, model, controller);
				} else if(cmdShape.equals("Line")) {
					
					Shape line = parseLine(cmdProperties,3);
					
					return helpDeleteUndo(line, shape, model, controller);
				} else if(cmdShape.equals("Circle")) {
					
					Shape circle = parseCircle(cmdProperties,3);
					
					return helpDeleteUndo(circle, shape, model, controller);
				} else if(cmdShape.equals("Square")) {
					
					Shape square = parseSquare(cmdProperties, 3);
					
					return helpDeleteUndo(square, shape, model, controller);
				} else if(cmdShape.equals("Rectangle")) {
					
					Shape rectangle = parseRectangle(cmdProperties, 3);
					
					return helpDeleteUndo(rectangle, shape, model, controller);
				} else if(cmdShape.equals("Hexagon")) {
					
					Shape hexagon = parseHexagon(cmdProperties, 3);
					
					return helpDeleteUndo(hexagon, shape, model, controller);
				}
			} else if(cmdOperation.equals("Update")) {
				Shape shape = null;
				int cmdTypeEnd = cmdString.indexOf("]");
				cmdString = cmdString.substring(cmdTypeEnd+2);
				
				int startOfOldState = cmdString.indexOf("[");
				int endOfOldState = cmdString.indexOf("]");
				String oldState = cmdString.substring(startOfOldState, endOfOldState);
				oldState = oldState.replace("[", "");
				oldState = oldState.replace("]", "");
				
				int startOfNewState = cmdString.lastIndexOf("[");
				int endOfNewState = cmdString.lastIndexOf("]");
				String newState = cmdString.substring(startOfNewState, endOfNewState);
				newState = newState.replace("[", "");
				newState = newState.replace("]", "");
				
				String[] oldStateProperties = oldState.split(";");
				String[] newStateProperties = newState.split(";");
				cmdShape = oldStateProperties[0].split(":")[1];
				
				if(cmdShape.equals("Point")) {
					
					Shape shapeOldState = parsePoint(oldStateProperties[1].split(":")[1]);
					shapeOldState.setColor(parseColor(oldStateProperties[2].split(":")[1]));
					shapeOldState.setId(parseValue(oldStateProperties[3].split(":")));
					
					Shape shapeNewState = parsePoint(newStateProperties[1].split(":")[1]);
					shapeNewState.setColor(parseColor(newStateProperties[2].split(":")[1]));
					shapeNewState.setId(parseValue(newStateProperties[3].split(":")));
					
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShape(i).getId() == shapeNewState.getId()) {
							
							shapeNewState = model.getShape(i);
							break;
						}
					}
					
					Command cmdModify = new CmdUpdateShape(shapeOldState, shapeNewState, controller);
					
					return cmdModify;
				} else if(cmdShape.equals("Line")) {
					
					Shape shapeOldState = parseLine(oldStateProperties, 1);
					Shape shapeNewState = parseLine(newStateProperties, 1);
					
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShape(i).getId() == shapeNewState.getId()) {
							shapeNewState = model.getShape(i);
							break;
						}
					}

					Command cmdModify = new CmdUpdateShape(shapeOldState, shapeNewState,controller);
					
					
					return cmdModify;
				} else if(cmdShape.equals("Circle")) {
					
					Shape shapeOldState = parseCircle(oldStateProperties, 1);
					Shape shapeNewState = parseCircle(newStateProperties, 1);
					
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShape(i).getId() == shapeNewState.getId()) {
							shapeNewState = model.getShape(i);
							break;
						}
					}
					
					Command cmdModify = new CmdUpdateShape(shapeOldState, shapeNewState,controller); 
					
					return cmdModify;
				} else if(cmdShape.equals("Square")) {
					
					Shape shapeOldState = parseSquare(oldStateProperties, 1);
					Shape shapeNewState = parseSquare(newStateProperties, 1);
					
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShape(i).getId() == shapeNewState.getId()) {
							shapeNewState = model.getShape(i);
							break;
						}
					}
					
					Command cmdModify = new CmdUpdateShape(shapeOldState, shapeNewState,controller); 
					
					
					return cmdModify;
				} else if(cmdShape.equals("Rectangle")) {
					
					Shape shapeOldState = parseRectangle(oldStateProperties, 1);
					Shape shapeNewState = parseRectangle(newStateProperties, 1);
					
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShape(i).getId() == shapeNewState.getId()) {
							shapeNewState = model.getShape(i);
							break;
						}
					}
					
					Command cmdModify = new CmdUpdateShape(shapeOldState, shapeNewState,controller); 
					
					return cmdModify;
				} else if(cmdShape.equals("Hexagon")) {
					
					Shape shapeOldState = parseHexagon(oldStateProperties, 1);
					Shape shapeNewState = parseHexagon(newStateProperties, 1);
					
					for (int i = 0; i < model.getShapes().size(); i++) {
						if (model.getShape(i).getId() == shapeNewState.getId()) {
							shapeNewState = model.getShape(i);
							break;
						}
					}
					
					Command cmdModify = new CmdUpdateShape(shapeOldState, shapeNewState,controller);
					
					return cmdModify;
				}
			}
			
		}
		
		return null;
	}
	
	private static Command helpAdd(Shape shapeHelp, Shape shape, DrawingModel model, DrawingController controller){
		
		for (int i = 0; i < model.getShapes().size(); i++) {
			if (model.getShape(i).getId()==shapeHelp.getId()) {
				shape = model.getShape(i);
				break;
			}
		}
		controller.setHelper(controller.getHelper()-1);
		Command cmdAdd = new CmdAddShape(model, shape,controller);
		
		return cmdAdd;
	}
	
	private static Command helpSelect(Shape shapeHelp, Shape shape, DrawingModel model, DrawingController controller, ArrayList<Shape> selectedShapes) {
		
		for (int i = 0; i < model.getShapes().size(); i++) {
			if (model.getShape(i).getId() == shapeHelp.getId()) {
				shape = model.getShape(i);
				break;
			}
		}
		Command cmdSelect = new CmdSelectShape(model,shape,controller, selectedShapes);
		
		return cmdSelect;
	}
	
	private static Command helpDeselect(Shape shapeHelp, Shape shape, DrawingModel model, DrawingController controller, ArrayList<Shape> selectedShapes) {
		
		for (int i = 0; i < model.getShapes().size(); i++) {
			if (model.getShape(i).getId() == shapeHelp.getId()) {
				shape = model.getShape(i);
				break;
			}
		}
		Command cmdDeselect = new CmdDeselectShape(model,shape,controller, selectedShapes);
		
		return cmdDeselect;
	}
	
	private static Command helpDelete(Shape shapeHelp, Shape shape, DrawingModel model, DrawingController controller) {
		
		for (int i = 0; i < model.getShapes().size(); i++) {
			if (model.getShape(i).getId() == shapeHelp.getId()) {
				shape = model.getShape(i);
				break;
			}
		}
		controller.setHelper(controller.getHelper()-1);
		Command cmdDelete = new CmdRemoveShape(model, shape,controller);
		
		return cmdDelete;
	}
	
	private static Command helpDeleteUndo(Shape shapeHelp, Shape shape, DrawingModel model, DrawingController controller) {
		for (int i = 0; i < model.getDeletedShapes().size(); i++) {
			if (model.getDeletedShapes().get(i).getId() == shapeHelp.getId()) {
				shape = model.getDeletedShapes().get(i);
				break;
			}
		}
		controller.setHelper(controller.getHelper()+1);
		Command cmdDelete = new CmdRemoveShape(model, shape,controller);
		
		return cmdDelete;
	}
	
	private static Point parsePoint(String point) {
		point = point.replace("(", "");
		point = point.replace(")", "");
		String[] coord = point.split(",");
		
		Point newPoint = new Point(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));
		return newPoint;
	}
	
	private static Line parseLine(String[] cmdProperties, int i) {
		
		String[] cmdStartPoint = cmdProperties[i].split(":");
		Point startPoint = parsePoint(cmdStartPoint[1]);
		String[] cmdEndPoint = cmdProperties[i+1].split(":");
		Point endPoint = parsePoint(cmdEndPoint[1]);
		String[] edgeColorString = cmdProperties[i+2].split(":");
		Color edgeColor = parseColor(edgeColorString[1]);
		int id = Integer.parseInt(cmdProperties[i+3].split(":")[1]);
		
		Line line = new Line(startPoint, endPoint, edgeColor);
		line.setId(id);
		
		return line;
	}
	
	private static Square parseSquare(String[] cmdProperties, int i) {
		String[] upperLeftString = cmdProperties[i].split(":");
		Point upperLeft = parsePoint(upperLeftString[1]);
		int sideLength = parseValue(cmdProperties[i+1].split(":"));
		Color edgeColor = parseColor(cmdProperties[i+2].split(":")[1]);
		Color areaColor = parseColor(cmdProperties[i+3].split(":")[1]);
		int id = Integer.parseInt(cmdProperties[i+4].split(":")[1]);
		
		Square square = new Square(upperLeft, sideLength, edgeColor, areaColor);
		square.setId(id);
		return square;
	}
	
	private static Rectangle parseRectangle(String[] cmdProperties,int i) {
		String[] upperLeftString = cmdProperties[i].split(":");
		Point upperLeft = parsePoint(upperLeftString[1]);
		int sideLength = parseValue(cmdProperties[i+1].split(":"));
		int height = parseValue(cmdProperties[i+2].split(":"));
		Color edgeColor = parseColor(cmdProperties[i+3].split(":")[1]);
		Color areaColor = parseColor(cmdProperties[i+4].split(":")[1]);
		int id = Integer.parseInt(cmdProperties[i+5].split(":")[1]);
		Rectangle rectangle = new Rectangle(upperLeft, sideLength, height, edgeColor, areaColor);
		rectangle.setId(id);
		
		return rectangle;
	}
	
	private static Circle parseCircle(String[] cmdProperties, int i) {
		String[] upperLeftString = cmdProperties[i].split(":");
		Point center = parsePoint(upperLeftString[1]);
		int radius = parseValue(cmdProperties[i+1].split(":"));
		Color edgeColor = parseColor(cmdProperties[i+2].split(":")[1]);
		Color areaColor = parseColor(cmdProperties[i+3].split(":")[1]);
		int id = Integer.parseInt(cmdProperties[i+4].split(":")[1]);
		Circle circle = new Circle(center, radius, edgeColor, areaColor);
		circle.setId(id);
		
		return circle;
	}
	
	private static HexagonAdapter parseHexagon(String[] cmdProperties, int i) {
		Point center = parsePoint(cmdProperties[i].split(":")[1]);
		int radius = parseValue(cmdProperties[i+1].split(":"));
		Color edgeColor = parseColor(cmdProperties[i+2].split(":")[1]);
		Color areaColor = parseColor(cmdProperties[i+3].split(":")[1]);
		int id = Integer.parseInt(cmdProperties[i+4].split(":")[1]);
		HexagonAdapter hexagon = new HexagonAdapter(new Hexagon(center.getX(), center.getY() ,radius), edgeColor, areaColor);
		hexagon.setId(id);
		
		return hexagon;
	}
	
	private static Color parseColor(String color) {
		
		color = color.replace(" ", "");
		String[] colorValues = color.split(",");
		int R = Integer.parseInt(colorValues[0]);
		int G = Integer.parseInt(colorValues[1]);
		int B = Integer.parseInt(colorValues[2]);
		Color parsedColor = new Color(R, G, B);
		return parsedColor;
	}
	
	private static int parseValue(String[] string) {
		
		return Integer.parseInt(string[1]);
	}

}
