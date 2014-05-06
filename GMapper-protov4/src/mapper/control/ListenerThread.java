package mapper.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.jolbox.bonecp.BoneCPDataSource;



public class ListenerThread implements Runnable {

	private final int SLEEPTIME = 10000;
	private String fromTableName;
	private String type;
	private String colName;
	private int mapperFieldId;
	private String sql;
	private BoneCPDataSource ds;
	private String pkType;
	private String pkName;
	
	private String action;
	private String targetType;
	private String targetName;
	private String targetObject;
	private String targetPk;
	private String targetPkType;
	private String targetDbType;

	public ListenerThread(String fromTableName, String type, String colName,
			int mapperFieldId, BoneCPDataSource ds,String pkType, String pkName,String action, String targetType, String targetName,String targetObject, String targetPk, String targetPkType, String targetDbType) {
		
		this.fromTableName = fromTableName;
		this.type = type;
		this.colName = colName;
		this.mapperFieldId = mapperFieldId;
		this.ds = ds;
		this.pkType = pkType;
		this.pkName = pkName;
		this.action = action;
		this.targetType = targetType;
		this.targetName = targetName;
		this.targetObject = targetObject;
		this.targetPk = targetPk;
		this.targetPkType = targetPkType;
		this.targetDbType = targetDbType;
		sql = "SELECT trigger_id,table_name,pk_val,time,mapper_field_id FROM "
				+ fromTableName;

	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
		
			try {
				Connection conn = ds.getConnection();
				Statement stmt = conn
						.createStatement(ResultSet.TYPE_FORWARD_ONLY,
								ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					//int triggerId = rs.getInt("trigger_id");
					String tableName = rs.getString("table_name");
					String pkVal = rs.getString("pk_val");
					Date time = rs.getTimestamp("time");
					//int mapperFieldId = rs.getInt("mapper_field_id");

		
					//get col data
					Connection conn2 = ds.getConnection();
					Statement stmt2 = conn2.createStatement();
					String sql2 = "SELECT "+colName+" FROM "+tableName+" WHERE "+pkName+"="+"'"+pkVal+"'";
					ResultSet rs2 = stmt2.executeQuery(sql2);
					String colValue="";
					
					
					
					while(rs2.next()){
						if (type.equalsIgnoreCase("varchar"))
						colValue=rs2.getString(colName);
						else if (type.equals("int"))
							colValue=String.valueOf(rs2.getInt(colName));
						else if (type.equals("double"))
							colValue=String.valueOf(rs2.getDouble(colName));
						else if (type.equals("date"))
							colValue=String.valueOf(rs2.getTime(colName));
					}
					if (rs2!=null)
					rs2.close();
					if (stmt2!=null)
					stmt2.close();
					if (conn2!=null)
					conn2.close();
					
					//place to q
					synchronized(this){
					new ProducerToQ(mapperFieldId,colName,type,colValue,time,action,targetType,targetName,targetObject,targetPk,targetPkType,targetDbType).run();
					}
					
					int rowNum = rs.getRow();
					rs.deleteRow();
					if (rowNum == rs.getRow()) {
						rs.previous();
					}

				}
				if (rs!=null)
				 rs.close();
				if (stmt!=null)
				 stmt.close();
				if (conn!=null)
				 conn.close();
				Thread.sleep(SLEEPTIME);
			} catch (InterruptedException e) {
				System.out.println("thread fail");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("thread query failed");
			}

		}

	}

}
