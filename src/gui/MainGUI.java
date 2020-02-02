package gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.Statement;

public class MainGUI {

	static final String NAME = "Stæm";

	public static Connection conn;
	public static Statement statement;
	public static ResultSet rs;

	static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

	public static int screenWidth = screen.width;
	public static int screenHeight = screen.height;

	public static StartFrame sf;

	public static void main(String[] args) throws SQLException {

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "BEGMYRAT", "murat");
			statement = conn.createStatement();
			// rs = statement.executeQuery("SELECT * FROM USER");

		} catch (ClassNotFoundException e) {

			System.out.println("Connection could not established");

		} catch(SQLRecoverableException e) {
			e.printStackTrace();
		}

		sf = new StartFrame(NAME);

	}

	// A method to scale image
	public static Image getScaledImage(Image image, int width, int height) {

		/*
		 *
		 * Create a BufferedImage container to draw the image into
		 *
		 * Draw the image into it by getting its Graphics2D object and setting
		 * appropriate algorithms to use while drawing it by using RenderingHints
		 *
		 */

		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = resizedImage.createGraphics();

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;

	}

	public ResultSet getRS() {
		return rs;
	}

	public Connection getConnection() {
		return conn;
	}

	public Statement getStatement() {
		return statement;
	}

}
