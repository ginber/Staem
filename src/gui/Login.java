package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel {

	private JTextField uName = new JTextField(20);
	private JTextField password = new JPasswordField(20);
	private JLabel nameLabel = new JLabel("User name:");
	private JLabel passwordLabel = new JLabel("Password:");
	private JButton loginButton = new JButton("Login");

	public Login() {

		setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		add(nameLabel, constraints);

		constraints.gridx = 1;

		add(uName, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;

		add(passwordLabel, constraints);

		constraints.gridx = 1;

		add(password, constraints);

		constraints.gridy = 2;
		constraints.gridx = 0;

		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// SQL Check
				// if check passes ------------------------------------------

				if (uName.getText().equals(AdminPage.NAME) && password.getText().equals(AdminPage.PASSWORD)) {

					new AdminPage();
					MainGUI.sf.close();

				}  else {

					try {
						MainGUI.rs = MainGUI.statement.executeQuery(
								"SELECT password\r\n" + "FROM USERS\r\n" + "WHERE name='" + uName.getText() + "'");
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					int b = 0;
					try {

						if (!MainGUI.rs.next() || !MainGUI.rs.getString("PASSWORD").equals(password.getText())) {
							JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Error",
									JOptionPane.ERROR_MESSAGE);
							b++;
						}
						do {
							if (b != 0)
								break;
							if (password.getText().equals(MainGUI.rs.getString("PASSWORD"))) {
								String name, mail = null, password = null, gender = null, dob = null;
								int wallet = 0, id = -1;
								name = uName.getText();
								try {
									MainGUI.rs = MainGUI.statement.executeQuery(
											"SELECT *\r\n" + "FROM USERS\r\n" + "WHERE name='" + uName.getText() + "'");
									while (MainGUI.rs.next()) {
										mail = MainGUI.rs.getString("EMAIL");
										password = MainGUI.rs.getString("PASSWORD");
										gender = MainGUI.rs.getString("GENDER");
										dob = MainGUI.rs.getString("DOB");
										dob = dob.substring(0, 10);
										wallet = MainGUI.rs.getInt("WALLET");
										id = MainGUI.rs.getInt("ID");
									}
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								User user = new User(name, mail, password, gender, wallet, dob, id);
								try {
									new LibraryGUI(user);
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								MainGUI.sf.close();
							}
						} while (MainGUI.rs.next());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

				// ----------------------------------------------------------
			}
		});

		add(loginButton, constraints);

	}

}
