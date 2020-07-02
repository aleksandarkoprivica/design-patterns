package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
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

import javax.swing.JTextField;

public class DlgLine extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtXStart;
	private JTextField txtYStart;
	private JTextField txtXEnd;
	private JTextField txtYEnd;
	private JPanel pnlColor;
	private boolean check;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgLine dialog = new DlgLine();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgLine() {
		setModal(true);
		setLocation(800,600);
		setResizable(false);
		setTitle("Line");
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
			JLabel lblXStartCoordinate = new JLabel("X start coordinate:");
			GridBagConstraints gbc_lblXStartCoordinate = new GridBagConstraints();
			gbc_lblXStartCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblXStartCoordinate.gridx = 0;
			gbc_lblXStartCoordinate.gridy = 0;
			contentPanel.add(lblXStartCoordinate, gbc_lblXStartCoordinate);
		}
		{
			txtXStart = new JTextField();
			GridBagConstraints gbc_txtXStart = new GridBagConstraints();
			gbc_txtXStart.insets = new Insets(0, 0, 5, 0);
			gbc_txtXStart.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtXStart.gridx = 3;
			gbc_txtXStart.gridy = 0;
			contentPanel.add(txtXStart, gbc_txtXStart);
			txtXStart.setColumns(10);
		}
		{
			JLabel lblYStartCoordinate = new JLabel("Y start coordinate:");
			GridBagConstraints gbc_lblYStartCoordinate = new GridBagConstraints();
			gbc_lblYStartCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblYStartCoordinate.gridx = 0;
			gbc_lblYStartCoordinate.gridy = 1;
			contentPanel.add(lblYStartCoordinate, gbc_lblYStartCoordinate);
		}
		{
			txtYStart = new JTextField();
			GridBagConstraints gbc_txtYStart = new GridBagConstraints();
			gbc_txtYStart.insets = new Insets(0, 0, 5, 0);
			gbc_txtYStart.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtYStart.gridx = 3;
			gbc_txtYStart.gridy = 1;
			contentPanel.add(txtYStart, gbc_txtYStart);
			txtYStart.setColumns(10);
		}
		{
			JLabel lblXEndCoordinate = new JLabel("X end coordinate:");
			GridBagConstraints gbc_lblXEndCoordinate = new GridBagConstraints();
			gbc_lblXEndCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblXEndCoordinate.gridx = 0;
			gbc_lblXEndCoordinate.gridy = 2;
			contentPanel.add(lblXEndCoordinate, gbc_lblXEndCoordinate);
		}
		{
			txtXEnd = new JTextField();
			GridBagConstraints gbc_txtXEnd = new GridBagConstraints();
			gbc_txtXEnd.anchor = GridBagConstraints.NORTH;
			gbc_txtXEnd.insets = new Insets(0, 0, 5, 0);
			gbc_txtXEnd.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtXEnd.gridx = 3;
			gbc_txtXEnd.gridy = 2;
			contentPanel.add(txtXEnd, gbc_txtXEnd);
			txtXEnd.setColumns(10);
		}
		{
			JLabel lblYEndCoordinate = new JLabel("Y end coordinate");
			GridBagConstraints gbc_lblYEndCoordinate = new GridBagConstraints();
			gbc_lblYEndCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblYEndCoordinate.gridx = 0;
			gbc_lblYEndCoordinate.gridy = 3;
			contentPanel.add(lblYEndCoordinate, gbc_lblYEndCoordinate);
		}
		{
			txtYEnd = new JTextField();
			GridBagConstraints gbc_txtYEnd = new GridBagConstraints();
			gbc_txtYEnd.anchor = GridBagConstraints.NORTH;
			gbc_txtYEnd.insets = new Insets(0, 0, 5, 0);
			gbc_txtYEnd.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtYEnd.gridx = 3;
			gbc_txtYEnd.gridy = 3;
			contentPanel.add(txtYEnd, gbc_txtYEnd);
			txtYEnd.setColumns(10);
		}
		{
			JLabel lblColor = new JLabel("Color:");
			GridBagConstraints gbc_lblColor = new GridBagConstraints();
			gbc_lblColor.insets = new Insets(0, 0, 0, 5);
			gbc_lblColor.gridx = 0;
			gbc_lblColor.gridy = 5;
			contentPanel.add(lblColor, gbc_lblColor);
		}
		pnlColor = new JPanel();
		pnlColor.setBorder((Border) new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		pnlColor.setSize(200, 100);
		pnlColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color color = JColorChooser.showDialog(null, "Choose line color:", Color.BLACK);
				if(color != null)
				pnlColor.setBackground(color);
			}
		});
		GridBagConstraints gbc_pnlColor = new GridBagConstraints();
		gbc_pnlColor.fill = GridBagConstraints.BOTH;
		gbc_pnlColor.gridx = 3;
		gbc_pnlColor.gridy = 5;
		contentPanel.add(pnlColor, gbc_pnlColor);
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
	}

	public JTextField getTxtXStart() {
		return txtXStart;
	}

	public void setTxtXStart(JTextField txtXStart) {
		this.txtXStart = txtXStart;
	}

	public JTextField getTxtYStart() {
		return txtYStart;
	}

	public void setTxtYStart(JTextField txtYStart) {
		this.txtYStart = txtYStart;
	}

	public JTextField getTxtXEnd() {
		return txtXEnd;
	}

	public void setTxtXEnd(JTextField txtXEnd) {
		this.txtXEnd = txtXEnd;
	}

	public JTextField getTxtYEnd() {
		return txtYEnd;
	}

	public void setTxtYEnd(JTextField txtYEnd) {
		this.txtYEnd = txtYEnd;
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

}
