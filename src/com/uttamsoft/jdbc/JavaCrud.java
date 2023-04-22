package com.uttamsoft.jdbc;

import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaCrud {

	private JFrame frame;
	private JTextField txtName;
	private JTextField txtEdition;
	private JTextField txtPrice;
	private JTable table;
	private JTextField txtId;
	private JButton btnSave, btnUpdate, btnClear, btnDelete, btnExit;
	private JScrollPane scrollPane;
	Connection con;
	Statement st;
	ResultSet rs;
	String tableName;
	DefaultTableModel model;

	public static void main(String[] args)  throws Exception{

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JavaCrud() {
		tableName = "demo";
		try {
			Connect();
		} catch (Exception e) {e.printStackTrace();}
		initialize();
	}

	public void Connect() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/uttamdb", "root", "rwmtgb649");
		//con=DriverManager.getConnection("byiig0vngbvcenz9feed-mysql.services.clever-cloud.com", "upw0dxpo8jbhxutr", "5sbWqBapPMO5lAaznMG6");
		st=con.createStatement();
	}

	private void initialize() {
		machine_init();

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String edition = txtEdition.getText();
				String price = txtPrice.getText();
				if (isValid(name,edition,price)) {
					String query = String.format("insert into " + tableName + " (name, edition, price) values (\"%s\", %s, %s);", name, edition, price); //execute SQL command
					runSQL(query);
				}
			}
		});


		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		

		model = new DefaultTableModel(new String[]{"ID", "Book Name", "Edition", "Price"}, 0);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		loadTable();
		txtId.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String id = txtId.getText();
				if (!idIsValid(id)) return;
				String query = "select name, edition, price from " + tableName + " where id = " + id + ";";
				runSQL(query);
				try {
					if (rs.next()) {
						System.out.println("search result found");
						txtName.setText(rs.getString("name"));
						txtEdition.setText(rs.getString("edition"));
						txtPrice.setText(rs.getString("price"));
					}
					else {
						System.out.println("search result not found");
					}
				} catch (SQLException e1) {e1.printStackTrace();}
			}
		});
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtId.getText();
				if (!idIsValid(id)) return;
				String name = txtName.getText();
				String edition = txtEdition.getText();
				String price = txtPrice.getText();
				String query = String.format("update " + tableName + " set name=\"%s\", edition=%s, price=%s where id = %s;", name, edition, price, id);
				runSQL(query);
			}});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtId.getText();
				if (!idIsValid(id)) return;
				String query = String.format("delete from " + tableName + " where id = %s;", id);
				runSQL(query);
			}
		});
		
	}
	
	void machine_init() {
		frame = new JFrame();
		frame.setBounds(100, 100, 784, 466);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 33));
		lblNewLabel.setBounds(257, 0, 232, 72);
		frame.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 91, 356, 180);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(32, 35, 109, 30);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(32, 76, 109, 30);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(32, 121, 109, 30);
		panel.add(lblNewLabel_1_1_1);

		txtName = new JTextField();
		txtName.setBounds(138, 42, 169, 20);
		panel.add(txtName);
		txtName.setColumns(10);

		txtEdition = new JTextField();
		txtEdition.setColumns(10);
		txtEdition.setBounds(138, 83, 169, 20);
		panel.add(txtEdition);

		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(138, 128, 169, 20);
		panel.add(txtPrice);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(24, 296, 89, 23);
		frame.getContentPane().add(btnSave);

		btnExit = new JButton("Exit");
		btnExit.setBounds(139, 296, 89, 23);
		frame.getContentPane().add(btnExit);

		btnClear = new JButton("Clear");
		btnClear.setBounds(260, 296, 89, 23);
		frame.getContentPane().add(btnClear);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(388, 83, 340, 236);
		frame.getContentPane().add(scrollPane);


		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(24, 344, 342, 72);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1_1_2 = new JLabel("Book ID");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_2.setBounds(37, 31, 109, 30);
		panel_1.add(lblNewLabel_1_1_2);

		txtId = new JTextField();
		txtId.setColumns(10);
		txtId.setBounds(143, 38, 169, 20);
		panel_1.add(txtId);

		btnUpdate = new JButton("Update");
		btnUpdate.setBounds(518, 335, 89, 23);
		frame.getContentPane().add(btnUpdate);

		btnDelete = new JButton("Delete");
		btnDelete.setBounds(639, 335, 89, 23);
		frame.getContentPane().add(btnDelete);
	}

	void clear() {
		txtEdition.setText("");
		txtId.setText("");
		txtName.setText("");
		txtPrice.setText("");
	}

	boolean isValid(String name, String edition, String price) {
		if (name.length() < 1) return false;
		if (edition.length() < 1 || !edition.matches("\\d+")) return false;
		if (price.length() < 1 || !price.matches("\\d+")) return false;
		return true;
	}

	boolean idIsValid(String id) {
		if (id.length() < 1 || !id.matches("\\d+")) {
			System.out.println("invalid id");
			return false;
		}
		return true;
	}

	void loadTable() {
		model.setRowCount(0); //clear the table
		runSQL("select * from " + tableName + " ");
		try {
			while(rs.next())
				model.addRow(new Object[]{rs.getString("id"),rs.getString("name"), rs.getString("edition"), rs.getString("price")});

			System.out.println("successfully imported mySQL table to JTable");
		} catch (SQLException e) {e.printStackTrace(); System.err.println("B rs.next() failed");}
	}

	void runSQL(String query) {
		if (query.indexOf("select") == 0) {
			try {
				rs = st.executeQuery(query);
				System.out.println(query + " was successful");
			} catch (SQLException e) {e.printStackTrace(); System.err.println(query + " failed");}
		}
		else {
			try {
				st.executeUpdate(query);
				System.out.println(query + " was successful");
				clear();
				loadTable();
			} catch (SQLException e) {e.printStackTrace(); System.err.println(query + " failed");}
		}


	}
}
