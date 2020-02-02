package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Profile extends JFrame {

	private final String EXIT_LOGO_PATH = "./res/images/exit_logo.png";
	private final String RETURN_LOGO_PATH = "./res/images/return_logo.png";

	private JComboBox<String> selectionArea;
	private JTextArea displayArea;
	private JLabel exitContainer, returnContainer;
	private JScrollPane selectionPane, displayPane;

	private void createImages() {

		BufferedImage returnImage = null;
		BufferedImage exitImage = null;

		try {
			returnImage = ImageIO.read(new File(RETURN_LOGO_PATH));
			exitImage = ImageIO.read(new File(EXIT_LOGO_PATH));
		} catch (IOException e) {
			System.out.println("Logo could not be loaded");
		}

		Image scaledImage = MainGUI.getScaledImage(returnImage, MainGUI.screenWidth / 6, MainGUI.screenHeight / 5);
		Image exitScaledImage = MainGUI.getScaledImage(exitImage, MainGUI.screenWidth / 10, MainGUI.screenHeight / 10);

		ImageIcon icon = new ImageIcon(scaledImage);
		ImageIcon exitIcon = new ImageIcon(exitScaledImage);

		returnContainer = new JLabel(icon);
		exitContainer = new JLabel(exitIcon);

		returnContainer.setPreferredSize(new Dimension(MainGUI.screenWidth / 6, MainGUI.screenHeight / 5));
		exitContainer.setPreferredSize(new Dimension(MainGUI.screenWidth / 10, MainGUI.screenHeight / 10));

	}

	/**
	 * @param user
	 */
	public Profile(User user) {

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setLayout(new GridBagLayout());

		createImages();

		exitContainer.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		returnContainer.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
				try {
					new LibraryGUI(user);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		GridBagConstraints gbc = new GridBagConstraints();

		// Get the necessary info via uName

		String[] userInfos = {"Name", "Mail", "Gender", "Password", "Age", "Wallet"};
		
		

		selectionArea = new JComboBox<>(userInfos);
		displayArea = new JTextArea();

		selectionArea.setSelectedIndex(0);
		displayArea.append(user.getName());

		selectionArea.setPreferredSize(new Dimension(MainGUI.screenWidth / 6, MainGUI.screenHeight / 6));
		displayArea.setPreferredSize(new Dimension(MainGUI.screenWidth / 5, MainGUI.screenHeight / 5));

		selectionArea.setEditable(false);
		displayArea.setEditable(false);

		selectionArea.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				switch (selectionArea.getSelectedIndex()) {

				case 0:
					displayArea.setText("");
					displayArea.append(user.getName());
					break;
				case 1:
					displayArea.setText("");
					displayArea.append(user.getMail());
					break;
				case 2:
					displayArea.setText("");
					displayArea.append(user.getGender());
					break;
				case 3:
					displayArea.setText("");
					displayArea.append(user.getPassword());
					break;
				case 5:
					displayArea.setText(user.getWallet() + "");
					break;
				case 4:
					displayArea.setText(user.getDob() + "");
					break;
				
				
				}

			}
		});

		selectionArea.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
		displayArea.setBorder(BorderFactory.createDashedBorder(Color.BLACK));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 2;
		gbc.weighty = 1;

		add(returnContainer, gbc);

		gbc.gridy = 1;
		gbc.weighty = 3;

		add(selectionArea, gbc);

		gbc.gridx = 1;
		//gbc.gridy = 0;
		gbc.weightx = 3;

		add(displayArea, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		//gbc.anchor = GridBagConstraints.SOUTHEAST;

		add(exitContainer, gbc);

		setVisible(true);

	}

}
