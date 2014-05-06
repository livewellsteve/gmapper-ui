package controller.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import controller.Connectionable;

public class MysqlAdaptor implements Connectionable {
Connection conn = null;
	public MysqlAdaptor(String db, String user, String password){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("driver not load");
		}
		try {
			conn = DriverManager.getConnection(db, user, password);
		} catch (SQLException e) {
			System.out.println("connection failed");
		}
	}

	@Override
	public boolean insertUpdateStatement(String sql) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("insertstatment error");
			return false;
		} 
		return true;
	}
	
	@Override
		public void closeConn() {
			if (conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("closing connection error");
				}
			}
		}
	
	public String createListenerTable(){
		String tableName = "gmapper_listen_"+new SimpleDateFormat("yyyyMMddhhmm").format(Calendar.getInstance().getTime());
		try {
			Statement stmt = conn.createStatement();
			String sql = "CREATE TABLE "+tableName+" (" +
					"trigger_id INT NOT NULL AUTO_INCREMENT, "+
					"table_name VARCHAR(255) NULL, "+
					"pk_val VARCHAR(255) NULL, "+
					"time TIMESTAMP NULL, "+
					"mapper_field_id INT NULL, "+
					"PRIMARY KEY(trigger_id)"+
					")";
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			System.out.println("creating listner table error");
		} 
		return tableName;
	}
	
	public boolean createTrigger(String action, String tableName, String dbName, String toTableName, String pkValue, int mapperFieldId){
		try {
		
			String triggerName = "gmapper_listen_"+new SimpleDateFormat("yyyyMMddhhmm").format(Calendar.getInstance().getTime());
			
			Statement stmt = conn.createStatement();
//			String sql1 = "CREATE TRIGGER "+dbName+"."+triggerName +
//					" AFTER "+action+" ON "+tableName+" FOR EACH ROW BEGIN " +
//					"INSERT INTO "+dbName+"."+toTableName+"(table_name,pk_val,time,mapper_field_id) VALUES('"+tableName+"','"+pkValue+"',NOW(),"+mapperFieldId+"); END;";
//			
//			String sql1 = "CREATE TRIGGER "+dbName+"."+triggerName +
//					" AFTER "+action+" ON "+tableName+" FOR EACH ROW BEGIN DECLARE pk_id int; Set pk_id = LAST_INSERT_ID()+1;" +
//					"INSERT INTO "+dbName+"."+toTableName+"(table_name,pk_val,time,mapper_field_id) VALUES('"+tableName+"',pk_id,NOW(),"+mapperFieldId+"); END;";
//		
			String sql1 = "CREATE TRIGGER "+dbName+"."+triggerName +
					" AFTER "+action+" ON "+tableName+" FOR EACH ROW BEGIN " +
					"INSERT INTO "+dbName+"."+toTableName+"(table_name,pk_val,time,mapper_field_id) VALUES('"+tableName+"',NEW."+pkValue+",NOW(),"+mapperFieldId+"); END;";
		
			//System.out.println(sql1);
			stmt.executeUpdate(sql1);

		} catch (SQLException e) {
			System.out.println("creating listner table error");
			return false;
		} 
		return true;
	}

	

}
