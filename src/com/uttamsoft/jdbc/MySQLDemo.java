package com.uttamsoft.jdbc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MySQLDemo {

	public static void main(String[] args) throws Exception
	{
		//load driver
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/uttamdb", "root", "rwmtgb649");
		
		Statement st=con.createStatement();
		
		//dynamic table name
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the Table Name :- ");
		
		String tname = br.readLine();
		String query = "create table " +tname +" (ENO int(3) primary key, ENAME varchar(5), ESAL float(5))"; //execute SQL command
		
		st.executeUpdate(query);
		
		System.out.println("Employee table created successfully");
		
		st.close();
		con.close();
	}

}
