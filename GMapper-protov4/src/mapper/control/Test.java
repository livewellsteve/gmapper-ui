package mapper.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCPDataSource;
import com.zaxxer.hikari.HikariDataSource;

import pool.ListenerConnPool;
import pool.ListenerThreadPool;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			//Class.forName("org.postgresql.Driver");
//		} catch (ClassNotFoundException e) {
//			System.out.println("driver not load");
//		}
//		Connection conn = null;
		
		///*** these db pass user are adaptors!!!!from ***
		String db = "jdbc:mysql://mydbinstance.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:3306/dummydata";
		String db2post ="jdbc:postgresql://mydbinstancepg1.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:5432/postgres";
		String user = "cmpe";
		String password = "password";
		
//		try{
//			conn = DriverManager.getConnection(db, user, pass);
//		} catch(SQLException e){
//			
//			System.out.println("connection failed");
//			e.printStackTrace();
//			
//		}
	//	Thread t1 = new Thread(new ListenerThread(conn, "gmapper_listen_201404291258", "string", "first_name", 1));
		//ListenerProducer t = new ListenerProducer(conn, "gmapper_listen_201404291258", "string", "first_name", 1);
		//t1.start();

	//HikariDataSource ds = ListenerConnPool.runConnPool("com.mysql.jdbc.jdbc2.optional.MysqlDataSource",db, user, pass);
		
	
		//what initial listner needs
		String className = "com.mysql.jdbc.Driver";
		
		String fromTableName="gmapper_listen_201404300939";
		String type ="string";
		String colName ="first_name";
		int mapper_field_id=16;
		String pkType ="int";
		String pkName ="id_mysql";
		String action="insert";
		String targetType="string";
		String targetName="name";
		String targetObject="aa_table";
		String targetPk="id";
		String targetPkType="int";
		String targetDbType="postgres";
		
		
	//initial listener	
		BoneCPDataSource ds = ListenerConnPool.run(className, db, user, password);
		ListenerThreadPool.runListenerPool(fromTableName, type, colName, mapper_field_id, ds, pkType, pkName, action, targetType, targetName, targetObject, targetPk,targetPkType,targetDbType);
	
		//what final listener needs
		String classNameFinal ="org.postgresql.Driver";
		
		
	//final listner
		BoneCPDataSource dsFinal = ListenerConnPool.run(classNameFinal, db2post, user, password);
		ListenerThreadPool.runListenerFinalPool(dsFinal);
	}

}
