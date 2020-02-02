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
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Shop extends JFrame {
	
	private final String EXIT_LOGO_PATH = "./res/images/exit_logo.png";
	private final String RETURN_LOGO_PATH = "./res/images/return_logo.png";

	private String[] games; // will get it from DB
	private String[] payments; // will get it from DB
	private JComboBox<String> gameBox;
	private JComboBox<String> paymentBox;
	private JComboBox<String> categoryBox;
	private JButton buy;
	private JLabel returnContainer, exitContainer, walletLabel;
	private JTextField walletField;
	private DefaultListModel<String> model = new DefaultListModel<>();

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

	public Shop(User user) throws SQLException {
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		createImages();
		
		MainGUI.rs = MainGUI.statement.executeQuery("SELECT count(NAME) FROM GAMES");
		int size=0,index=0;
		while(MainGUI.rs.next()) {
			size=MainGUI.rs.getInt(1);
		}								
		// add all games but already owned to games array
		// þimdilik deneme amaçlý
		games = new String[size];
		
		
		//MainGUI.rs = MainGUI.statement.executeQuery("SELECT distinct(g.NAME) FROM GAMES g, BUY b WHERE b.USER_ID=" + user.getID() + " and g.ID NOT IN (SELECT b.GAME_ID FROM BUY b)");
		MainGUI.rs = MainGUI.statement.executeQuery("SELECT g.NAME FROM GAMES g, BUY b WHERE b.GAME_ID=g.ID and b.USER_ID=" + user.getID());
		
		if(!MainGUI.rs.next()) {			
			MainGUI.rs = MainGUI.statement.executeQuery("SELECT g.NAME FROM GAMES g");
		}
		else 
			MainGUI.rs = MainGUI.statement.executeQuery("SELECT distinct(g.NAME) FROM GAMES g, BUY b WHERE b.USER_ID=" + user.getID() + " and g.ID NOT IN (SELECT b.GAME_ID FROM BUY b)");
		
		while(MainGUI.rs.next()) {
			games[index] = MainGUI.rs.getString(1);
			index++;
		}
		
		//for Payment
		MainGUI.rs = MainGUI.statement.executeQuery("SELECT count(NAME) FROM PAYMENT");
		int pSize=0,pIndex=0;		
		while(MainGUI.rs.next()) {
			pSize=MainGUI.rs.getInt(1);
		}
		
		payments = new String[pSize];
				
		MainGUI.rs = MainGUI.statement.executeQuery("SELECT NAME FROM PAYMENT");
		
		while(MainGUI.rs.next()) {
			payments[pIndex] = MainGUI.rs.getString(1);
			pIndex++;
		}
		
		List<String> comboBoxNames = new ArrayList<>();
		
		MainGUI.rs = MainGUI.statement.executeQuery("SELECT NAME FROM CATEGORY");
		while(MainGUI.rs.next()) {
			
			comboBoxNames.add(MainGUI.rs.getString(1));
			
		}
		
		categoryBox = new JComboBox(comboBoxNames.toArray());
		
		
		
		JList list = new JList(model);
		
		for(String s : games) {
			model.addElement(s);
		}
		
		JScrollPane scrollPane = new JScrollPane();		
		scrollPane.setViewportView(list);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		
		//list.setPreferredSize(new Dimension(200, 200));
				
		
		
		gameBox = new JComboBox<>(games);		
		gameBox.setSelectedIndex(0);
		gameBox.setPreferredSize(new Dimension(MainGUI.screenWidth / 4, MainGUI.screenHeight / 5));
		
		paymentBox = new JComboBox<>(payments);				
		
		buy = new JButton("Buy");
		buy.setPreferredSize(new Dimension(MainGUI.screenWidth / 5, MainGUI.screenHeight / 5));
		buy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				//int chosen = JOptionPane.showConfirmDialog(null, "Are you sure you want to buy " + gameBox.getItemAt(gameBox.getSelectedIndex()), "Sure?", JOptionPane.YES_NO_OPTION);
				int chosen = JOptionPane.showConfirmDialog(null, "Are you sure you want to buy " + list.getSelectedValue().toString(), "Sure?", JOptionPane.YES_NO_OPTION);
				
				if(chosen == JOptionPane.YES_OPTION) {
					
					int pChoosen = JOptionPane.showConfirmDialog(null, paymentBox);
					
					String selectedGame,selectedPayment;					
					
					if(pChoosen == JOptionPane.YES_OPTION) {
						selectedGame = list.getSelectedValue().toString();
						selectedPayment = paymentBox.getSelectedItem().toString();
						int uID = -1;
						
						int gID=-1, pID=-1;
						
						try {
							
							MainGUI.rs = MainGUI.statement.executeQuery("SELECT p.ID, g.ID, u.ID FROM USERS u,PAYMENT p, GAMES g WHERE g.NAME='" + selectedGame + "' AND p.NAME='" + selectedPayment +
																		"' AND u.NAME='" + user.getName() + "'");													
							
							while(MainGUI.rs.next()) {
								gID = MainGUI.rs.getInt(2);
								pID = MainGUI.rs.getInt(1);
								uID = MainGUI.rs.getInt(3);
							}
							
							
							
							//MainGUI.rs.close();
							//MainGUI.statement.close();
							
							DateTimeFormatter sene = DateTimeFormatter.ofPattern("dd/MM/YYYY");
							LocalDateTime date = LocalDateTime.now();															
							
							MainGUI.rs = MainGUI.statement.executeQuery("INSERT INTO BUY VALUES(" +uID + "," +gID+ "," +pID+ ",TO_DATE('" + sene.format(date) +"','DD/MM/YYYY'))");														
							
							
						} catch(SQLIntegrityConstraintViolationException e2) {
							JOptionPane.showMessageDialog(null, "You already have this game", "Error", JOptionPane.ERROR_MESSAGE);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					
					
					
				}				
				
				
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
		
		categoryBox.addActionListener(new ActionListener() {
			
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String categoryName = categoryBox.getItemAt(categoryBox.getSelectedIndex());
				model.removeAllElements();
				
				try {
					
					MainGUI.rs = MainGUI.statement.executeQuery("SELECT c.ID FROM CATEGORY c WHERE c.NAME='" + categoryName + "'");
					
					int cID=-1;
					
					while(MainGUI.rs.next()) {
						cID = MainGUI.rs.getInt(1);
					}
					
					MainGUI.rs.close();
					
					/*MainGUI.rs = MainGUI.statement.executeQuery("SELECT g.NAME FROM GAMES g, BUY b WHERE b.GAME_ID=g.ID and b.USER_ID="+ user.getID());	
					
					
					int girdim=0;
					
					while(MainGUI.rs.next()) {
						if(MainGUI.rs.getString(1)==null) {
							System.out.println("girrrrrrr");
							MainGUI.rs = MainGUI.statement.executeQuery("SELECT g.NAME FROM GAMES g");
						}
						else {
							girdim++;
							MainGUI.rs = MainGUI.statement.executeQuery("SELECT distinct(g.NAME) FROM GAMES g, BUY b, CATEGORY c, GAMES_CATEGORY gc WHERE gc.GAME_ID = g.ID and gc.CATEGORY_ID ="+ cID +" and g.ID NOT IN (SELECT b.GAME_ID FROM BUY b WHERE b.USER_ID=" + user.getID() + ")");
						}
						if(girdim>0)
							break;
					}*/
					MainGUI.rs = MainGUI.statement.executeQuery("SELECT g.NAME FROM GAMES g, BUY b WHERE b.GAME_ID=g.ID and b.USER_ID=" + user.getID());
					
					if(!MainGUI.rs.next()) {			
						MainGUI.rs = MainGUI.statement.executeQuery("SELECT g.NAME FROM GAMES g, GAMES_CATEGORY gc WHERE gc.GAME_ID=g.ID and gc.CATEGORY_ID=" + cID);
					}
					else 
						MainGUI.rs = MainGUI.statement.executeQuery("SELECT distinct(g.NAME) FROM GAMES g, BUY b, CATEGORY c, GAMES_CATEGORY gc WHERE gc.GAME_ID = g.ID and gc.CATEGORY_ID ="+ cID +" and g.ID NOT IN (SELECT b.GAME_ID FROM BUY b WHERE b.USER_ID=" + user.getID() + ")");
					
					while(MainGUI.rs.next()) {
						model.addElement(MainGUI.rs.getString(1));
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		String getWalletQuery = "";
		int wallet = 0;
		MainGUI.rs = MainGUI.statement.executeQuery(getWalletQuery);
		
		while(MainGUI.rs.next()) {
			wallet = MainGUI.rs.getInt(1);
		}
		
		walletLabel = new JLabel("Wallet:");
		walletField = new JTextField(wallet);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.anchor = GridBagConstraints.PAGE_START;
		
		add(returnContainer, gbc);
		
		//gbc.anchor = GridBagConstraints.WEST;
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 6.0;
		gbc.weighty = 6.0;
		
		add(scrollPane, gbc);
		
		gbc.gridy = 2;
		
		add(categoryBox, gbc);
		
		gbc.gridy = 1;
		gbc.gridx = 2;
		gbc.weightx = 4.0;
		gbc.weighty = 4.0;
		//gbc.anchor = GridBagConstraints.EAST;
		
		add(buy, gbc);
		
		
		gbc.gridx = 3;
		gbc.gridy = 3;
		
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		add(exitContainer, gbc);
		
		setVisible(true);

	}

}
