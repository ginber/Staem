package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class NewUser extends JPanel {

	private JTextField nameText, passwordText, emailText;
	private JLabel nameLabel, passwordLabel, dobLabel, genderLabel, emailLabel;
	private JButton signUpButton;
	private SpinnerListModel gender;
	private JSpinner genderSpinner;
	private static int ID = 263;
	private static int YEAR = 2019;

	public NewUser() {

		nameText = new JTextField(20);
		passwordText = new JTextField(20);
		emailText = new JTextField(20);

		nameLabel = new JLabel("User name:");
		passwordLabel = new JLabel("Password:");
		dobLabel = new JLabel("Date of birth:");
		genderLabel = new JLabel("Gender:");
		emailLabel = new JLabel("E-mail:");

		List<String> genderList = new ArrayList<>();
		genderList.add("M");
		genderList.add("F");

		gender = new SpinnerListModel(genderList);

		genderSpinner = new JSpinner(gender);

		signUpButton = new JButton("Sign Up");
		

		setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		add(nameLabel, constraints);

		constraints.gridx = 1;

		add(nameText, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;

		add(passwordLabel, constraints);

		constraints.gridx = 1;

		add(passwordText, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;

		add(emailLabel, constraints);

		constraints.gridx = 1;
		
		add(emailText, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;

		add(genderLabel, constraints);

		constraints.gridx = 1;

		add(genderSpinner, constraints);

		constraints.gridy = 4;
		constraints.gridx = 0;

		add(dobLabel, constraints);

		constraints.gridx = 1;

		// Using an external library that implements a date picker for Swing
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		signUpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// ----------------------------------
				// TODO: Add user to the database |
				// ----------------------------------
				
				PreparedStatement ps = null;
				String s1;
				
				try {
					String[] date = datePicker.getJFormattedTextField().getText().split("-");
					String day = date[2];
					String month = date[1];
					String year = date[0];
					String finalDate = day + "/" + month + "/" + year;
					
					
					
					/*s1 = "INSERT INTO USERS(ID,NAME,PASSWORD,EMAIL,WALLET,GENDER,DOB) VALUES (?,?,?,?,?,?,?)";
					ps = MainGUI.conn.prepareStatement(s1);
					ps.setInt(1, ID++);
					ps.setString(2, nameText.getText());
					ps.setString(3, passwordText.getText());
					ps.setString(4, emailText.getText());					
					ps.setInt(5, 0);
					ps.setString(6, gender.getValue().toString());
					System.out.println(gender.getValue().toString());
					ps.setDate(7, new Date(1999, 05, 29));
					
										
					ps.executeUpdate();
					*/
					
					s1 = "INSERT INTO USERS VALUES(".concat(""+(ID++)).concat(",'").concat(nameText.getText()).concat("','").concat(passwordText.getText()).concat("','")
												.concat(emailText.getText()).concat("',0,").concat("'").concat(gender.getValue().toString()).concat("',").
												concat("TO_DATE('" + finalDate + "','DD/MM/YYYY'))");
					
					System.out.println(s1);
					
					System.out.println("AA " + MainGUI.statement.executeQuery(s1) + " bb");
					MainGUI.conn.commit();
					
					JOptionPane.showMessageDialog(null, "User successfully registered", "Success", JOptionPane.OK_OPTION);
					
				} catch(SQLIntegrityConstraintViolationException constraintException) {
					
					JOptionPane.showMessageDialog(null, "A user with this name already exists", "Error", JOptionPane.ERROR_MESSAGE);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					/*try {
						//ps.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}*/
				}
						
				new StartFrame(MainGUI.NAME); // return to the main screen

			}
		});

		add(datePicker, constraints);

		constraints.gridx = 0;
		constraints.gridy = 5;

		add(signUpButton, constraints);

	}

}
