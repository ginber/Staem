package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import listeners.SKeyListener;

public class StartFrame {


	private final String LOGO_PATH = "./res/images/stæm_logo.png";
	private final String EXIT_LOGO_PATH = "./res/images/exit_logo.png";

	private JFrame frame;
	private JLabel iconContainer, exitContainer;
	private JTextField userNameText, passwordText;
	private JButton login, signup;

	protected void createIcon() {

		BufferedImage iconImage = null;
		BufferedImage exitImage = null;

		try {
			iconImage = ImageIO.read(new File(LOGO_PATH));
			exitImage = ImageIO.read(new File(EXIT_LOGO_PATH));
		} catch (IOException e) {
			System.out.println("Logo could not be loaded");
		}

		Image scaledImage = MainGUI.getScaledImage(iconImage, MainGUI.screenWidth / 4, MainGUI.screenHeight / 5);
		Image exitScaledImage = MainGUI.getScaledImage(exitImage, MainGUI.screenWidth / 10, MainGUI.screenHeight / 10);

		ImageIcon icon = new ImageIcon(scaledImage);
		ImageIcon exitIcon = new ImageIcon(exitScaledImage);
		
		iconContainer = new JLabel(icon);
		exitContainer = new JLabel(exitIcon);
		
		iconContainer.setPreferredSize(new Dimension(MainGUI.screenWidth / 4, MainGUI.screenHeight / 5));
		exitContainer.setPreferredSize(new Dimension(MainGUI.screenWidth / 10, MainGUI.screenHeight / 10));
		

		System.out.println("screen width: " + MainGUI.screenWidth); // OK
		System.out.println("image icon width: " + icon.getIconWidth()); // OK
		System.out.println("width: " + iconContainer.getWidth()); // -> FAIL

	}

	public StartFrame(String title) {

		frame = new JFrame(title);

		frame.addKeyListener(new SKeyListener());

		frame.setUndecorated(true); // Getting rid of OS buttons - like minimize, close etc.
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Making the frame full screen

		frame.setLayout(new GridBagLayout()); // GridBagLayout is easy to use
		GridBagConstraints constraints = new GridBagConstraints(); // Constraints for the layout

		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new GridBagLayout());

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.BOTH;

		contentPane.add(Box.createGlue(), constraints);

		createIcon();

		constraints.gridy = 1;
		constraints.weightx = 5.0;
		constraints.weighty = 5.0;
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.fill = GridBagConstraints.HORIZONTAL;

		contentPane.add(iconContainer, constraints);

		constraints.gridy = 2;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 3.0;
		
		JPanel loginPanel = new Login();
		JPanel signupPanel = new NewUser();

		login = new JButton("Login");
		login.setVisible(false);
		signupPanel.setVisible(false);
		signup = new JButton("Signup");

		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				loginPanel.setVisible(true);
				signupPanel.setVisible(false);

				login.setVisible(false);
				signup.setVisible(true);

			}
		});

		signup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				signupPanel.setVisible(true);
				loginPanel.setVisible(false);

				signup.setVisible(false);
				login.setVisible(true);

			}
		});

		contentPane.add(login, constraints);
		contentPane.add(signup, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 5.0;
		constraints.weighty = 5.0;

		contentPane.add(loginPanel, constraints);
		contentPane.add(signupPanel, constraints);
		
		constraints.anchor = GridBagConstraints.SOUTHEAST;
		
		contentPane.add(exitContainer, constraints);
		
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

		frame.pack();
		frame.setVisible(true);

	}

	
	
	public void close() {
		
		frame.dispose();
		
	}

}
