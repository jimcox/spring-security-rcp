package jcox.security.rcp.client.ui;

import javax.swing.JPanel;


import jcox.security.rcp.client.service.ServiceLocator;

import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.Box;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;

public class MainPanel extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public MainPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnGetServerTime = new JButton("Get Server Time");

		btnGetServerTime.addActionListener(e -> 
		{
			System.out.println("Handled by Lambda listener");
			Date date = ServiceLocator.getDateService().getDate();
			
			textField.setText(date.toString());
			
		});
		
		panel.add(btnGetServerTime);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(20);
		textField.setEditable(false);
		
		Component rigidArea = Box.createRigidArea(new Dimension(450, 80));
		add(rigidArea, BorderLayout.NORTH);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(450, 97));
		add(rigidArea_1, BorderLayout.SOUTH);

	}

}
