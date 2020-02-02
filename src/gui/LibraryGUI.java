package gui;

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
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LibraryGUI extends JFrame {

	private final String EXIT_LOGO_PATH = "./res/images/exit_logo.png";
	private final String RETURN_LOGO_PATH = "./res/images/return_logo.png";

	private List<String> games;
	private User user;

	private JLabel exitContainer, returnContainer; 
	private JList ownedGames;
	private JTextArea description = new JTextArea();
	private DefaultListModel<String> gameModel = new DefaultListModel<>();
	private JButton shop = new JButton("Shop");
	private JButton profile = new JButton("Profile");
	private JScrollPane jsPane, descPane;

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

		returnContainer= new JLabel(icon);
		exitContainer = new JLabel(exitIcon);

		returnContainer.setPreferredSize(new Dimension(MainGUI.screenWidth / 6, MainGUI.screenHeight / 5));
		exitContainer.setPreferredSize(new Dimension(MainGUI.screenWidth / 10, MainGUI.screenHeight / 10));

	}

	public LibraryGUI(User user) throws SQLException {
		
		this.user = user;

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		
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
				new StartFrame(MainGUI.NAME);
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

		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		
		ownedGames = new JList(gameModel);

		ownedGames.setSize(new Dimension(getToolkit().getScreenSize().height, getToolkit().getScreenSize().width / 2));
		description.setPreferredSize(new Dimension(getToolkit().getScreenSize().height, getToolkit().getScreenSize().width / 2));

		jsPane = new JScrollPane(ownedGames);
		jsPane.setPreferredSize(
				new Dimension(getToolkit().getScreenSize().height / 2, getToolkit().getScreenSize().width / 3));
		
		descPane = new JScrollPane(description);
		jsPane.setPreferredSize(
				new Dimension(getToolkit().getScreenSize().height / 2, getToolkit().getScreenSize().width / 3));
		
		
		/*
		 * ----------------------------------------------------------------------------
		 * 					GET GAMES FROM DATABASE TO games and jsPane
		 * ----------------------------------------------------------------------------
		 */
		
		
		MainGUI.rs = MainGUI.statement.executeQuery("SELECT g.NAME FROM GAMES g, BUY b, USERS u WHERE g.ID = b.GAME_ID and u.ID=b.USER_ID and u.NAME='" + user.getName()+ "'"); 
		while(MainGUI.rs.next()) {
			
			gameModel.addElement(MainGUI.rs.getString(1) + "\n");
			System.out.println(MainGUI.rs.getString(1));
			
		}
		
		ownedGames.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				String descriptionText = "";
				String selectedGame = (String) ownedGames.getSelectedValue();
				// ---------------------------
				String getDescQuery = ""; // | // Get description of the game
				// ---------------------------
				
				try {
					MainGUI.rs = MainGUI.statement.executeQuery(getDescQuery);
					while(MainGUI.rs.next()) {
						descriptionText = MainGUI.rs.getString(1);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				description.setText(descriptionText);
				
			}
		});
		
		profile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new Profile(user);
				dispose();
				
			}
		});
		
		shop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					new Shop(user);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
				
			}
		});
		

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 2;
		gbc.weighty = 2;
		
		gbc.anchor = GridBagConstraints.PAGE_START;

		add(returnContainer, gbc);
		
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		
		add(jsPane, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 3;

		add(shop, gbc);

		gbc.gridy = 1;

		add(profile, gbc);
		
		//gbc.gridx = 2;
		//gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		add(exitContainer, gbc);

		setVisible(true);

	}

}
