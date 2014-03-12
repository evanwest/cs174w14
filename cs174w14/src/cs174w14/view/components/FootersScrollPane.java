package cs174w14.view.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class FootersScrollPane extends JScrollPane {
	private JPanel orderPanel;
	private int numComponents;
	private int numFooters;
	
	private ArrayList<JTextField> modelNumberFields;
	private ArrayList<JTextField> quantityFields;
	
	public FootersScrollPane() {
		orderPanel = new JPanel();
		orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.PAGE_AXIS));
		
		orderPanel.add(Box.createVerticalGlue());
		numComponents = 0;
		numFooters = 0;
		
		this.setViewportView(orderPanel);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	
	public Component add(Component comp) {
		comp.setMaximumSize(new Dimension(Integer.MAX_VALUE, comp.getPreferredSize().height));
		orderPanel.add(comp, numComponents++);
		numComponents++;
		return comp;
	}
	
	public Component addFooter(Component comp) {
		comp.setMaximumSize(new Dimension(Integer.MAX_VALUE, comp.getPreferredSize().height));
		orderPanel.add(comp, numComponents + numFooters++);
		return comp;
	}
	
	// Removes everything in this ScrollPane but the footers.
	public void removeAll() {
		for (; numComponents > 0; numComponents--) {
			orderPanel.remove(numComponents-1);
		}
	}
	
	public void removeFooters() {
		for (; numFooters > 0; numFooters--) {
			orderPanel.remove(numComponents + numFooters-1);
		}
	}

}
