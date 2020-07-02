package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import commands.CmdAddShape;
import commands.CmdBringToBack;
import commands.CmdBringToFront;
import commands.CmdDeselectShape;
import commands.CmdParser;
import commands.CmdRemoveShape;
import commands.CmdSelectShape;
import commands.CmdToBack;
import commands.CmdToFront;
import commands.CmdUpdateShape;
import commands.Command;
import commands.CommandList;
import dialogs.DlgCircle;
import dialogs.DlgHexagon;
import dialogs.DlgLine;
import dialogs.DlgPoint;
import dialogs.DlgRectangle;
import dialogs.DlgSquare;
import fileManager.ExportLog;
import fileManager.ExportManager;
import fileManager.ExportSerializedShapes;
import fileManager.ImportDrawing;
import fileManager.ImportLog;
import fileManager.ImportManager;
import geometry.Circle;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Square;
import hexagon.Hexagon;
import observer.Observer;
import observer.Subject;

public class DrawingController implements Subject {
		
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private ArrayList<Observer> observersUndoRedo = new ArrayList<Observer>();
	
	private JFileChooser jfc;
	private ExportManager manager;
	private ExportManager saveLog;
	private BufferedReader bufferLog;
	private ImportManager importer;
	
	private ArrayList<Shape> selectedShapes;
	
	private Shape current;
	private int position;
	private int position2;
	private int helper = 0;
	private int countLine;
	private int countUndo;
	private int x,y;
	private int openCounter = 0;
	int counter = 0;
	private Point firstPointLine;
	
	private CmdUpdateShape updateShape;
	
	private DrawingModel model;
	private DrawingFrame frame;
	
	private CommandList cmdList = new CommandList();

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		cmdList.setCommandListener(frame.getTextArea());
		saveLog = new ExportManager(new ExportLog());
		importer = new ImportManager(new ImportLog());
		selectedShapes = new ArrayList<Shape>();
		initialCheck();
	}
	
	public Shape select(int x, int y) {
		
		for(int i = model.getShapes().size() -1; i>= 0; i-- ) {
			if(model.getShapes().get(i).isSelected() && model.getShapes().get(i).contains(x, y)) {
				model.getShapes().get(i).setSelected(false);
				CmdDeselectShape temp = new CmdDeselectShape(model, model.getShapes().get(i), this, selectedShapes);
				cmdList.add(temp);
				cmdList.setPosition(cmdList.getCommands().size() - 1);
				cmdList.newCommand(true);
				selectedShapes.remove(model.getShapes().get(i));
				System.out.println("Deselect: " +selectedShapes);
				frame.setCountUndo(0);
				notifyAllObservers();
				notifyAllObserversRedoUndo();
				checkExecute();
				frame.getView().repaint();
				if(i==0) {
					return null;
				}else {
				return model.getShapes().get(i-1);
				}
			} else if (model.getShapes().get(i).contains(x, y)) {
				model.getShapes().get(i).setSelected(true);
				CmdSelectShape temp = new CmdSelectShape(model, model.getShapes().get(i), this, selectedShapes);
				cmdList.add(temp);
				cmdList.setPosition(cmdList.getCommands().size() - 1);
				cmdList.newCommand(true);
				selectedShapes.add(model.getShapes().get(i));
				System.out.println("Select: " + selectedShapes);
				frame.setCountUndo(0);
				notifyAllObservers();
				notifyAllObserversRedoUndo();
				checkExecute();
				frame.getView().repaint();
				return model.getShapes().get(i);
			} else {
				
			}
		}
		for(int i = 0; i < model.getShapes().size(); i++) {
			if(model.getShapes().get(i).isSelected()) {
				model.getShapes().get(i).setSelected(false);
				CmdDeselectShape temp = new CmdDeselectShape(model, model.getShapes().get(i), this, selectedShapes);
				cmdList.add(temp);
				cmdList.setPosition(cmdList.getCommands().size() - 1);
				cmdList.newCommand(true);
				selectedShapes.clear();
				notifyAllObservers();
				notifyAllObserversRedoUndo();
				checkExecute();
			}
		}
		
		frame.getView().repaint();
		return null;
	}
	
	public void paint(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(frame.getTglbtnPoint().isSelected()) {
			try {
				if(frame.getEdgeColor() == null) {
						Point p = new Point(x, y, Color.BLACK);
						model.add(p);
						CmdAddShape temp = new CmdAddShape(model, p, this);
						helpPaint(temp);	
				} else {
					Point p = new Point(x, y, frame.getEdgeColor());
					model.add(p);
					CmdAddShape temp = new CmdAddShape(model, p, this);
					helpPaint(temp);
				}
				
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Invalid values!");
			}
		} else if(frame.getTglbtnLine().isSelected()) {
			try {
				if(frame.getEdgeColor() == null) {
					if (countLine %2 == 0)
						firstPointLine = new Point(x, y);
					else {
						
							Line l = new Line(firstPointLine, new Point(x, y), Color.BLACK);
							model.add(l);
							CmdAddShape temp = new CmdAddShape(model, l, this);
							helpPaint(temp);
						
					}
					countLine++;
				} else { 
					if (countLine %2 == 0)
						firstPointLine = new Point(x,y);
					else {
						Line l = new Line(firstPointLine, new Point(x, y), frame.getEdgeColor());
						model.add(l);
						CmdAddShape temp = new CmdAddShape(model, l, this);
						helpPaint(temp);
					}
					countLine++;
				}
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Invalid values!");
			}
		} else if(frame.getTglbtnSquare().isSelected()) {
			DlgSquare dlgS = new DlgSquare();
			dlgS.getTxtX().setText(Integer.toString(x));
			dlgS.getTxtY().setText(Integer.toString(y));
			dlgS.getTxtX().setEditable(false);
			dlgS.getTxtY().setEditable(false);
			
			try {
				
				if(frame.getEdgeColor() == null && frame.getAreaColor() == null) {
					dlgS.setVisible(true);
					if(dlgS.isCheck()) {
					int n = Integer.parseInt(dlgS.getTxtLength().getText());
					if( n <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgS.getPnlColor().getBackground();
					Color areaColor = dlgS.getPnlAreaColor().getBackground();
					
					Square s = new Square(new Point(x, y), n, edgeColor, areaColor);
					model.add(s);
					CmdAddShape temp = new CmdAddShape(model, s, this);
					helpPaint(temp);
					}
				} else if(frame.getEdgeColor() != null && frame.getAreaColor() != null) {
					dlgS.getPnlColor().setBackground(frame.getEdgeColor());
					dlgS.getPnlAreaColor().setBackground(frame.getAreaColor());
					dlgS.setVisible(true);
					if(dlgS.isCheck()) {
					int n = Integer.parseInt(dlgS.getTxtLength().getText());
					if( n <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgS.getPnlColor().getBackground();
					Color areaColor = dlgS.getPnlAreaColor().getBackground();
					
					Square s = new Square(new Point(x, y), n, edgeColor, areaColor);
					model.add(s);
					CmdAddShape temp = new CmdAddShape(model, s, this);
					helpPaint(temp);
					}
				} else if (frame.getEdgeColor() == null && frame.getAreaColor() != null) {
					dlgS.getPnlAreaColor().setBackground(frame.getAreaColor());
					dlgS.setVisible(true);
					if(dlgS.isCheck()) {
					int n = Integer.parseInt(dlgS.getTxtLength().getText());
					if( n <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgS.getPnlColor().getBackground();
					Color areaColor = dlgS.getPnlAreaColor().getBackground();
					
					Square s = new Square(new Point(x, y), n, edgeColor, areaColor);
					model.add(s);
					CmdAddShape temp = new CmdAddShape(model, s, this);
					helpPaint(temp);
					}
				} else {
					dlgS.getPnlColor().setBackground(frame.getEdgeColor());
					dlgS.setVisible(true);
					if(dlgS.isCheck()) {
					int n = Integer.parseInt(dlgS.getTxtLength().getText());
					if( n <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgS.getPnlColor().getBackground();
					Color areaColor = dlgS.getPnlAreaColor().getBackground();
					
					Square s = new Square(new Point(x, y), n, edgeColor, areaColor);
					model.add(s);
					CmdAddShape temp = new CmdAddShape(model, s, this);
					helpPaint(temp);
					}
				}
				
			} catch(Exception ex){
				JOptionPane.showMessageDialog(null, "Invalid values!");
				dlgS.getTxtX().setText("");
				dlgS.getTxtY().setText("");
				dlgS.getTxtLength().setText("");
				dlgS.getPnlColor().setBackground(Color.BLACK);
				dlgS.getPnlAreaColor().setBackground(Color.WHITE);
			}
		} else if(frame.getTglbtnRectangle().isSelected()) {
			DlgRectangle dlgR = new DlgRectangle();
			dlgR.getTxtX().setText(Integer.toString(x));
			dlgR.getTxtY().setText(Integer.toString(y));
			dlgR.getTxtX().setEditable(false);
			dlgR.getTxtY().setEditable(false);
			
			try {
				if (frame.getEdgeColor() == null && frame.getAreaColor() == null) {
					dlgR.setVisible(true);
					if(dlgR.isCheck()) {
					int width = Integer.parseInt(dlgR.getTxtWidth().getText());
					int length = Integer.parseInt(dlgR.getTxtLength().getText());
					if( width <= 0 || length <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgR.getPnlColor().getBackground();
					Color areaColor = dlgR.getPnlAreaColor().getBackground();
					
					Rectangle r = new Rectangle(new Point(x, y), width, length, edgeColor, areaColor);
					model.add(r);
					CmdAddShape temp = new CmdAddShape(model, r, this);
					helpPaint(temp);
					}
				} else if (frame.getEdgeColor() != null && frame.getAreaColor() == null){
					
					dlgR.getPnlColor().setBackground(frame.getEdgeColor());
					dlgR.setVisible(true);
					if(dlgR.isCheck()) {
					int width = Integer.parseInt(dlgR.getTxtWidth().getText());
					int length = Integer.parseInt(dlgR.getTxtLength().getText());
					if( width <= 0 || length <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgR.getPnlColor().getBackground();
					Color areaColor = dlgR.getPnlAreaColor().getBackground();
					
					Rectangle r = new Rectangle(new Point(x, y), width, length, edgeColor, areaColor);
					model.add(r);
					CmdAddShape temp = new CmdAddShape(model, r, this);
					helpPaint(temp);
					}
				} else if (frame.getEdgeColor() == null && frame.getAreaColor() != null){
					
					dlgR.getPnlAreaColor().setBackground(frame.getAreaColor());
					dlgR.setVisible(true);
					if(dlgR.isCheck()) {
					int width = Integer.parseInt(dlgR.getTxtWidth().getText());
					int length = Integer.parseInt(dlgR.getTxtLength().getText());
					if( width <= 0 || length <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgR.getPnlColor().getBackground();
					Color areaColor = dlgR.getPnlAreaColor().getBackground();
					
					Rectangle r = new Rectangle(new Point(x, y), width, length, edgeColor, areaColor);
					model.add(r);
					CmdAddShape temp = new CmdAddShape(model, r, this);
					helpPaint(temp);
					}
				} else if (frame.getEdgeColor() != null && frame.getAreaColor() != null){
					
					dlgR.getPnlColor().setBackground(frame.getEdgeColor());
					dlgR.getPnlAreaColor().setBackground(frame.getAreaColor());
					dlgR.setVisible(true);
					if(dlgR.isCheck()) {
					int width = Integer.parseInt(dlgR.getTxtWidth().getText());
					int length = Integer.parseInt(dlgR.getTxtLength().getText());
					if( width <= 0 || length <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgR.getPnlColor().getBackground();
					Color areaColor = dlgR.getPnlAreaColor().getBackground();
					
					Rectangle r = new Rectangle(new Point(x, y), width, length, edgeColor, areaColor);
					model.add(r);
					CmdAddShape temp = new CmdAddShape(model, r, this);
					helpPaint(temp);
					}
				}
			} catch(Exception ex){
				JOptionPane.showMessageDialog(null, "Invalid values!");
				dlgR.getTxtX().setText("");
				dlgR.getTxtY().setText("");
				dlgR.getTxtLength().setText("");
				dlgR.getTxtWidth().setText("");
				dlgR.getPnlColor().setBackground(Color.BLACK);
				dlgR.getPnlAreaColor().setBackground(Color.WHITE);
			}
		} else if(frame.getTglbtnCircle().isSelected()) {
			DlgCircle dlgC = new DlgCircle();
			dlgC.getTxtX().setText(Integer.toString(x));
			dlgC.getTxtY().setText(Integer.toString(y));
			dlgC.getTxtX().setEditable(false);
			dlgC.getTxtY().setEditable(false);
			
			try {
				if(frame.getEdgeColor() == null && frame.getAreaColor() == null) {
					
					dlgC.setVisible(true);
					if(dlgC.isCheck()) {
					int r = Integer.parseInt(dlgC.getTxtR().getText());
					if( r <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgC.getPnlColor().getBackground();
					Color areaColor = dlgC.getPnlAreaColor().getBackground();
					
					Circle c = new Circle(new Point(x,y), r, edgeColor, areaColor);
					model.add(c);
					CmdAddShape temp = new CmdAddShape(model, c, this);
					helpPaint(temp);
					}
				} else if(frame.getEdgeColor() != null && frame.getAreaColor() == null) {
					
					dlgC.getPnlColor().setBackground(frame.getEdgeColor());
					dlgC.setVisible(true);
					if(dlgC.isCheck()) {
					int r = Integer.parseInt(dlgC.getTxtR().getText());
					if( r <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgC.getPnlColor().getBackground();
					Color areaColor = dlgC.getPnlAreaColor().getBackground();
					
					Circle c = new Circle(new Point(x,y), r, edgeColor, areaColor);
					model.add(c);
					CmdAddShape temp = new CmdAddShape(model, c, this);
					helpPaint(temp);
					}
				}else if (frame.getEdgeColor() == null && frame.getAreaColor() != null){
					dlgC.getPnlAreaColor().setBackground(frame.getAreaColor());
					dlgC.setVisible(true);
					if(dlgC.isCheck()) {
					int r = Integer.parseInt(dlgC.getTxtR().getText());
					if( r <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgC.getPnlColor().getBackground();
					Color areaColor = dlgC.getPnlAreaColor().getBackground();
					
					Circle c = new Circle(new Point(x,y), r, edgeColor, areaColor);
					model.add(c);
					CmdAddShape temp = new CmdAddShape(model, c, this);
					helpPaint(temp);
					}
				} else if (frame.getEdgeColor() != null && frame.getAreaColor() != null){
					dlgC.getPnlColor().setBackground(frame.getEdgeColor());
					dlgC.getPnlAreaColor().setBackground(frame.getAreaColor());
					dlgC.setVisible(true);
					if(dlgC.isCheck()) {
					int r = Integer.parseInt(dlgC.getTxtR().getText());
					if( r <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgC.getPnlColor().getBackground();
					Color areaColor = dlgC.getPnlAreaColor().getBackground();
					
					Circle c = new Circle(new Point(x,y), r, edgeColor, areaColor);
					model.add(c);
					CmdAddShape temp = new CmdAddShape(model, c, this);
					helpPaint(temp);
					}
				}
			} catch(Exception ex){
				JOptionPane.showMessageDialog(null, "Invalid values!");
				dlgC.getTxtX().setText("");
				dlgC.getTxtY().setText("");
				dlgC.getTxtR().setText("");
				dlgC.getPnlColor().setBackground(Color.BLACK);
				dlgC.getPnlAreaColor().setBackground(Color.WHITE);
			}
		} else if(frame.getTglbtnHexagon().isSelected()) {
			DlgHexagon dlgH = new DlgHexagon();
			dlgH.getTxtX().setText(Integer.toString(x));
			dlgH.getTxtY().setText(Integer.toString(y));
			dlgH.getTxtX().setEditable(false);
			dlgH.getTxtY().setEditable(false);
			
			try {
				if (frame.getEdgeColor() == null && frame.getAreaColor() == null) {
					dlgH.setVisible(true);
					if(dlgH.isCheck()) {
					int r = Integer.parseInt(dlgH.getTxtR().getText());
					Color edgeColor = dlgH.getPnlColor().getBackground();
					Color areaColor = dlgH.getPnlAreaColor().getBackground();
					if( r <= 0) {
						throw new Exception();
					}
					Shape h = new HexagonAdapter(new Hexagon(x, y, r), edgeColor, areaColor);
					model.add(h);
					CmdAddShape temp = new CmdAddShape(model, h, this);
					helpPaint(temp);
					}
				} else if (frame.getEdgeColor() != null && frame.getAreaColor() == null){
					dlgH.getPnlColor().setBackground(frame.getEdgeColor());
					dlgH.setVisible(true);
					if(dlgH.isCheck()) {
					int r = Integer.parseInt(dlgH.getTxtR().getText());
					if( r <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgH.getPnlColor().getBackground();
					Color areaColor = dlgH.getPnlAreaColor().getBackground();
					
					Shape h = new HexagonAdapter(new Hexagon(x, y, r), edgeColor, areaColor);
					model.add(h);
					CmdAddShape temp = new CmdAddShape(model, h, this);
					helpPaint(temp);
					}
				} else if (frame.getEdgeColor() == null && frame.getAreaColor() != null){
					dlgH.getPnlAreaColor().setBackground(frame.getAreaColor());
					dlgH.setVisible(true);
					if(dlgH.isCheck()) {
					int r = Integer.parseInt(dlgH.getTxtR().getText());
					if( r <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgH.getPnlColor().getBackground();
					Color areaColor = dlgH.getPnlAreaColor().getBackground();
					
					Shape h = new HexagonAdapter(new Hexagon(x, y, r), edgeColor, areaColor);
					model.add(h);
					CmdAddShape temp = new CmdAddShape(model, h, this);
					helpPaint(temp);
					}
				} else if (frame.getEdgeColor() != null && frame.getAreaColor() != null){
					dlgH.getPnlColor().setBackground(frame.getEdgeColor());
					dlgH.getPnlAreaColor().setBackground(frame.getAreaColor());
					dlgH.setVisible(true);
					if(dlgH.isCheck()) {
					int r = Integer.parseInt(dlgH.getTxtR().getText());
					if( r <= 0) {
						throw new Exception();
					}
					Color edgeColor = dlgH.getPnlColor().getBackground();
					Color areaColor = dlgH.getPnlAreaColor().getBackground();
					
					Shape h = new HexagonAdapter(new Hexagon(x, y, r), edgeColor, areaColor);
					model.add(h);
					CmdAddShape temp = new CmdAddShape(model, h, this);
					helpPaint(temp);
					}
				}
			} catch(Exception ex){
				JOptionPane.showMessageDialog(null, "Invalid values!");
				dlgH.getTxtX().setText("");
				dlgH.getTxtY().setText("");
				dlgH.getTxtR().setText("");
				dlgH.getPnlColor().setBackground(Color.BLACK);
				dlgH.getPnlAreaColor().setBackground(Color.WHITE);
			}
		} else if(frame.getTglbtnSelect().isSelected()) {
			current = select(x, y);
			cmdList.newCommand(true);
		}
	}
	
	public void helpPaint (CmdAddShape temp) {
		frame.setCountUndo(0);
		cmdList.add(temp);
		cmdList.setPosition(cmdList.getCommands().size() - 1);
		cmdList.newCommand(true);
		selectedShapes.remove(current);
		checkExecute();
		notifyAllObservers();
		notifyAllObserversRedoUndo();
		for (int i = 0; i < model.getShapes().size(); i++) {
			if (model.getShapes().get(i).isSelected())
				model.getShapes().get(i).setSelected(false);
		}
		
		checkEnable();
		checkEnableButtonGroup();
		if(model.getShapes().size() > 0)
			frame.getBtnUndo().setEnabled(true);
	}
	
	public void modify() {
		for(int i = 0; i < model.getShapes().size(); i++) {
			if(model.getShapes().get(i).isSelected())
				current=model.getShapes().get(i);
		}
		if(current instanceof Point) {
			notifyAllObserversRedoUndo();
			DlgPoint dlgP = new DlgPoint();
			Point helper = (Point)current;
			
			dlgP.getTxtX().setText(Integer.toString(helper.getX()));
			dlgP.getTxtY().setText(Integer.toString(helper.getY()));
			dlgP.getPnlColor().setBackground(helper.getColor());
			
			try {
				dlgP.setVisible(true);
				
				int newX = Integer.parseInt(dlgP.getTxtX().getText());
				int newY = Integer.parseInt(dlgP.getTxtY().getText());
				Color newColor = dlgP.getPnlColor().getBackground();
				if(dlgP.isCheck()) {
				helper.setSelected(true);
				Point newPoint = new Point(newX, newY, newColor);
				
				CmdUpdateShape temp = new CmdUpdateShape(helper, newPoint, this);
				helpModify(temp);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "You have entered invalid values!");
				dlgP.getTxtX().setText("");
				dlgP.getTxtY().setText("");
				dlgP.getPnlColor().setBackground(Color.BLACK);
			}
		} else if(current instanceof Line) {
			notifyAllObserversRedoUndo();
			DlgLine dlgL = new DlgLine();
			Line helper = (Line) current;
			dlgL.getTxtXStart().setText(Integer.toString(helper.getpBegin().getX()));
			dlgL.getTxtYStart().setText(Integer.toString(helper.getpBegin().getY()));
			dlgL.getTxtXEnd().setText(Integer.toString(helper.getpEnd().getX()));
			dlgL.getTxtYEnd().setText(Integer.toString(helper.getpEnd().getY()));
			dlgL.getPnlColor().setBackground(helper.getColor());
			
			try {
				dlgL.setVisible(true);
				
				int newXStart = Integer.parseInt(dlgL.getTxtXStart().getText());
				int newYStart = Integer.parseInt(dlgL.getTxtYStart().getText());
				int newXEnd = Integer.parseInt(dlgL.getTxtXEnd().getText());
				int newYEnd = Integer.parseInt(dlgL.getTxtYEnd().getText());
				Color color = dlgL.getPnlColor().getBackground();
				if(dlgL.isCheck()) {
				Line newLine = new Line(new Point(newXStart, newYStart), new Point(newXEnd, newYEnd), color);
				helper.setSelected(true);
				
				CmdUpdateShape temp = new CmdUpdateShape(helper, newLine, this);
				
				helpModify(temp);
				}
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "You have entered invalid values!");
				dlgL.getTxtXStart().setText("");
				dlgL.getTxtYStart().setText("");
				dlgL.getTxtXEnd().setText("");
				dlgL.getTxtYEnd().setText("");
				dlgL.getPnlColor().setBackground(Color.BLACK);
			}
			
		} else if(current instanceof Circle) {
			DlgCircle dlgC = new DlgCircle();
			Circle helper = (Circle) current;
			dlgC.getTxtX().setText(Integer.toString(helper.getCenter().getX()));
			dlgC.getTxtY().setText(Integer.toString(helper.getCenter().getY()));
			dlgC.getTxtR().setText(Integer.toString(helper.getR()));
			dlgC.getPnlColor().setBackground(helper.getColor());
			dlgC.getPnlAreaColor().setBackground(helper.getAreaColor());
			
			try {
				dlgC.setVisible(true);
				int newX = Integer.parseInt(dlgC.getTxtX().getText());
				int newY = Integer.parseInt(dlgC.getTxtY().getText());
				int newR = Integer.parseInt(dlgC.getTxtR().getText());
				Color color = dlgC.getPnlColor().getBackground();
				Color areaColor = dlgC.getPnlAreaColor().getBackground();
				if(dlgC.isCheck()) {
				
				Circle newCircle = new Circle(new Point(newX, newY), newR, color, areaColor);
				helper.setSelected(true);
				CmdUpdateShape temp = new CmdUpdateShape(helper, newCircle, this);
				
				helpModify(temp);
				}
			} catch(Exception ex){
				JOptionPane.showMessageDialog(null, ex);
				dlgC.getTxtX().setText("");
				dlgC.getTxtY().setText("");
				dlgC.getTxtR().setText("");
				dlgC.getPnlColor().setBackground(Color.BLACK);
				dlgC.getPnlAreaColor().setBackground(Color.WHITE);
			}
		} else if(current instanceof Square && !(current instanceof Rectangle)) {
			notifyAllObserversRedoUndo();
			DlgSquare dlgS = new DlgSquare();
			Square helper = (Square)current;
			dlgS.getTxtX().setText(Integer.toString(helper.getUpperLeft().getX()));
			dlgS.getTxtY().setText(Integer.toString(helper.getUpperLeft().getY()));
			dlgS.getTxtLength().setText(Integer.toString(helper.getN()));
			dlgS.getPnlColor().setBackground(helper.getColor());
			dlgS.getPnlAreaColor().setBackground(helper.getAreaColor());
			
			try {
				dlgS.setVisible(true);
				
				int newX = Integer.parseInt(dlgS.getTxtX().getText());
				int newY = Integer.parseInt(dlgS.getTxtY().getText());
				int newLength = Integer.parseInt(dlgS.getTxtLength().getText());
				Color color = dlgS.getPnlColor().getBackground();
				Color areaColor = dlgS.getPnlAreaColor().getBackground();
				if(dlgS.isCheck()) {
				Square newSquare = new Square(new Point(newX, newY), newLength, color, areaColor);
				helper.setSelected(true);
				
				CmdUpdateShape temp = new CmdUpdateShape(helper, newSquare, this);
				
				helpModify(temp);
				}
			} catch(Exception ex){
				JOptionPane.showMessageDialog(null, "You have entered invalid values!");
				dlgS.getTxtX().setText("");
				dlgS.getTxtY().setText("");
				dlgS.getTxtLength().setText("");
				dlgS.getPnlColor().setBackground(Color.WHITE);
				dlgS.getPnlAreaColor().setBackground(Color.WHITE);
			}
		} else if(current instanceof Rectangle) {
			notifyAllObserversRedoUndo();
			DlgRectangle dlgR = new DlgRectangle();
			Rectangle helper = (Rectangle)current;
			dlgR.getTxtX().setText(Integer.toString(helper.getUpperLeft().getX()));
			dlgR.getTxtY().setText(Integer.toString(helper.getUpperLeft().getY()));
			dlgR.getTxtLength().setText(Integer.toString(helper.getHeight()));
			dlgR.getTxtWidth().setText(Integer.toString(helper.getN()));
			dlgR.getPnlColor().setBackground(helper.getColor());
			dlgR.getPnlAreaColor().setBackground(helper.getAreaColor());
			
			try {
				dlgR.setVisible(true);
				
				int newX = Integer.parseInt(dlgR.getTxtX().getText());
				int newY = Integer.parseInt(dlgR.getTxtY().getText());
				int newLength = Integer.parseInt(dlgR.getTxtLength().getText());
				int newWidth = Integer.parseInt(dlgR.getTxtWidth().getText());
				Color color = dlgR.getPnlColor().getBackground();
				Color areaColor = dlgR.getPnlAreaColor().getBackground();
				if(dlgR.isCheck()) {
				Rectangle newRectangle = new Rectangle(new Point(newX, newY), newWidth, newLength, color, areaColor);
				helper.setSelected(true);
				
				CmdUpdateShape temp = new CmdUpdateShape(helper, newRectangle, this);
				
				helpModify(temp);
				}
			} catch(Exception ex){
				JOptionPane.showMessageDialog(null, "You have entered invalid values!");
				dlgR.getTxtX().setText("");
				dlgR.getTxtY().setText("");
				dlgR.getTxtWidth().setText("");
				dlgR.getTxtLength().setText("");
				dlgR.getPnlColor().setBackground(Color.BLACK);
				dlgR.getPnlAreaColor().setBackground(Color.WHITE);
			}
		} else if(current instanceof HexagonAdapter) {
			DlgHexagon dlgH = new DlgHexagon();
			HexagonAdapter helper = (HexagonAdapter) current;
			dlgH.getTxtX().setText(Integer.toString(helper.getX()));
			dlgH.getTxtY().setText(Integer.toString(helper.getY()));
			dlgH.getTxtR().setText(Integer.toString(helper.getR()));
			dlgH.getPnlColor().setBackground(helper.getColor());
			dlgH.getPnlAreaColor().setBackground(helper.getAreaColor());
			
			try {
				dlgH.setVisible(true);
				int newX = Integer.parseInt(dlgH.getTxtX().getText());
				int newY = Integer.parseInt(dlgH.getTxtY().getText());
				int newR = Integer.parseInt(dlgH.getTxtR().getText());
				Color color = dlgH.getPnlColor().getBackground();
				Color areaColor = dlgH.getPnlAreaColor().getBackground();
				if(dlgH.isCheck()) {
				helper.setSelected(true);
				HexagonAdapter newHex = new HexagonAdapter(new Hexagon(newX, newY, newR), color, areaColor);
				
				CmdUpdateShape temp = new CmdUpdateShape(helper, newHex, this);
				helpModify(temp);
				}
			} catch(Exception ex){
				JOptionPane.showMessageDialog(null, "You have entered invalid values!");
				dlgH.getTxtX().setText("");
				dlgH.getTxtY().setText("");
				dlgH.getTxtR().setText("");
				dlgH.getPnlColor().setBackground(Color.BLACK);
				dlgH.getPnlAreaColor().setBackground(Color.WHITE);
			}
		}
	}
	
	public void helpModify(CmdUpdateShape temp) {
		temp.execute();
		frame.setCountUndo(0);
		cmdList.add(temp);
		cmdList.setPosition(cmdList.getCommands().size() - 1);
		cmdList.newCommand(true);
		selectedShapes.remove(current);
		notifyAllObservers();
		notifyAllObserversRedoUndo();
		checkExecute();
		frame.getView().repaint();
	}
	
	public void delete() {
		notifyAllObserversRedoUndo();
		ArrayList<Shape> delete = new ArrayList<Shape>();
		for(int i = 0; i < model.getShapes().size(); i++) {
			if(model.getShapes().get(i).isSelected()) {
				delete.add(model.getShapes().get(i));
			}
		}
		Object[] choice = {"Yes", "No"};
		int choiceSet = JOptionPane.showOptionDialog(null, "Are you sure?", "Delete shape", JOptionPane.WARNING_MESSAGE,JOptionPane.PLAIN_MESSAGE, null, choice, null);
		if(choiceSet == JOptionPane.OK_OPTION) {
			for (Shape o : model.getShapes()) {
				if(o.isSelected()) {
					Shape temp1 = o;
					frame.setCountUndo(0);
					CmdRemoveShape temp = new CmdRemoveShape(model, temp1, this);
					cmdList.add(temp);
					cmdList.setPosition(cmdList.getCommands().size() - 1);
					cmdList.newCommand(true);
					selectedShapes.remove(current);
					notifyAllObserversRedoUndo();
					notifyAllObservers();
					checkExecute();
				}
			}
			model.getShapes().removeAll(delete);
			checkEnable();
			checkEnableButtonGroup();
			for(int i = 0; i < delete.size(); i++) {
				counter--;
			}
			for(Observer observer : observers) {
				observer.update(counter);
			}
			delete.clear();
			frame.getView().repaint();
		}
	}
	
	public void bringToFront() {
		for(int i = 0; i<model.getShapes().size(); i++) {
			if (model.getShapes().get(i).equals(current)) {
				position2 = i;
			}
		}
		Shape temp1 = current;
		CmdBringToFront temp = new CmdBringToFront(model, temp1, this, this.getPosition2());
		temp.execute();
		for(Observer observer : observers) {
			observer.update(counter);
		}
		cmdList.add(temp);
		checkExecute();
		cmdList.setPosition(cmdList.getCommands().size() - 1);
		cmdList.newCommand(true);
		notifyAllObserversRedoUndo();
	}
	
	public void toFront() {
		Shape temp1 = current;
		CmdToFront temp = new CmdToFront(model, temp1, this);
		temp.execute();
		checkExecute();
		for(Observer observer : observers) {
			observer.update(counter);
		}
		frame.setCountUndo(0);
		
		notifyAllObservers();
		cmdList.add(temp);
		cmdList.setPosition(cmdList.getCommands().size() - 1);
		cmdList.newCommand(true);
		notifyAllObserversRedoUndo();
	}

	public void bringToBack() {
		for(int i = 0 ; i < model.getShapes().size(); i++) {
			if(model.getShapes().get(i).equals(current)) {
				position = i;
			}
		}
		checkExecute();
		Shape temp1 = current;
		CmdBringToBack temp = new CmdBringToBack(model, temp1, this, this.getPosition());
		temp.execute();
		for(Observer observer : observers) {
			observer.update(counter);
		}
		frame.setCountUndo(0);
		cmdList.add(temp);
		cmdList.setPosition(cmdList.getCommands().size() - 1);
		cmdList.newCommand(true);
		notifyAllObserversRedoUndo();
	}

	public void toBack() {
		Shape temp1 = current;
		checkExecute();
		CmdToBack temp = new CmdToBack(model, temp1, this);
		temp.execute();
		
		for(Observer observer : observers) { 
			observer.update(counter);
		}
		frame.setCountUndo(0);
		
		cmdList.add(temp);
		cmdList.setPosition(cmdList.getCommands().size() - 1);
		cmdList.newCommand(true);
		notifyAllObserversRedoUndo();
	}
	
	public void openDrawing() {
		cmdList.setExecutedCommands("");
		frame.getTextArea().setText("");
		openCounter = 0;
		model.getShapes().clear();
		cmdList.getCommands().clear();
		cmdList.getUndo().clear();
		selectedShapes.remove(current);
		frame.setCountUndo(0);
		notifyAllObserversRedoUndo();
		ImportManager importer = new ImportManager(new ImportDrawing());
		String path = "";
		jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("binary files (*.bin)", "bin");
		jfc.setFileFilter(filter);
		jfc.setAcceptAllFileFilterUsed(false);
		
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			path = jfc.getSelectedFile().getAbsolutePath();
			
			ArrayList<Object> objects = (ArrayList<Object>)importer.importData(path);
			for (Shape s : (ArrayList<Shape>)objects.get(0)) {
				model.add(s);
				openCounter++;
			}
			frame.getBtnExecute().setEnabled(true);
		}
		frame.getView().repaint();
	}
	
	public void saveDrawing() {
		
		ArrayList<Object> objects = new ArrayList<Object>();
		objects.add(model.getShapes());
		
		manager = new ExportManager(new ExportSerializedShapes());
		saveLog = new ExportManager(new ExportLog());
		jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setAcceptAllFileFilterUsed(false);
		
		int returnValue = jfc.showSaveDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			String path = selectedFile.getAbsolutePath()+".bin";
			String logPath = selectedFile.getAbsolutePath()+".txt";
			
			if(path != null) {
				manager.exportData(objects, path);
				saveLog.exportData(cmdList.getExecutedCommands(), logPath);
			}
		}
	}
	
	public void importLog() throws IOException {
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("text files (*.txt)", "txt");
		fileChooser.setFileFilter(filter);
		
		if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			String filePath = fileChooser.getSelectedFile().getAbsolutePath();
			FileReader fileReader = (FileReader)importer.importData(filePath);
			System.out.println(fileReader);
			bufferLog = new BufferedReader(fileReader);
			this.model.getShapes().clear();
			cmdList.getCommands().clear();
			cmdList.setExecutedCommands("");
			frame.getTextArea().setText("");
			checkEnable();
			checkExecute();
		}
	}
	
	public boolean executeCommand(){
		if(bufferLog==null) 
			return false;
		String commandString="";
		try{
			if((commandString = bufferLog.readLine())!= null){
				System.out.println(commandString);
				String typeOfCommand = commandString.split(";")[0]; 
				String test = commandString.split(";")[1]; 
				System.out.println(typeOfCommand);
				System.out.println(test);
				if (!typeOfCommand.equals("[Undo]")) {
					Command cmd = CmdParser.createCmd(commandString, model,cmdList,this,selectedShapes);
					cmd.execute();
					cmdList.add(cmd);
					cmdList.setPosition(cmdList.getCommands().size() - 1);
					cmdList.newCommand(true);
					
				} else{
					Command cmd = CmdParser.createCmd(commandString, model,cmdList,this,selectedShapes);
					cmd.unexecute();
					cmdList.add(cmd);
					cmdList.setPosition(cmdList.getCommands().size() - 1);
					cmdList.newCommand(true);
					
				}
			} else {
				checkExecute();
				return false;
			}
			checkEnable();
			notifyAllObservers();
			notifyAllObserversRedoUndo();
			
			
		}catch (IOException ex){
			ex.printStackTrace();
		}
		return true;
	}
	
	public void checkExecute() {
		if (helper == model.getShapes().size()) {
			frame.getBtnExecute().setEnabled(false);
		}

	}
	
	public void initialCheck() {
		if (model.getShapes().size() == 0) {
			frame.getBtnRedo().setEnabled(false);
			frame.getBtnUndo().setEnabled(false);
			frame.getTglbtnDelete().setEnabled(false);
			frame.getTglbtnSelect().setEnabled(false);
			frame.getTglbtnModify().setEnabled(false);
			frame.getTglbtnBringToBack().setEnabled(false);
			frame.getTglbtnBringToFront().setEnabled(false);
			frame.getTglbtnToBack().setEnabled(false);
			frame.getTglbtnToFront().setEnabled(false);
		}
	}

	public void checkEnable() {
		if (helper == model.getShapes().size()-1) {
			frame.getTglbtnModify().setEnabled(false);
			frame.getTglbtnDelete().setEnabled(false);
			frame.getTglbtnBringToBack().setEnabled(false);
			frame.getTglbtnBringToFront().setEnabled(false);
			frame.getTglbtnToFront().setEnabled(false);
			frame.getTglbtnSelect().setEnabled(false);
			frame.getTglbtnToBack().setEnabled(false);
			frame.getBtnUndo().setEnabled(false);
			frame.getBtnRedo().setEnabled(false);
		}

	}
	
	public void checkEnableButtonGroup() {
		
		if (model.getShapes().size() > 0) {
			frame.getTglbtnModify().setEnabled(false);
			frame.getTglbtnDelete().setEnabled(false);
			frame.getTglbtnBringToBack().setEnabled(false);
			frame.getTglbtnBringToFront().setEnabled(false);
			frame.getTglbtnToFront().setEnabled(false);
			frame.getTglbtnSelect().setEnabled(true);
			frame.getTglbtnToBack().setEnabled(false);
			frame.getBtnUndo().setEnabled(true);
			//frame.getBtnRedo().setEnabled(true);
		} else {
			frame.getTglbtnModify().setEnabled(false);
			frame.getTglbtnDelete().setEnabled(false);
			frame.getTglbtnBringToBack().setEnabled(false);
			frame.getTglbtnBringToFront().setEnabled(false);
			frame.getTglbtnToFront().setEnabled(false);
			frame.getTglbtnSelect().setEnabled(false);
			frame.getTglbtnToBack().setEnabled(false);
			//frame.getBtnUndo().setEnabled(false);
			//frame.getBtnRedo().setEnabled(false);
		}
	}
	
	public void undoAddShape() {
		cmdList.undo();
	}
	
	public void redoAddShape() {
		cmdList.redo();
	}
	
	public void undoRemoveShape() {
		cmdList.undo();
	}
	
	public void redoRemoveShape() {
		cmdList.redo();
	}
	
	public void undoSelectShape() {
		cmdList.undo();
	}
	
	public void redoSelectShape() {
		cmdList.redo();
	}
	
	public void undoDeselectShape() {
		cmdList.undo();
	}
	
	public void redoDeselectShape() {
		cmdList.redo();
	}
	
	public void undoUpdateShape() {
		cmdList.undo();
	}
	
	public void redoUpdateShape() {
		cmdList.redo();
	}
	
	public void undoToFrontShape() {
		cmdList.undo();
	}
	
	public void redoToFrontShape() {
		cmdList.redo();
	}
	
	public void undoToBackShape() {
		cmdList.undo();
	}
	
	public void redoToBackShape() {
		cmdList.redo();
	}
	
	public void undoBringToFrontShape() {
		cmdList.undo();
	}
	
	public void redoBringToFrontShape() {
		cmdList.redo();
	}
	
	public void undoBringToBackShape() {
		cmdList.undo();
	}
	
	public void redoBringToBackShape() {
		cmdList.redo();
	}
	
	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		observers.add(observer);
		
	}

	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		observers.remove(observer);
		
	}

	@Override
	public void notifyAllObservers() {
		// TODO Auto-generated method stub
		counter = 0;
		for(int i = 0; i < model.getShapes().size(); i++) {
			if(model.getShapes().get(i).isSelected())
				counter++;
		}
		for(Observer observer : observers)
			observer.update(counter);
	}

	@Override
	public void addObserverRedoUndo(Observer observer) {
		// TODO Auto-generated method stub
		observersUndoRedo.add(observer);
	}

	@Override
	public void removeObserverRedoUndo(Observer observer) {
		// TODO Auto-generated method stub
		observersUndoRedo.remove(observer);
	}

	@Override
	public void notifyAllObserversRedoUndo() {
		// TODO Auto-generated method stub
		int switcher = 0;
		
		if(model.getShapes().size() == 0 && cmdList.getCommands().size() > 0)
			switcher = 0;
		else if (model.getShapes().size() == 0)
			switcher = 2;
		else if (model.getShapes().size() > 0 && model.getShapes().size() == openCounter && cmdList.getCommands().size() == 1)
			switcher = 3;
		else if (model.getShapes().size() > 0 && model.getShapes().size() == openCounter && cmdList.getCommands().size() - cmdList.getUndo().size() > 1)
			switcher = 4;
		else if (model.getShapes().size() > 0 && model.getShapes().size() == openCounter && 
				cmdList.getCommands().size() - cmdList.getUndo().size() > 1 && cmdList.getUndo().size() == 0)
			switcher = 5;
		else if (model.getShapes().size() > 0)
			switcher = 1;
		
		for(Observer observer : observers) 
			observer.updateRedoUndo(switcher);
		System.out.println("Switcher " + switcher);
	}

	public Shape getCurrent() {
		return current;
	}

	public void setCurrent(Shape current) {
		this.current = current;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition2() {
		return position2;
	}

	public void setPosition2(int position2) {
		this.position2 = position2;
	}

	public Point getFirstPointLine() {
		return firstPointLine;
	}

	public void setFirstPointLine(Point firstPointLine) {
		this.firstPointLine = firstPointLine;
	}

	public DrawingModel getModel() {
		return model;
	}

	public void setModel(DrawingModel model) {
		this.model = model;
	}

	public int getHelper() {
		return helper;
	}

	public void setHelper(int helper) {
		this.helper = helper;
	}

}
