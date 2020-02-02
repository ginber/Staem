// User class - to hold information about user

package gui;

import java.sql.Date;

public class User {

	private String name, mail, password, gender, dob;
	private int wallet,ID;
	

	public User(String name, String mail, String password, String gender, int wallet, String dob,int ID) {
		super();
		this.name = name;
		this.mail = mail;
		this.password = password;
		this.gender = gender;
		this.wallet = wallet;
		this.dob = dob;
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getWallet() {
		return wallet;
	}

	public void setWallet(int wallet) {
		this.wallet = wallet;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getDob() {
		return dob;
	}

	public void getDob(String dob) {
		this.dob = dob;
	}

}
