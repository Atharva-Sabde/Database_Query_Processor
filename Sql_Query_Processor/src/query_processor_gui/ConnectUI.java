package query_processor_gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ConnectUI extends JFrame implements ActionListener {
	JFrame frmcon;
	JPanel conpanel;
	JLabel description, db, driver, url, username, password, prt1, prt2, test ,label, welcome;
	public JTextField txtdescription, txtdriver, txturl, txtusername;
	JPasswordField txtpassword;
	JComboBox<String> drpdriver;
	JButton btnconnect, btncancel, btnreset;
	Font f1, f2, f3;
	ImageIcon icon;
	public ConnectUI() {
		frmcon = new JFrame();
		frmcon.setTitle("Connection Prompt...");
		// frmcon.setLayout(new GridLayout(5,5));
		frmcon.setVisible(true);
		frmcon.setSize(800, 500);
		//frmcon.getContentPane().setBackground(Color.LIGHT_GRAY);
		
		f1 = new Font("Arial", Font.BOLD, 20);
		f2 = new Font("Arial", Font.BOLD, 16);
		f3 = new Font("Arial", Font.ITALIC, 14);

		//welcome page
		icon = new ImageIcon(
				"C:\\Users\\athar\\OneDrive\\Desktop\\customization\\Icons\\Numix Circle For Windows\\PNG\\oracle-sqldeveloper.png");
		welcome = new JLabel("We <3 Databases!");
		welcome.setBounds(310, 50, 500, 30);
		welcome.setFont(f1);
		//welcome.setForeground(Color.BLACK);
		frmcon.add(welcome);
		label = new JLabel(icon);
		label.setSize(800, 500);
	//	label.setBounds(x, y, width, height);
		label.setVisible(true);
		frmcon.add(label);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		label.setVisible(false);
		welcome.setVisible(false);
		// frmcon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frmcon.pack();
		// frmcon.setForeground(Color.green);

		
		// description = new JLabel("Enter your DataBase Details...");
		description = new JLabel("Database Query Processor");
		description.setBounds(260, 5, 500, 30);
		// description.setHorizontalAlignment(description.LEFT);
		description.setFont(f1);
		description.setForeground(Color.RED);

		prt1 = new JLabel(
				"----------------------------------------   DRIVER INFO   ----------------------------------------");
		prt1.setBounds(100, 50, 600, 20);
		prt1.setHorizontalAlignment(prt1.CENTER);
		prt1.setFont(f3);
		prt2 = new JLabel(
				"-------------------------------------   AUTHENTICATION   -------------------------------------");
		prt2.setBounds(100, 255, 600, 20);
		prt2.setHorizontalAlignment(prt2.CENTER);
		prt2.setFont(f3);

		// LABELS
		db = new JLabel("Database:");
		db.setBounds(300, 85, 150, 30);
		db.setFont(f2);

		driver = new JLabel("Driver     :");
		driver.setBounds(100, 120, 100, 30);
		driver.setFont(f2);

		url = new JLabel("URL        :");
		url.setBounds(100, 160, 100, 30);
		url.setFont(f2);

		username = new JLabel("Username   :");
		username.setBounds(220, 290, 100, 30);
		username.setFont(f2);

		password = new JLabel("Password   :");
		password.setBounds(220, 330, 100, 30);
		password.setFont(f2);

		// ComboBox
		String[] input = { "Oracle", "MySql", "MS Sql" };
		drpdriver = new JComboBox(input);
		drpdriver.setBounds(390, 85, 80, 30);
		drpdriver.setFont(f2);
		// drpdriver.setBounds(x, y, width, height);

		// TEXTFIELDS
		txtdriver = new JTextField();
		txtdriver.setFont(f2);
		txtdriver.setBounds(180, 120, 500, 30);

		txturl = new JTextField();
		txturl.setFont(f2);
		txturl.setBounds(180, 160, 500, 30);

		txtusername = new JTextField();
		txtusername.setBounds(325, 290, 250, 30);
		txtusername.setFont(f2);

		txtpassword = new JPasswordField();
		txtpassword.setBounds(325, 330, 250, 30);
		txtpassword.setFont(f2);

		// BUTTONS
		btnconnect = new JButton("Connect");
		btnconnect.setFont(f2);
		btnconnect.setBounds(250, 390, 130, 40);
		btnconnect.setBackground(Color.CYAN);

		btnreset = new JButton("Reset");
		btnreset.setFont(f2);
		// btnreset.setBounds(360, 390,120,40);
		btnreset.setBounds(330, 200, 130, 30);
		btnreset.setBackground(Color.orange);

		btncancel = new JButton("Cancel");
		btncancel.setBounds(420, 390, 120, 40);
		btncancel.setFont(f2);
		btncancel.setBackground(Color.PINK);

		// adding
		frmcon.add(btnconnect);
		frmcon.add(btnreset);
		frmcon.add(btncancel);

		frmcon.add(description);
		frmcon.add(prt1);
		frmcon.add(prt2);

		frmcon.add(db);
		frmcon.add(driver);
		frmcon.add(url);
		frmcon.add(username);
		frmcon.add(password);

		frmcon.add(drpdriver);
		frmcon.add(txtdriver);
		frmcon.add(txturl);
		frmcon.add(txtusername);
		frmcon.add(txtpassword);
		frmcon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// String drv , url, usr, psw;

		test = new JLabel();
		test.setBounds(10, 10, 100, 30);
		// test.setText(url);
		frmcon.add(test);
		
		frmcon.setVisible(true);
		
		drpdriver.addActionListener(this);
		btnreset.addActionListener(this);
		btnconnect.addActionListener(this);
		btncancel.addActionListener(this);

	}

	public static boolean connect(String drv, String url, String usr, String psw) {
		try {
			Class.forName(drv);

			Connection conn = DriverManager.getConnection(url, usr, psw);
			conn.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}// method close

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object action = ae.getSource();

		System.out.println(drpdriver.getSelectedItem());

		String drv, url, usr, psw;
		drv = txtdriver.getText();
		url = txturl.getText();
		usr = txtusername.getText();
		psw = txtpassword.getText();

		if (action == btnconnect) {
			boolean valid;
			valid = connect(drv, url, usr, psw);
			if (valid) {
				// test.setText("Success!");
				JOptionPane.showMessageDialog(frmcon, "Database Connected!");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				QueryProcessor qp = new QueryProcessor();
				frmcon.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(frmcon, "Database Connection Failed");
			}
		} else if (action == btnreset) {
			txtdriver.setText(null);
			txturl.setText(null);
			txtusername.setText(null);
			txtpassword.setText(null);
		} else if (action == btncancel) {
			// frmcon.setDefaultCloseOperation(1);
			frmcon.dispose();
			// frmcon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}
	}
}
