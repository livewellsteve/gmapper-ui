package controller;

import java.sql.Connection;
import java.sql.ResultSet;



public interface Connectionable {

	
	boolean insertUpdateStatement(String sql);
	void closeConn();
	
	//read and delete needs preparedstatment
//	ResultSet read(Connection conn, String query);
//	boolean delete(Connection conn, String sql);
}
