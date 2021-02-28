package query_processor_gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class QueryProcessor extends ConnectUI implements ActionListener {
	JFrame query;
	JLabel qry , qryresult ;
	JTextArea  txtqry , txtqryresult ;
	JButton btnexecute , btnclear;
	
	
	public QueryProcessor() {
		//super();
		query = new JFrame("Query Processor");
		query.setVisible(true);
		query.setSize(800,500);
		
		//LAbels
		qry= new JLabel("----------------------------  Enter Query:  ----------------------------");
		qry.setBounds(10,10,500,30);
	//	qry.setHorizontalAlignment(qry.CENTER);
		qryresult = new JLabel("----------------------------  Query Output:  ----------------------------");
		qryresult.setBounds(10, 250,500, 30);
	//	qryresult.setHorizontalAlignment(qryresult.CENTER);
		
		//TExtarea
		txtqry= new JTextArea();
		txtqry.setBounds(20,50, 600, 100);
		query.add(txtqry);
		
		txtqryresult = new JTextArea();
		txtqryresult.setBounds(20,250, 740, 170);
		query.add(txtqryresult);
		
		//Buttons
		btnexecute = new JButton("Execute");
		btnexecute.setBounds(700, 20, 80,30);
		query.add(btnexecute);
		
		btnclear = new JButton("Clear");
		btnclear.setBounds(700, 60, 80,30);
		query.add(btnclear);
		
		//Adding..
		query.add(qry);
		query.add(qryresult);
		query.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		btnexecute.addActionListener(this);
		btnclear.addActionListener(this);
		
	}
	
	@SuppressWarnings("deprecation")
	public  void fetch(String qry) throws SQLException {
		
		String drv ,  url , usr , psw;
		drv = txtdriver.getText();
		url = txturl.getText();
		usr = txtusername.getText();
		psw = txtpassword.getText();
		try {
			Class.forName(drv);
			Connection conn = DriverManager.getConnection(url, usr, psw);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry);
			
			String out ;
			out = rs.getString(2);
			
			txtqryresult.setText(out);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent aee) {
		Object action = aee.getSource();
		
		String qry, result ;
		
		if(action == btnexecute) {
			qry = txtqry.getText();
			qry.toLowerCase();
			//if(qry.contains("insert")) {	
			try {
				fetch(qry);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(action == btnclear) {
			txtqry.setText(null);
			txtqryresult.setText(null);
		}
		
		
	}
	
	
	public static void main(String[] args) {
		ConnectUI cnt = new ConnectUI();
		
		//QueryProcessor qp = new QueryProcessor();
	}
	
	
}
