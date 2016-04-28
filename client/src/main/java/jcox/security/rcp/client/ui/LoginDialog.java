package jcox.security.rcp.client.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import jcox.security.rcp.client.service.ServiceInvocator;
import jcox.security.rcp.client.service.ServiceLocator;

public class LoginDialog extends JDialog {

	 
	private final static Logger logger = LoggerFactory.getLogger(LoginDialog.class);
	
	private final JPanel contentPanel = new JPanel();
	private JTextField txtUserName;
	private JPasswordField passwordField;
 
	private final ServiceInvocator<?> accessDenied;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LoginDialog dialog = new LoginDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LoginDialog( ) {
		
		this(null);
		
	}
	
	
	/**
	 * Create the dialog.
	 */
	public LoginDialog(ServiceInvocator<?> accessDenied) {
		
		this.accessDenied = accessDenied;
		
		setTitle("Authenticate");
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 283, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{43, 0, 45, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
			
			Component horizontalStrut = Box.createHorizontalStrut(30);
			GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
			gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
			gbc_horizontalStrut.gridx = 2;
			gbc_horizontalStrut.gridy = 1;
			contentPanel.add(horizontalStrut, gbc_horizontalStrut);
		
			Component verticalStrut = Box.createVerticalStrut(20);
			GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
			gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
			gbc_verticalStrut.gridx = 1;
			gbc_verticalStrut.gridy = 0;
			contentPanel.add(verticalStrut, gbc_verticalStrut);
		
		
			JLabel lblUserName = new JLabel("User Name:");
			GridBagConstraints gbc_lblUserName = new GridBagConstraints();
			gbc_lblUserName.insets = new Insets(0, 0, 5, 5);
			gbc_lblUserName.anchor = GridBagConstraints.EAST;
			gbc_lblUserName.gridx = 0;
			gbc_lblUserName.gridy = 1;
			contentPanel.add(lblUserName, gbc_lblUserName);
		
		
			txtUserName = new JTextField(20);
			GridBagConstraints gbc_txtUserName = new GridBagConstraints();
			gbc_txtUserName.insets = new Insets(0, 0, 5, 5);
			gbc_txtUserName.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUserName.gridx = 1;
			gbc_txtUserName.gridy = 1;
			contentPanel.add(txtUserName, gbc_txtUserName);
			txtUserName.setColumns(10);
		
		
			Component verticalStrut2 = Box.createVerticalStrut(20);
			GridBagConstraints gbc_verticalStrut2 = new GridBagConstraints();
			gbc_verticalStrut2.insets = new Insets(0, 0, 5, 0);
			gbc_verticalStrut2.gridx = 1;
			gbc_verticalStrut2.gridy = 2;
			contentPanel.add(verticalStrut2, gbc_verticalStrut2);
		
		
			JLabel lblPassword = new JLabel("Password:");
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.anchor = GridBagConstraints.EAST;
			gbc_lblPassword.insets = new Insets(0, 0, 0, 5);
			gbc_lblPassword.gridx = 0;
			gbc_lblPassword.gridy = 3;
			contentPanel.add(lblPassword, gbc_lblPassword);
		
		
			passwordField = new JPasswordField(20);
			GridBagConstraints gbc_passwordField = new GridBagConstraints();
			gbc_passwordField.insets = new Insets(0, 0, 0, 5);
			gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
			gbc_passwordField.gridx = 1;
			gbc_passwordField.gridy = 3;
			contentPanel.add(passwordField, gbc_passwordField);
		
		
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
				JButton okButton = new JButton("OK");
				
				okButton.addActionListener(e -> 
				
				{
					ServiceInvocator<Authentication> invocator = new ServiceInvocator<Authentication> (
							
						() -> { 
							
							UsernamePasswordAuthenticationToken authRequest =
									new UsernamePasswordAuthenticationToken(txtUserName.getText(), String.valueOf(passwordField.getPassword()));
							
							return ServiceLocator.getRemoteAuthenticationManager().authenticate(authRequest);
						},
						
						(Authentication authentication) ->	{
							
							logger.debug("authentication is {}", authentication);
		
							if(accessDenied != null) {
								
								accessDenied.invoke();
							}
							
						});
												
					invocator.invoke();
					this.dispose();

				});
				
				
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			
			
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				
				cancelButton.addActionListener(e -> this.dispose() );
				
				
				buttonPane.add(cancelButton);
			
		
	}

}
