package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JTextField;

public class DlgCircle extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtR;
	private JPanel pnlColor;
	private JPanel pnlAreaColor;
	private boolean check;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgCircle dialog = new DlgCircle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgCircle() {
		setModal(true);
		setLocation(800,600);
		setResizable(false);
		setTitle("Circle");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblXCoordinateUpper = new JLabel("X coordinate upper left:");
			GridBagConstraints gbc_lblXCoordinateUpper = new GridBagConstraints();
			gbc_lblXCoordinateUpper.insets = new Insets(0, 0, 5, 5);
			gbc_lblXCoordinateUpper.gridx = 0;
			gbc_lblXCoordinateUpper.gridy = 0;
			contentPanel.add(lblXCoordinateUpper, gbc_lblXCoordinateUpper);
		}
		{
			txtX = new JTextField();
			GridBagConstraints gbc_txtX = new GridBagConstraints();
			gbc_txtX.insets = new Insets(0, 0, 5, 0);
			gbc_txtX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtX.gridx = 3;
			gbc_txtX.gridy = 0;
			contentPanel.add(txtX, gbc_txtX);
			txtX.setColumns(10);
		}
		{
			JLabel lblYCoordinateUpper = new JLabel("Y coordinate upper left:");
			GridBagConstraints gbc_lblYCoordinateUpper = new GridBagConstraints();
			gbc_lblYCoordinateUpper.insets = new Insets(0, 0, 5, 5);
			gbc_lblYCoordinateUpper.gridx = 0;
			gbc_lblYCoordinateUpper.gridy = 1;
			contentPanel.add(lblYCoordinateUpper, gbc_lblYCoordinateUpper);
		}
		{
			txtY = new JTextField();
			GridBagConstraints gbc_txtY = new GridBagConstraints();
			gbc_txtY.anchor = GridBagConstraints.NORTH;
			gbc_txtY.insets = new Insets(0, 0, 5, 0);
			gbc_txtY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtY.gridx = 3;
			gbc_txtY.gridy = 1;
			contentPanel.add(txtY, gbc_txtY);
			txtY.setColumns(10);
		}
		{
			JLabel lblRadius = new JLabel("Radius:");
			GridBagConstraints gbc_lblRadius = new GridBagConstraints();
			gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
			gbc_lblRadius.anchor = GridBagConstraints.WEST;
			gbc_lblRadius.gridx = 0;
			gbc_lblRadius.gridy = 2;
			contentPanel.add(lblRadius, gbc_lblRadius);
		}
		{
			txtR = new JTextField();
			GridBagConstraints gbc_txtR = new GridBagConstraints();
			gbc_txtR.insets = new Insets(0, 0, 5, 0);
			gbc_txtR.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtR.gridx = 3;
			gbc_txtR.gridy = 2;
			contentPanel.add(txtR, gbc_txtR);
			txtR.setColumns(10);
		}
		{
			JLabel lblBorderColor = new JLabel("Border color:");
			GridBagConstraints gbc_lblBorderColor = new GridBagConstraints();
			gbc_lblBorderColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblBorderColor.anchor = GridBagConstraints.WEST;
			gbc_lblBorderColor.gridx = 0;
			gbc_lblBorderColor.gridy = 3;
			contentPanel.add(lblBorderColor, gbc_lblBorderColor);
		}
		pnlColor = new JPanel();
		pnlColor.setBackground(Color.BLACK);
		pnlColor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Color color = JColorChooser.showDialog(null,"Choose border color:", pnlColor.getBackground());
				if(color != null)
				pnlColor.setBackground(color);
			}
		});
		GridBagConstraints gbc_pnlColor = new GridBagConstraints();
		gbc_pnlColor.insets = new Insets(0, 0, 5, 0);
		gbc_pnlColor.fill = GridBagConstraints.BOTH;
		gbc_pnlColor.gridx = 3;
		gbc_pnlColor.gridy = 3;
		contentPanel.add(pnlColor, gbc_pnlColor);
		{
			JLabel lblAreaColor = new JLabel("Area color:");
			GridBagConstraints gbc_lblAreaColor = new GridBagConstraints();
			gbc_lblAreaColor.insets = new Insets(0, 0, 0, 5);
			gbc_lblAreaColor.anchor = GridBagConstraints.WEST;
			gbc_lblAreaColor.gridx = 0;
			gbc_lblAreaColor.gridy = 5;
			contentPanel.add(lblAreaColor, gbc_lblAreaColor);
		}
		pnlAreaColor = new JPanel();
		pnlAreaColor.setBackground(Color.WHITE);
		pnlAreaColor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Color areaColor = JColorChooser.showDialog(null, "Choose area color:", pnlAreaColor.getBackground());
				if(areaColor != null)
				pnlAreaColor.setBackground(areaColor);
			}
		});
		GridBagConstraints gbc_pnlAreaColor = new GridBagConstraints();
		gbc_pnlAreaColor.fill = GridBagConstraints.BOTH;
		gbc_pnlAreaColor.gridx = 3;
		gbc_pnlAreaColor.gridy = 5;
		contentPanel.add(pnlAreaColor, gbc_pnlAreaColor);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							check = true;
							setVisible(false);
						}catch(Exception exception) {
							JOptionPane.showMessageDialog(null, "Parameters not valid!");
						}
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				txtX.setText("");
				txtY.setText("");
				txtR.setText("");
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public JTextField getTxtX() {
		return txtX;
	}

	public void setTxtX(JTextField txtX) {
		this.txtX = txtX;
	}

	public JTextField getTxtY() {
		return txtY;
	}

	public void setTxtY(JTextField txtY) {
		this.txtY = txtY;
	}

	public JTextField getTxtR() {
		return txtR;
	}

	public void setTxtR(JTextField txtR) {
		this.txtR = txtR;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public JPanel getPnlColor() {
		return pnlColor;
	}

	public void setPnlColor(JPanel pnlColor) {
		this.pnlColor = pnlColor;
	}

	public JPanel getPnlAreaColor() {
		return pnlAreaColor;
	}

	public void setPnlAreaColor(JPanel pnlAreaColor) {
		this.pnlAreaColor = pnlAreaColor;
	}

}
