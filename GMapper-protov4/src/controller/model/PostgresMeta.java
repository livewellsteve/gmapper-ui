package controller.model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostgresMeta {

	private Connection conn;
	private DatabaseMetaData md;

	public PostgresMeta(String db, String user, String password) {
	
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("driver not load");
		}	
		
		try{
			conn = DriverManager.getConnection(db, user, password);
			md = conn.getMetaData();
		} catch(SQLException e){
			System.out.println("connection failed");
		}
	}
	

	public ArrayList<String> getTables(){
		ArrayList<String> list = new ArrayList<String>();
		try{
			ResultSet rs = md.getTables(null, null, "%", new String[] {"TABLE"});
			while(rs.next()){
				//System.out.println(rs.getString(3));
				list.add(rs.getString(3));
			}
			rs.close();
			conn.close();
		} catch (SQLException e1) {	
			System.out.println("query failed from postgres gettable");
		}
	
		return list;
	}
	
	public ArrayList<String> getPk(String objName){
		ArrayList<String> list = new ArrayList<String>();
		try{
			ResultSet rs = md.getPrimaryKeys(null, null, objName);
			while(rs.next()){
				
				list.add(rs.getString(4));
				
			}
			rs.close();
			conn.close();
		} catch (SQLException e1) {	
			System.out.println("query failed");
			
		}
			
		return list;
	}
	public Map<String,String> getFieldsTypes(String objName){
		Map<String,String> map = new HashMap<String,String>();
		try {
		
			ResultSet rs = md.getColumns(null, null, objName, null);
			while(rs.next()){
				//column name 4, type 6
				map.put(rs.getString(4), rs.getString(6));
			}

			rs.close();
			conn.close();
			
		} catch (SQLException e1) {
			System.out.println("query failed from getfields postgres");

		}

		return map;
	}

}
