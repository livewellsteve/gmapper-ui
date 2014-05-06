package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//need to fix this
public class MapperDB {

	public static List<String> getExistingAdaptors(int adaptorId){
		List<String> list = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
	
		} catch (ClassNotFoundException e) {
			System.out.println("driver not load");
		}
		Connection conn = null;
		String db = "jdbc:mysql://mydbinstance.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:3306/mydb";
		String user = "cmpe";
		String pass = "password";

		try{
			conn = DriverManager.getConnection(db, user, pass);
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM adaptor_access WHERE adaptor_id="+adaptorId;
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				list.add(rs.getString("endpoint"));
				list.add(rs.getString("db_name"));
				list.add(rs.getString("user"));
				list.add(rs.getString("password"));
				list.add(rs.getString("type"));
				
			}
			if (rs!=null)
				rs.close();
			if (stmt!=null)
				stmt.close();
			if (conn!=null)
				conn.close();
		} catch(SQLException e){
			System.out.println("connection failed");
			e.printStackTrace();
		
		}
		
		
		return list;
	}

	public static Map<Integer,String> getAdaptors(int userId){
		Map<Integer,String> map = new HashMap<Integer,String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
	
		} catch (ClassNotFoundException e) {
			System.out.println("driver not load");
		}
		Connection conn = null;
		String db = "jdbc:mysql://mydbinstance.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:3306/mydb";
		String user = "cmpe";
		String pass = "password";

		try{
			conn = DriverManager.getConnection(db, user, pass);
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM adaptor_access WHERE userid="+userId;
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
	
			map.put(rs.getInt("adaptor_id"), rs.getString("type")+" "+rs.getString("user")+" "+rs.getString("endpoint")+"/"+rs.getString("db_name"));
			}
			if (rs!=null)
				rs.close();
			if (stmt!=null)
				stmt.close();
			if (conn!=null)
				conn.close();
		} catch(SQLException e){
			System.out.println("connection failed");
			e.printStackTrace();
		
		}
		
		
		return map;
	}
	
	public static Map<String,String> getMapperDesc(int userId){
		Map<String,String> map = new HashMap<String,String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
	
		} catch (ClassNotFoundException e) {
			System.out.println("driver not load");
		}
		Connection conn = null;
		String db = "jdbc:mysql://mydbinstance.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:3306/mydb";
		String user = "cmpe";
		String pass = "password";

		try{
			conn = DriverManager.getConnection(db, user, pass);
			Statement stmt = conn.createStatement();
			String query = "SELECT mapper_name,description FROM mapper_config WHERE userid="+userId;
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				map.put(rs.getString("mapper_name"), rs.getString("description"));
			}
			if (rs!=null)
				rs.close();
			if (stmt!=null)
				stmt.close();
			if (conn!=null)
				conn.close();
		} catch(SQLException e){
			System.out.println("connection failed");
			e.printStackTrace();
		
		}
		
		
		return map;
	}
	
	public static int getMapperCount(int userId){
		int count=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
	
		} catch (ClassNotFoundException e) {
			System.out.println("driver not load");
		}
		Connection conn = null;
		String db = "jdbc:mysql://mydbinstance.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:3306/mydb";
		String user = "cmpe";
		String pass = "password";
		
		try{
			conn = DriverManager.getConnection(db, user, pass);
			Statement stmt = conn.createStatement();
			String query = "SELECT COUNT(*) AS count FROM mapper_config WHERE userid="+userId;
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				count = rs.getInt("count");
			}
			
			if (rs!=null)
				rs.close();
			if (stmt!=null)
				stmt.close();
			if (conn!=null)
				conn.close();
	
		} catch(SQLException e){
			System.out.println("connection failed");
			e.printStackTrace();
		
		}
		return count;
	}
}
