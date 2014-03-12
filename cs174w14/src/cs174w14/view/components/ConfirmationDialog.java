package cs174w14.view.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ConfirmationDialog extends JDialog {
	private static final int DIALOG_WIDTH = 300;
	private static final int DIALOG_HEIGHT = 200;
	
	private JButton cancelButton;
	private JButton confirmButton;

	public ConfirmationDialog(String message) {
		this.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
		this.setModal(true);

		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new BorderLayout());
		dialogPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JTextArea messageArea = new JTextArea(message);
		messageArea.setLineWrap(true);
		messageArea.setWrapStyleWord(true);
		messageArea.setEditable(false);
		dialogPanel.add(messageArea, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		
		cancelButton = new JButton("Cancel");
		confirmButton = new JButton("Confirm");
		buttonPanel.add(cancelButton);
		buttonPanel.add(confirmButton);
		
		dialogPanel.add(buttonPanel, BorderLayout.SOUTH);
		this.add(dialogPanel);
	    this.setLocationRelativeTo(null); // center the window
	}
	
	public void addCancelButtonListener(ActionListener buttonListener) {
		cancelButton.addActionListener(buttonListener);
	}
	
	public void addConfirmButtonListener(ActionListener buttonListener) {
		confirmButton.addActionListener(buttonListener);
	}
}
