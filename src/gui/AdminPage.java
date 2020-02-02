package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AdminPage extends JFrame {

	public static final String NAME = "Admin";
	public static final String PASSWORD = "psw";

	private final String EXIT_LOGO_PATH = "./res/images/exit_logo.png";
	private final String RETURN_LOGO_PATH = "./res/images/return_logo.png";

	private List<String> userNames = new ArrayList<>();
	private List<String> gameNames = new ArrayList<>();

	private JLabel exitContainer, returnContainer;
	private DefaultListModel<String> userModel = new DefaultListModel<>();
	private DefaultListModel<String> gameModel = new DefaultListModel<>();
	private DefaultListModel<String> categoryModel = new DefaultListModel<>();
	private DefaultListModel<String> companyModel = new DefaultListModel<>();
	private DefaultListModel<String> paymentModel = new DefaultListModel<>();
	private JScrollPane userPane, gamePane, categoryPane, companyPane, paymentPane;
	private JButton editUserButton, removeUserButton, addGameButton, editGameButton, removeGameButton,
			addCategoryButton, removeCategoryButton, addCompanyButton, editCompanyButton, removeCompanyButton,
			addPaymentButton, removePaymentButton;

	private void createImages() {

		BufferedImage returnImage = null;
		BufferedImage exitImage = null;

		try {
			returnImage = ImageIO.read(new File(RETURN_LOGO_PATH));
			exitImage = ImageIO.read(new File(EXIT_LOGO_PATH));
		} catch (IOException e) {
			System.out.println("Logo could not be loaded");
		}

		Image scaledImage = MainGUI.getScaledImage(returnImage, MainGUI.screenWidth / 8, MainGUI.screenHeight / 6);
		Image exitScaledImage = MainGUI.getScaledImage(exitImage, MainGUI.screenWidth / 10, MainGUI.screenHeight / 10);

		ImageIcon icon = new ImageIcon(scaledImage);
		ImageIcon exitIcon = new ImageIcon(exitScaledImage);

		returnContainer = new JLabel(icon);
		exitContainer = new JLabel(exitIcon);

		returnContainer.setPreferredSize(new Dimension(MainGUI.screenWidth / 8, MainGUI.screenHeight / 6));
		exitContainer.setPreferredSize(new Dimension(MainGUI.screenWidth / 10, MainGUI.screenHeight / 10));

	}

	public AdminPage() {

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		createImages();

		// inþ doðrudur -- BUNLARA BAKIN DOÐRU MU DÝYE
		String getUserQuery = "SELECT NAME FROM USERS";
		String getGamesQuery = "SELECT NAME FROM GAMES";
		String getCategoriesQuery = "SELECT NAME FROM CATEGORY";
		String getCompaniesQuery = "SELECT NAME FROM COMPANY";
		String getPaymentQuery = "SELECT NAME FROM PAYMENT";

		try {

			MainGUI.rs = MainGUI.statement.executeQuery(getUserQuery);
			while (MainGUI.rs.next()) {
				userModel.addElement(MainGUI.rs.getString(1) + "\n");
			}

			MainGUI.rs = MainGUI.statement.executeQuery(getGamesQuery);
			while (MainGUI.rs.next()) {
				gameModel.addElement(MainGUI.rs.getString(1) + "\n");
			}

			MainGUI.rs = MainGUI.statement.executeQuery(getCategoriesQuery);
			while (MainGUI.rs.next()) {
				categoryModel.addElement(MainGUI.rs.getString(1) + "\n");
			}

			MainGUI.rs = MainGUI.statement.executeQuery(getCompaniesQuery);
			while (MainGUI.rs.next()) {
				companyModel.addElement(MainGUI.rs.getString(1) + "\n");
			}

			MainGUI.rs = MainGUI.statement.executeQuery(getPaymentQuery);
			while (MainGUI.rs.next()) {
				paymentModel.addElement(MainGUI.rs.getString(1) + "\n");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JLabel usersLabel = new JLabel("USERS");
		JLabel gamesLabel = new JLabel("GAMES");
		JLabel categoriesLabel = new JLabel("CATEGORIES");
		JLabel companiesLabel = new JLabel("COMPANIES");
		JLabel paymentLabel = new JLabel("PAYMENT METHODS");

		JList userList = new JList<>(userModel);
		JList gameList = new JList<>(gameModel);
		JList categoryList = new JList<>(categoryModel);
		JList companyList = new JList<>(companyModel);
		JList paymentList = new JList<>(paymentModel);

		userPane = new JScrollPane(userList);
		gamePane = new JScrollPane(gameList);
		categoryPane = new JScrollPane(categoryList);
		companyPane = new JScrollPane(companyList);
		paymentPane = new JScrollPane(paymentList);

		editUserButton = new JButton("Edit");
		editCompanyButton = new JButton("Edit");
		editGameButton = new JButton("Edit");

		removeCategoryButton = new JButton("Remove");
		removeCompanyButton = new JButton("Remove");
		removeGameButton = new JButton("Remove");
		removePaymentButton = new JButton("Remove");
		removeUserButton = new JButton("Remove");

		addCategoryButton = new JButton("Add");
		addCompanyButton = new JButton("Add");
		addGameButton = new JButton("Add");
		addPaymentButton = new JButton("Add");

		addGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String name = (String) JOptionPane.showInputDialog(null, "Name:", "Add New Game",
						JOptionPane.OK_CANCEL_OPTION);

				String description = (String) JOptionPane.showInputDialog(null, "Description:", "Add New Game",
						JOptionPane.OK_CANCEL_OPTION);

				String rating = (String) JOptionPane.showInputDialog(null, "Rating:", "Add New Game",
						JOptionPane.OK_CANCEL_OPTION);

				String price = (String) JOptionPane.showInputDialog(null, "Price:", "Add New Game",
						JOptionPane.OK_CANCEL_OPTION);

				// Yukarda name, description falan var bunlarý GAME e ekleyecek SQLi yazýn,
				// price falan da
				// int ise Integer.parseInt() vb kullanýrsýnýz
				// ------------------------------
				String insertGameQuery = ""; // |
				// ------------------------------

				try {
					MainGUI.rs = MainGUI.statement.executeQuery(insertGameQuery);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		addCategoryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String name = (String) JOptionPane.showInputDialog(null, "Name:", "Add New Category",
						JOptionPane.OK_CANCEL_OPTION);

				// yukardaki name i CATEGORYye ekleyecek SQL scripti
				// ----------------------------------
				String insertCategoryQuery = ""; // |
				// ----------------------------------

				try {
					MainGUI.rs = MainGUI.statement.executeQuery(insertCategoryQuery);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		addCompanyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String name = (String) JOptionPane.showInputDialog(null, "Name:", "Add New Company",
						JOptionPane.OK_CANCEL_OPTION);

				// yukardaki name i COMPANY ye ekleyecek SQL scripti
				// ---------------------------------
				String insertCompanyQuery = ""; // |
				// ---------------------------------

				try {
					MainGUI.rs = MainGUI.statement.executeQuery(insertCompanyQuery);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		addPaymentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String name = (String) JOptionPane.showInputDialog(null, "Name:", "Add New Payment Method",
						JOptionPane.OK_CANCEL_OPTION);

				// yukardaki name i PAYMENT a ekleyecek SQL scripti
				// ---------------------------------
				String insertPaymentQuery = ""; // |
				// ---------------------------------

				try {
					MainGUI.rs = MainGUI.statement.executeQuery(insertPaymentQuery);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		editUserButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String[] userVals = new String[6];

				// -------------------------------
				String getUserValsQuery = ""; // | // SELECT * FROM USER olmasý gerek sanýrým ama siz yazýn þimdi yanlýþ
												// biþeyler yazmayayým
				// -------------------------------

				// [0] -> NAME
				// [1] -> PASSWORD
				// [2] -> EMAIL
				// [3] -> WALLET
				// [4] -> GENDER
				// [5] -> DOB

				try {
					MainGUI.rs = MainGUI.statement.executeQuery(getUserValsQuery);

					while (MainGUI.rs.next()) {

						userVals[0] = MainGUI.rs.getString(2);
						userVals[1] = MainGUI.rs.getString(3);
						userVals[2] = MainGUI.rs.getString(4);
						userVals[3] = MainGUI.rs.getString(5);
						userVals[4] = MainGUI.rs.getString(6);
						userVals[5] = MainGUI.rs.getString(7);

					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				String name = (String) JOptionPane.showInputDialog(null, "Name:", userVals[0]);

				String password = (String) JOptionPane.showInputDialog(null, "Password:", userVals[1]);

				String email = (String) JOptionPane.showInputDialog(null, "E-mail:", userVals[2]);

				String wallet = (String) JOptionPane.showInputDialog(null, "Wallet:", userVals[3]);

				String gender = (String) JOptionPane.showInputDialog(null, "Gender:", userVals[4]);

				String dob = (String) JOptionPane.showInputDialog(null, "Date of Birth:", userVals[5]);

				// ------------------------------
				String updateUserQuery = ""; // |
				// ------------------------------

				// Yukardakilere göre user ý update edicek query

				try {
					MainGUI.statement.executeUpdate(updateUserQuery);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		editCompanyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String oldName = (String) gameList.getSelectedValue();

				String name = (String) JOptionPane.showInputDialog(null, "Name:", oldName);

				// ---------------------------------
				String updateCompanyQuery = ""; // | // WHERE COMPANY.NAME = oldName
				// ---------------------------------

				try {
					MainGUI.statement.executeUpdate(updateCompanyQuery);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		editGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String oldName = (String) gameList.getSelectedValue();
				String oldDescription = (String) gameList.getSelectedValue();
				String oldPrice = (String) gameList.getSelectedValue();

				String name = (String) JOptionPane.showInputDialog(null, "Name:", oldName);
				String description = (String) JOptionPane.showInputDialog(null, "Description:", oldDescription);
				String price = (String) JOptionPane.showInputDialog(null, "Price:", oldPrice);

				// ------------------------------
				String updateGameQuery = ""; // |
				// ------------------------------

				try {
					MainGUI.statement.executeUpdate(updateGameQuery);
				} catch (SQLException ez) {
					// TODO Auto-generated catch block
					ez.printStackTrace();
				}

			}
		});

		removeCategoryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String name = (String) categoryList.getSelectedValue();

				int choice = JOptionPane.showConfirmDialog(null, "Are you sure to delete " + name + "?");

				if (choice == JOptionPane.OK_OPTION) {

					// ----------------------------------
					String removeCategoryQuery = ""; // |
					// ----------------------------------

					try {
						MainGUI.statement.execute(removeCategoryQuery);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		});

		removeCompanyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String name = (String) companyList.getSelectedValue();

				int choice = JOptionPane.showConfirmDialog(null, "Are you sure to delete " + name + "?");

				if (choice == JOptionPane.OK_OPTION) {

					// ----------------------------------
					String removeCompanyQuery = ""; // |
					// ----------------------------------

					try {
						MainGUI.statement.execute(removeCompanyQuery);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		});

		removeGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String name = (String) gameList.getSelectedValue();

				int choice = JOptionPane.showConfirmDialog(null, "Are you sure to delete " + name + "?");

				if (choice == JOptionPane.OK_OPTION) {

					// ----------------------------------
					String removeGameQuery = ""; // |
					// ----------------------------------

					try {
						MainGUI.statement.execute(removeGameQuery);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		});

		removePaymentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String name = (String) categoryList.getSelectedValue();

				int choice = JOptionPane.showConfirmDialog(null, "Are you sure to delete " + name + "?");

				if (choice == JOptionPane.OK_OPTION) {

					// ----------------------------------
					String removePaymentQuery = ""; // |
					// ----------------------------------

					try {
						MainGUI.statement.execute(removePaymentQuery);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		});

		removeUserButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String name = (String) userList.getSelectedValue();

				int choice = JOptionPane.showConfirmDialog(null, "Are you sure to delete " + name + "?");

				if (choice == JOptionPane.OK_OPTION) {

					// ----------------------------------
					String removeUserQuery = ""; // |
					// ----------------------------------

					try {
						MainGUI.statement.execute(removeUserQuery);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 2;
		gbc.weighty = 2;

		gbc.anchor = GridBagConstraints.PAGE_START;
		add(returnContainer, gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(usersLabel, gbc);

		gbc.gridy = 2;
		gbc.weightx = 4;
		gbc.weighty = 4;
		gbc.fill = GridBagConstraints.BOTH;
		add(userPane, gbc);

		gbc.fill = GridBagConstraints.NONE;
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(editUserButton, BorderLayout.WEST);
		buttonPanel.add(removeUserButton, BorderLayout.EAST);

		gbc.gridy = 3;
		add(buttonPanel, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.weightx = 2;
		gbc.weighty = 2;
		add(gamesLabel, gbc);

		gbc.gridy = 2;
		gbc.weightx = 4;
		gbc.weighty = 4;
		gbc.fill = GridBagConstraints.BOTH;
		add(gamePane, gbc);

		gbc.fill = GridBagConstraints.NONE;
		buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(addGameButton, BorderLayout.WEST);
		buttonPanel.add(editGameButton, BorderLayout.CENTER);
		buttonPanel.add(removeGameButton, BorderLayout.EAST);

		gbc.gridy = 3;
		add(buttonPanel, gbc);

		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.weightx = 2;
		gbc.weighty = 2;
		add(categoriesLabel, gbc);

		gbc.gridy = 2;
		gbc.weightx = 4;
		gbc.weighty = 4;
		gbc.fill = GridBagConstraints.BOTH;
		add(categoryPane, gbc);

		gbc.fill = GridBagConstraints.NONE;
		buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(addCategoryButton, BorderLayout.WEST);
		buttonPanel.add(removeCategoryButton, BorderLayout.EAST);

		gbc.gridy = 3;
		add(buttonPanel, gbc);

		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.weightx = 2;
		gbc.weighty = 2;
		add(companiesLabel, gbc);

		gbc.gridy = 2;
		gbc.weightx = 4;
		gbc.weighty = 4;
		gbc.fill = GridBagConstraints.BOTH;
		add(companyPane, gbc);

		gbc.fill = GridBagConstraints.NONE;
		buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(addCompanyButton, BorderLayout.WEST);
		buttonPanel.add(editCompanyButton, BorderLayout.CENTER);
		buttonPanel.add(removeCompanyButton, BorderLayout.EAST);

		gbc.gridy = 3;
		add(buttonPanel, gbc);

		gbc.gridx = 5;
		gbc.gridy = 1;
		gbc.weightx = 2;
		gbc.weighty = 2;
		add(paymentLabel, gbc);

		gbc.gridy = 2;
		gbc.weightx = 4;
		gbc.weighty = 4;
		gbc.fill = GridBagConstraints.BOTH;
		add(paymentPane, gbc);

		gbc.fill = GridBagConstraints.NONE;
		buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(addPaymentButton, BorderLayout.WEST);
		buttonPanel.add(removePaymentButton, BorderLayout.EAST);

		gbc.gridy = 3;
		add(buttonPanel, gbc);

		gbc.gridx = 6;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		add(exitContainer, gbc);

		setVisible(true);

	}

}
