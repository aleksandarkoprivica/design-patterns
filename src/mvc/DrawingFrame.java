package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import commands.CommandList;
import observer.Observer;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.GridBagLayout;
import javax.swing.JToggleButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.swing.JToolBar;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.UIManager;
import javax.swing.SwingConstants;

public class DrawingFrame extends JFrame implements Observer {

	private JPanel contentPane;
	private JPanel pnlShapes = new JPanel();
	private JPanel pnlColor = new JPanel();
	private JPanel pnlEdgeColor = new JPanel();
	private JPanel pnlAreaColor = new JPanel();
	private JPanel pnlLog = new JPanel();

	private ButtonGroup buttonGroup = new ButtonGroup();

	private JToggleButton tglbtnSelect = new JToggleButton("Select");
	private JToggleButton tglbtnModify = new JToggleButton("Modify");
	private JToggleButton tglbtnDelete = new JToggleButton("Delete");
	private JToolBar toolBar = new JToolBar();

	private JScrollPane scrollPane;
	private JTextArea textArea = new JTextArea();

	private Color edgeColor;
	private Color areaColor;
	private int countUndo = 0;

	private DrawingView view;
	private DrawingController controller;
	private CommandList cmdList = new CommandList();
	private JLabel lblLogger = new JLabel("Commands:");
	private JToggleButton tglbtnToBack = new JToggleButton("To Back");
	private JToggleButton tglbtnToFront = new JToggleButton("To Front");
	private JToggleButton tglbtnBringToBack = new JToggleButton("Bring To Back");
	private JToggleButton tglbtnBringToFront = new JToggleButton("Bring To Front");
	private JLabel lblCommands = new JLabel("Commands:");
	private JToggleButton tglbtnHexagon = new JToggleButton("Hexagon");
	private JToggleButton tglbtnRectangle = new JToggleButton("Rectangle");
	private JToggleButton tglbtnSquare = new JToggleButton("Square");
	private JToggleButton tglbtnCircle = new JToggleButton("Circle");
	private JToggleButton tglbtnLine = new JToggleButton("Line");
	private JToggleButton tglbtnPoint = new JToggleButton("Point");
	private JLabel lblShapes = new JLabel("Shapes:");
	private JButton btnUndo = new JButton("Undo");
	private JButton btnRedo = new JButton("Redo");
	private JButton btnImportLog = new JButton("Import Log");
	private JButton btnExecute = new JButton("Execute");
	private final JLabel lblNewLabel = new JLabel("Logger:");
	private final JLabel lbl_cursor = new JLabel("Cursor coordinates: X, Y");

	/**
	 * Create the frame.
	 */
	public DrawingFrame() {

		setTitle("Aleksandar Koprivica - Dizajnerski obrasci");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 750);
		pnlEdgeColor.setBackground(Color.BLACK);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.openDrawing();
			}
		});
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.saveDrawing();
			}
		});
		mnFile.add(mntmSave);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		contentPane.add(pnlShapes, BorderLayout.WEST);
		GridBagLayout gbl_pnlShapes = new GridBagLayout();
		gbl_pnlShapes.columnWidths = new int[] { 0, 933, 119, 0 };
		gbl_pnlShapes.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 33, 0, 0 };
		gbl_pnlShapes.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_pnlShapes.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		pnlShapes.setLayout(gbl_pnlShapes);

		GridBagConstraints gbc_lblShapes = new GridBagConstraints();
		gbc_lblShapes.insets = new Insets(0, 0, 5, 5);
		gbc_lblShapes.gridx = 0;
		gbc_lblShapes.gridy = 0;
		pnlShapes.add(lblShapes, gbc_lblShapes);

		view = new DrawingView();
		GridBagConstraints gbc_view = new GridBagConstraints();
		gbc_view.insets = new Insets(0, 0, 0, 5);
		gbc_view.gridheight = 14;
		gbc_view.fill = GridBagConstraints.BOTH;
		gbc_view.gridx = 1;
		gbc_view.gridy = 0;
		pnlShapes.add(view, gbc_view);
		view.setBackground(Color.WHITE);

		view.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.paint(e);
				btnExecute.setEnabled(false);
			}
		});

		GridBagConstraints gbc_lblLogger = new GridBagConstraints();
		gbc_lblLogger.insets = new Insets(0, 0, 5, 0);
		gbc_lblLogger.gridx = 2;
		gbc_lblLogger.gridy = 0;
		pnlShapes.add(lblLogger, gbc_lblLogger);

		GridBagConstraints gbc_tglbtnPoint = new GridBagConstraints();
		gbc_tglbtnPoint.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnPoint.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnPoint.gridx = 0;
		gbc_tglbtnPoint.gridy = 1;
		pnlShapes.add(tglbtnPoint, gbc_tglbtnPoint);
		buttonGroup.add(tglbtnPoint);

		GridBagConstraints gbc_tglbtnBringToFront = new GridBagConstraints();
		gbc_tglbtnBringToFront.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnBringToFront.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnBringToFront.gridx = 2;
		gbc_tglbtnBringToFront.gridy = 1;
		pnlShapes.add(tglbtnBringToFront, gbc_tglbtnBringToFront);
		buttonGroup.add(tglbtnBringToFront);
		tglbtnBringToFront.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				controller.bringToFront();
			}
		});

		GridBagConstraints gbc_tglbtnLine = new GridBagConstraints();
		gbc_tglbtnLine.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnLine.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnLine.gridx = 0;
		gbc_tglbtnLine.gridy = 2;
		pnlShapes.add(tglbtnLine, gbc_tglbtnLine);
		buttonGroup.add(tglbtnLine);

		GridBagConstraints gbc_tglbtnBringToBack = new GridBagConstraints();
		gbc_tglbtnBringToBack.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnBringToBack.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnBringToBack.gridx = 2;
		gbc_tglbtnBringToBack.gridy = 2;
		pnlShapes.add(tglbtnBringToBack, gbc_tglbtnBringToBack);
		buttonGroup.add(tglbtnBringToBack);
		tglbtnBringToBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				controller.bringToBack();

			}
		});

		GridBagConstraints gbc_tglbtnCircle = new GridBagConstraints();
		gbc_tglbtnCircle.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnCircle.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnCircle.gridx = 0;
		gbc_tglbtnCircle.gridy = 3;
		pnlShapes.add(tglbtnCircle, gbc_tglbtnCircle);
		buttonGroup.add(tglbtnCircle);

		GridBagConstraints gbc_tglbtnToFront = new GridBagConstraints();
		gbc_tglbtnToFront.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnToFront.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnToFront.gridx = 2;
		gbc_tglbtnToFront.gridy = 3;
		pnlShapes.add(tglbtnToFront, gbc_tglbtnToFront);
		buttonGroup.add(tglbtnToFront);
		tglbtnToFront.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				controller.toFront();
			}
		});

		GridBagConstraints gbc_tglbtnSquare = new GridBagConstraints();
		gbc_tglbtnSquare.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnSquare.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnSquare.gridx = 0;
		gbc_tglbtnSquare.gridy = 4;
		pnlShapes.add(tglbtnSquare, gbc_tglbtnSquare);
		buttonGroup.add(tglbtnSquare);

		GridBagConstraints gbc_tglbtnToBack = new GridBagConstraints();
		gbc_tglbtnToBack.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnToBack.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnToBack.gridx = 2;
		gbc_tglbtnToBack.gridy = 4;
		pnlShapes.add(tglbtnToBack, gbc_tglbtnToBack);
		buttonGroup.add(tglbtnToBack);
		tglbtnToBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				controller.toBack();
			}
		});

		GridBagConstraints gbc_tglbtnRectangle = new GridBagConstraints();
		gbc_tglbtnRectangle.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnRectangle.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnRectangle.gridx = 0;
		gbc_tglbtnRectangle.gridy = 5;
		pnlShapes.add(tglbtnRectangle, gbc_tglbtnRectangle);
		buttonGroup.add(tglbtnRectangle);

		GridBagConstraints gbc_tglbtnHexagon = new GridBagConstraints();
		gbc_tglbtnHexagon.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnHexagon.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnHexagon.gridx = 0;
		gbc_tglbtnHexagon.gridy = 6;
		pnlShapes.add(tglbtnHexagon, gbc_tglbtnHexagon);
		buttonGroup.add(tglbtnHexagon);

		GridBagConstraints gbc_btnUndo = new GridBagConstraints();
		gbc_btnUndo.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUndo.insets = new Insets(0, 0, 5, 0);
		gbc_btnUndo.gridx = 2;
		gbc_btnUndo.gridy = 6;
		pnlShapes.add(btnUndo, gbc_btnUndo);
		btnUndo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (tglbtnDelete.isSelected()) {
					countUndo++;
					controller.undoRemoveShape();
					System.out.println(countUndo);
				} else if (tglbtnSelect.isSelected()) {
					countUndo++;
					controller.undoSelectShape();
					System.out.println(countUndo);
				} else if (tglbtnModify.isSelected()) {
					countUndo++;
					controller.undoUpdateShape();
					System.out.println(countUndo);
				} else if (tglbtnToFront.isSelected()) {
					countUndo++;
					controller.undoToFrontShape();
					System.out.println(countUndo);
				} else if (tglbtnToBack.isSelected()) {
					countUndo++;
					controller.undoToBackShape();
					
					System.out.println(countUndo);
				} else if (tglbtnBringToFront.isSelected()) {
					countUndo++;
					controller.undoBringToFrontShape();
					System.out.println(countUndo);
				} else if (tglbtnBringToBack.isSelected()) {
					countUndo++;
					controller.undoBringToBackShape();
					System.out.println(countUndo);
				} else {
					countUndo++;
					controller.undoAddShape();
					System.out.println(countUndo);
				}
				btnRedo.setEnabled(true);

			}
		});

		GridBagConstraints gbc_btnRedo = new GridBagConstraints();
		gbc_btnRedo.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRedo.insets = new Insets(0, 0, 5, 0);
		gbc_btnRedo.gridx = 2;
		gbc_btnRedo.gridy = 7;
		pnlShapes.add(btnRedo, gbc_btnRedo);
		btnRedo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (tglbtnDelete.isSelected()) {
					countUndo--;
					controller.redoRemoveShape();
					System.out.println(countUndo);
				} else if (tglbtnSelect.isSelected()) {
					countUndo--;
					controller.redoSelectShape();
					System.out.println(countUndo);
				} else if (tglbtnModify.isSelected()) {
					countUndo--;
					controller.redoUpdateShape();
					System.out.println(countUndo);

				} else if (tglbtnToFront.isSelected()) {
					countUndo--;
					controller.redoToFrontShape();
					System.out.println(countUndo);
				} else if (tglbtnToBack.isSelected()) {
					countUndo--;
					controller.redoToBackShape();
					System.out.println(countUndo);
				} else if (tglbtnBringToFront.isSelected()) {
					countUndo--;
					controller.redoBringToFrontShape();
					System.out.println(countUndo);
				} else if (tglbtnBringToBack.isSelected()) {
					countUndo--;
					controller.redoBringToBackShape();
					System.out.println(countUndo);
				} else {
					countUndo--;
					controller.redoAddShape();
					System.out.println(countUndo);
				}
				if(countUndo==0) {
					btnRedo.setEnabled(false);
				}
			}
		});

		GridBagConstraints gbc_btnImportLog = new GridBagConstraints();
		gbc_btnImportLog.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnImportLog.insets = new Insets(0, 0, 5, 0);
		gbc_btnImportLog.gridx = 2;
		gbc_btnImportLog.gridy = 9;
		pnlShapes.add(btnImportLog, gbc_btnImportLog);
		btnExecute.setEnabled(false);
		btnImportLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					controller.importLog();
					btnExecute.setEnabled(true);
				} catch (IOException exception) {
					exception.printStackTrace();
				}

			}
		});

		GridBagConstraints gbc_btnExecute = new GridBagConstraints();
		gbc_btnExecute.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExecute.insets = new Insets(0, 0, 5, 0);
		gbc_btnExecute.gridx = 2;
		gbc_btnExecute.gridy = 10;
		pnlShapes.add(btnExecute, gbc_btnExecute);
		btnExecute.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!controller.executeCommand()) {
					btnExecute.setEnabled(false);
				}
			}
		});

		GridBagConstraints gbc_pnlColor = new GridBagConstraints();
		gbc_pnlColor.insets = new Insets(0, 0, 5, 5);
		gbc_pnlColor.fill = GridBagConstraints.BOTH;
		gbc_pnlColor.gridx = 0;
		gbc_pnlColor.gridy = 12;
		pnlShapes.add(pnlColor, gbc_pnlColor);
		GridBagLayout gbl_pnlColor = new GridBagLayout();
		gbl_pnlColor.columnWidths = new int[] { 0, 0 };
		gbl_pnlColor.rowHeights = new int[] { 0, 30, 0, 25, 0 };
		gbl_pnlColor.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_pnlColor.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlColor.setLayout(gbl_pnlColor);

		JLabel lblEdgeColor = new JLabel("Edge color:");
		GridBagConstraints gbc_lblEdgeColor = new GridBagConstraints();
		gbc_lblEdgeColor.anchor = GridBagConstraints.WEST;
		gbc_lblEdgeColor.insets = new Insets(0, 0, 5, 0);
		gbc_lblEdgeColor.gridx = 0;
		gbc_lblEdgeColor.gridy = 0;
		pnlColor.add(lblEdgeColor, gbc_lblEdgeColor);

		pnlEdgeColor.setBackground(SystemColor.activeCaption);
		GridBagConstraints gbc_pnlEdgeColor = new GridBagConstraints();
		gbc_pnlEdgeColor.fill = GridBagConstraints.BOTH;
		gbc_pnlEdgeColor.insets = new Insets(0, 0, 5, 0);
		gbc_pnlEdgeColor.gridx = 0;
		gbc_pnlEdgeColor.gridy = 1;
		pnlColor.add(pnlEdgeColor, gbc_pnlEdgeColor);
		pnlEdgeColor.setBackground(Color.BLACK);
		pnlEdgeColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				edgeColor = JColorChooser.showDialog(null, "Choose edge color:", pnlEdgeColor.getBackground());
				pnlEdgeColor.setBackground(edgeColor);
			}
		});

		JLabel lblAreaColor = new JLabel("Area color:");
		GridBagConstraints gbc_lblAreaColor = new GridBagConstraints();
		gbc_lblAreaColor.anchor = GridBagConstraints.WEST;
		gbc_lblAreaColor.insets = new Insets(0, 0, 5, 0);
		gbc_lblAreaColor.gridx = 0;
		gbc_lblAreaColor.gridy = 2;
		pnlColor.add(lblAreaColor, gbc_lblAreaColor);

		pnlAreaColor.setBackground(SystemColor.activeCaption);
		GridBagConstraints gbc_pnlAreaColor = new GridBagConstraints();
		gbc_pnlAreaColor.fill = GridBagConstraints.BOTH;
		gbc_pnlAreaColor.gridx = 0;
		gbc_pnlAreaColor.gridy = 3;
		pnlColor.add(pnlAreaColor, gbc_pnlAreaColor);
		pnlAreaColor.setBackground(SystemColor.activeCaption);
		pnlAreaColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				areaColor = JColorChooser.showDialog(null, "Choose area color:", pnlAreaColor.getBackground());
				pnlAreaColor.setBackground(areaColor);
			}
		});

		GridBagConstraints gbc_label_3_1 = new GridBagConstraints();
		gbc_label_3_1.insets = new Insets(0, 0, 0, 5);
		gbc_label_3_1.gridx = 0;
		gbc_label_3_1.gridy = 13;
		lblLogger.setHorizontalAlignment(SwingConstants.LEFT);
		
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.BASELINE;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 13;
		pnlShapes.add(lblNewLabel, gbc_lblNewLabel);
		pnlAreaColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				areaColor = JColorChooser.showDialog(null, "Choose area color:", pnlAreaColor.getBackground());
				pnlAreaColor.setBackground(areaColor);
			}
		});

		contentPane.add(toolBar, BorderLayout.NORTH);

		JLabel label_3 = new JLabel("Options:");
		toolBar.add(label_3);

		toolBar.add(tglbtnSelect);
		buttonGroup.add(tglbtnSelect);

		toolBar.add(tglbtnModify);
		buttonGroup.add(tglbtnModify);
		tglbtnModify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				controller.modify();
			}
		});

		toolBar.add(tglbtnDelete);
		buttonGroup.add(tglbtnDelete);
		tglbtnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				controller.delete();
			}
		});

		contentPane.add(pnlLog, BorderLayout.SOUTH);
		GridBagLayout gbl_pnlLog = new GridBagLayout();
		gbl_pnlLog.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_pnlLog.rowHeights = new int[] { 80, 10, 0 };
		gbl_pnlLog.columnWeights = new double[] { 1.0, 4.9E-324, 1.0, Double.MIN_VALUE };
		gbl_pnlLog.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		pnlLog.setLayout(gbl_pnlLog);

		textArea = new JTextArea(5, 107);
		textArea.setForeground(UIManager.getColor("InternalFrame.borderDarkShadow"));
		textArea.setBackground(UIManager.getColor("ComboBox.foreground"));
		textArea.setLineWrap(true);

		scrollPane = new JScrollPane(textArea);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		pnlLog.add(scrollPane, gbc_scrollPane);
		
		GridBagConstraints gbc_lbl_cursor = new GridBagConstraints();
		gbc_lbl_cursor.anchor = GridBagConstraints.EAST;
		gbc_lbl_cursor.gridx = 2;
		gbc_lbl_cursor.gridy = 1;
		pnlLog.add(lbl_cursor, gbc_lbl_cursor);
		view.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				lbl_cursor.setText("Coordinates: (" + arg0.getX() + "," + arg0.getY()+")");
			}
		});

	}

	public JPanel getPnlShapes() {
		return pnlShapes;
	}

	public void setPnlShapes(JPanel pnlShapes) {
		this.pnlShapes = pnlShapes;
	}

	public JPanel getPnlColor() {
		return pnlColor;
	}

	public void setPnlColor(JPanel pnlColor) {
		this.pnlColor = pnlColor;
	}

	public JPanel getPnlEdgeColor() {
		return pnlEdgeColor;
	}

	public void setPnlEdgeColor(JPanel pnlEdgeColor) {
		this.pnlEdgeColor = pnlEdgeColor;
	}

	public JPanel getPnlAreaColor() {
		return pnlAreaColor;
	}

	public void setPnlAreaColor(JPanel pnlAreaColor) {
		this.pnlAreaColor = pnlAreaColor;
	}

	public JPanel getPnlLog() {
		return pnlLog;
	}

	public void setPnlLog(JPanel pnlLog) {
		this.pnlLog = pnlLog;
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public void setTglbtnPoint(JToggleButton tglbtnPoint) {
		this.tglbtnPoint = tglbtnPoint;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}

	public void setTglbtnLine(JToggleButton tglbtnLine) {
		this.tglbtnLine = tglbtnLine;
	}

	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	public void setTglbtnCircle(JToggleButton tglbtnCircle) {
		this.tglbtnCircle = tglbtnCircle;
	}

	public JToggleButton getTglbtnSquare() {
		return tglbtnSquare;
	}

	public void setTglbtnSquare(JToggleButton tglbtnSquare) {
		this.tglbtnSquare = tglbtnSquare;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}

	public void setTglbtnRectangle(JToggleButton tglbtnRectangle) {
		this.tglbtnRectangle = tglbtnRectangle;
	}

	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}

	public void setTglbtnHexagon(JToggleButton tglbtnHexagon) {
		this.tglbtnHexagon = tglbtnHexagon;
	}

	public JToggleButton getTglbtnBringToFront() {
		return tglbtnBringToFront;
	}

	public void setTglbtnBringToFront(JToggleButton tglbtnBringToFront) {
		this.tglbtnBringToFront = tglbtnBringToFront;
	}

	public JToggleButton getTglbtnBringToBack() {
		return tglbtnBringToBack;
	}

	public void setTglbtnBringToBack(JToggleButton tglbtnBringToBack) {
		this.tglbtnBringToBack = tglbtnBringToBack;
	}

	public JToggleButton getTglbtnToFront() {
		return tglbtnToFront;
	}

	public void setTglbtnToFront(JToggleButton tglbtnToFront) {
		this.tglbtnToFront = tglbtnToFront;
	}

	public JToggleButton getTglbtnToBack() {
		return tglbtnToBack;
	}

	public void setTglbtnToBack(JToggleButton tglbtnToBack) {
		this.tglbtnToBack = tglbtnToBack;
	}

	public JToggleButton getTglbtnSelect() {
		return tglbtnSelect;
	}

	public void setTglbtnSelect(JToggleButton tglbtnSelect) {
		this.tglbtnSelect = tglbtnSelect;
	}

	public JToggleButton getTglbtnModify() {
		return tglbtnModify;
	}

	public void setTglbtnModify(JToggleButton tglbtnModify) {
		this.tglbtnModify = tglbtnModify;
	}

	public JToggleButton getTglbtnDelete() {
		return tglbtnDelete;
	}

	public void setTglbtnDelete(JToggleButton tglbtnDelete) {
		this.tglbtnDelete = tglbtnDelete;
	}

	public JToolBar getToolBar() {
		return toolBar;
	}

	public void setToolBar(JToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public JButton getBtnImportLog() {
		return btnImportLog;
	}

	public void setBtnImportLog(JButton btnImportLog) {
		this.btnImportLog = btnImportLog;
	}

	public JButton getBtnExecute() {
		return btnExecute;
	}

	public void setBtnExecute(JButton btnExecute) {
		this.btnExecute = btnExecute;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public void setBtnUndo(JButton btnUndo) {
		this.btnUndo = btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public void setBtnRedo(JButton btnRedo) {
		this.btnRedo = btnRedo;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public Color getEdgeColor() {
		return edgeColor;
	}

	public void setEdgeColor(Color edgeColor) {
		this.edgeColor = edgeColor;
	}

	public Color getAreaColor() {
		return areaColor;
	}

	public void setAreaColor(Color areaColor) {
		this.areaColor = areaColor;
	}

	public int getCountUndo() {
		return countUndo;
	}

	public void setCountUndo(int countUndo) {
		this.countUndo = countUndo;
	}

	public JLabel getLblCommands() {
		return lblCommands;
	}

	public void setLblCommands(JLabel lblCommands) {
		this.lblCommands = lblCommands;
	}

	public DrawingView getView() {
		return view;
	}

	public void setView(DrawingView view) {
		this.view = view;
	}

	public DrawingController getController() {
		return controller;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	public CommandList getCmdList() {
		return cmdList;
	}

	public void setCmdList(CommandList cmdList) {
		this.cmdList = cmdList;
	}

	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}

	@Override
	public void update(int counter) {
		// TODO Auto-generated method stub
		if (counter == 1) {
			tglbtnModify.setEnabled(true);
			tglbtnDelete.setEnabled(true);

			for (int i = 0; i < controller.getModel().getShapes().size(); i++) {
				if (controller.getModel().getShapes().get(i).isSelected()) {
					if (i != controller.getModel().getShapes().size() - 1 && i != 0) {

						tglbtnBringToFront.setEnabled(true);
						tglbtnBringToBack.setEnabled(true);
						tglbtnToFront.setEnabled(true);
						tglbtnToBack.setEnabled(true);
					} else if (i != 0 && i == controller.getModel().getShapes().size() - 1) {
						tglbtnBringToFront.setEnabled(false);
						tglbtnBringToBack.setEnabled(true);
						tglbtnToBack.setEnabled(true);
						tglbtnToFront.setEnabled(false);
					} else if (i == 0 && i != controller.getModel().getShapes().size() - 1) {
						tglbtnBringToFront.setEnabled(true);
						tglbtnBringToBack.setEnabled(false);
						tglbtnToFront.setEnabled(true);
						tglbtnToBack.setEnabled(false);
					}
				}
			}

		} else if (counter > 1) {
			tglbtnDelete.setEnabled(true);
			tglbtnModify.setEnabled(false);
			tglbtnBringToBack.setEnabled(false);
			tglbtnBringToFront.setEnabled(false);
			tglbtnToBack.setEnabled(false);
			tglbtnToFront.setEnabled(false);
		} else {

			tglbtnModify.setEnabled(false);
			tglbtnDelete.setEnabled(false);
			tglbtnBringToBack.setEnabled(false);
			tglbtnBringToFront.setEnabled(false);
			tglbtnToBack.setEnabled(false);
			tglbtnToFront.setEnabled(false);

		}

	}

	@Override
	public void updateRedoUndo(int n) {
		// TODO Auto-generated method stub
		if (n == 2 && countUndo == 0) {
			btnUndo.setEnabled(false);
			btnRedo.setEnabled(false);
			System.out.println("RedoUndo 0");
		} else if (n == 1 && countUndo == 0) {
			btnUndo.setEnabled(true);
			btnRedo.setEnabled(false);
			System.out.println("RedoUndo 1");
		} else if (n == 1 && countUndo > 0) {
			btnUndo.setEnabled(true);
			btnRedo.setEnabled(true);
			System.out.println("RedoUndo 2");
		} else if (n == 0 && countUndo > 0) {
			btnUndo.setEnabled(false);
			btnRedo.setEnabled(true);
			System.out.println("RedoUndo 3");
		} else if (n == 3 && countUndo == 0) {
			btnUndo.setEnabled(true);
			btnRedo.setEnabled(false);
			System.out.println("RedoUndo 4");
		} else if (n == 4 && countUndo > 0) {
			btnUndo.setEnabled(true);
			btnRedo.setEnabled(true);
			System.out.println("RedoUndo 5");
		} else if (n == 5 && countUndo == 0) {
			btnUndo.setEnabled(false);
			btnRedo.setEnabled(true);
			System.out.println("RedoUndo 6");
		}
		System.out.println("CountUndo" + countUndo);

	}

}
