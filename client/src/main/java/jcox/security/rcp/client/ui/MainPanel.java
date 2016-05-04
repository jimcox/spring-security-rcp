/*
 
 Copyright 2016 James Cox <james.s.cox@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 */

package jcox.security.rcp.client.ui;

import javax.swing.JPanel;

import jcox.security.rcp.client.service.ServiceInvocator;
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
			
			ServiceInvocator<Date> invocator = new ServiceInvocator<Date>(
			
			() -> ServiceLocator.getDateService().getDate(), 
			(Date date) -> textField.setText(date.toString()));
			
			invocator.invoke();
			
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
