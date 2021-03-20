package query_processor_gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class QueryProcessor extends ConnectUI implements ActionListener {
	JFrame frmquery;
	JLabel qry , qryresult , qryres ;
	JTextArea  txtqry , txtqryresult ;
	JButton btnexecute , btnclear , btnoldqry;
	ImageIcon execute ;
	Image image , newimg;
	String QRY , OLDQRY;
	static JTable table;
	static DefaultTableModel tableModel;
	
	
	public QueryProcessor() {
		super();
		frmcon.setVisible(false);
		frmquery = new JFrame("Query Processor");
//		frmquery.setVisible(true);
		frmquery.setSize(1200,750);
		
		Image ticon = Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\athar\\OneDrive\\Desktop\\customization\\Icons\\Numix Circle For Windows\\PNG\\google-sheets.png");
		frmquery.setIconImage(ticon);
		execute = new ImageIcon("C:\\\\Users\\\\athar\\\\OneDrive\\\\Desktop\\\\customization\\\\Icons\\\\Numix Circle For Windows\\\\PNG\\\\glc_player.png");
		image  = execute.getImage(); // transform it 
		newimg = image.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		execute = new ImageIcon(newimg);  // transform it back
		
		
		//Labels
		qry= new JLabel("----------------------------  Enter Query:  ----------------------------");
		qry.setBounds(150,10,500,30);
		qry.setFont(f4);
//		qryresult = new JLabel("");
//		qryresult.setBounds(150, 150,500, 30);
//		qryresult.setFont(f4);
		
		qryres = new JLabel("------------------------  Query Output:  ---------------------------------------------------------------------------------------------------------");
		qryres.setBounds(150, 150,500, 30);
		qryres.setFont(f4);
		frmquery.add(qryres);
		//TExtarea
		txtqry= new JTextArea();
		txtqry.setBounds(30,50, 600, 80);
		txtqry.setBackground(Color.black);
		txtqry.setForeground(Color.green);
		txtqry.setFont(f4);
		frmquery.add(txtqry);
		
		txtqryresult = new JTextArea();
		txtqryresult.setBounds(30,250, 730, 170);
		txtqryresult.setFont(f4);
	//	frmquery.add(txtqryresult);
		

		//Buttons
		btnexecute = new JButton();  //"Execute"
		btnexecute.setIcon(execute);
		btnexecute.setBounds(650, 50, 80,80);
		//btnexecute.setBackground(Color.green);
		frmquery.add(btnexecute);

		btnclear = new JButton("CLEAR");
		btnclear.setBounds(30, 150, 600,30);
		//btnclear.setBackground(Color.YELLOW);
		//btnclear.setOpaque(false);
		frmquery.add(btnclear);
		
		btnoldqry = new JButton("Previous");
		btnoldqry.setBounds(650, 150, 80,30);
		btnoldqry.setBackground(Color.cyan);
		frmquery.add(btnoldqry);
		
		//Adding..
		frmquery.add(qry);
//		frmquery.add(qryresult);
		frmquery.add(qryres);
		
		
	
		
		frmquery.setVisible(true);
		frmquery.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
		btnoldqry.addActionListener(this);
		btnexecute.addActionListener(this);
		btnclear.addActionListener(this);
		
	}// constructor closed
//---------------------------------------------------------------------------------------------------------------------------
	
	
	
	@Override
	public void actionPerformed(ActionEvent aee) {
		Object action = aee.getSource();
		QRY = txtqry.getText();
		//String qry, result ;
		if(action == btnexecute) {
			
		//	System.out.println(OLDQRY);
		//	qry.toLowerCase();
			//if(qry.contains("insert")) {	
			try {
//				if(QRY.contains("*")) {
//					//add dql status button if possible
//					//table.setVisible(true);
//					txtqryresult.setVisible(false);
					fetchTable(QRY);
//				}else {
//					//table.setVisible(false);
//					txtqryresult.setVisible(true);
//					fetch(QRY);
//				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(action == btnoldqry) {
			txtqry.setText(OLDQRY);
			btnoldqry.setBackground(Color.yellow);
		}
		else if(action == btnclear) {
			txtqry.setText(null);
			txtqryresult.setText(null);
		}
		
	}// Action close
	
//=========================================== Logic MEthods========================================
	
	@SuppressWarnings("deprecation")
	public  boolean fetchTable(String QRY) throws ClassNotFoundException{
		try {
			Class.forName(drvIN);
			System.out.println(drvIN);
			Connection conn = DriverManager.getConnection(urlIN, usrIN, pswIN);
			System.err.println("Connected");
//			table.setAutoscrolls(true);
			
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(QRY);

			ResultSetMetaData rsmd = rs.getMetaData();
			
			
			tableModel = new DefaultTableModel();
			int colNo = rsmd.getColumnCount();
			Object[] row = new Object[colNo];
			while(rs.next()) {
				for (int i=0 ; i<colNo ; i++) {
					int x = i+1;
					row[i] = rs.getObject(x);
				}
				tableModel.addRow(row);
			}

	
			table = new JTable();
			JTableHeader header = table.getTableHeader();
			header.setBackground(Color.green);
			header.setBackground(Color.black);
			
			table.setModel(tableModel);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			table.setFillsViewportHeight(true);
			
			
			JScrollPane jsp = new JScrollPane(table);
			jsp.setBounds(10,300,1000,500);
			jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			for (int   i=1 ; i<=colNo  ;  i++) {
				tableModel.addColumn(rsmd.getColumnLabel(i));
			}

//			table.setPreferredScrollableViewportSize();
//			table.setBounds(20, 250, 750, 500);
			
			frmquery.add(jsp);
			rs.close();
			conn.close();
			return true;

		} catch (SQLException se) {
			// TODO Auto-generated catch block
			se.printStackTrace();
//			txtqryresult.setText(se);
			return false;
		}
		
		
	}
	
	//Only for singular queries....
	private void fetch(String qRY2) throws ClassNotFoundException {
		try {
			Class.forName(drvIN);
			System.out.println(drvIN);
			Connection conn = DriverManager.getConnection(urlIN, usrIN, pswIN);
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(QRY);
			 
			rs.next();
			String out = rs.getString(1);
			System.out.println(out);
			txtqryresult.setVisible(true);
			txtqryresult.setText(out);
			
			conn.close();
			
		}catch (SQLException se) {
			// TODO Auto-generated catch block
			se.printStackTrace();
//			txtqryresult.setText(se);
	
		}
		
	}

	//=========================MAIN===============================
	public static void main(String[] args) {
		ConnectUI cnt = new ConnectUI();
//		QueryProcessor qp = new QueryProcessor();
	}
}
