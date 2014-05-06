package controller.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import controller.Connectionable;

public class PostgresAdaptor implements Connectionable {
	Connection conn = null;

	public PostgresAdaptor(String db, String user, String password) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("driver not load");
		}
		try {
			conn = DriverManager.getConnection(db, user, password);
		} catch (SQLException e) {
			System.out.println("connection failed");
		}
	}

	// need create listnertable and trigger

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
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("closing connection error");
			}
		}
	}

	public String createListenerTable() {
		String tableName = "gmapper_listen_"
				+ new SimpleDateFormat("yyyyMMddhhmm").format(Calendar
						.getInstance().getTime());
		try {
			Statement stmt = conn.createStatement();
			String sql = "CREATE TABLE " + tableName + " ("
					+ "trigger_id serial NOT NULL, "
					+ "table_name character varying(255), "
					+ "pk_val character varying(255), "
					+ "time timestamp without time zone, mapper_field_id integer" + "CONSTRAINT "
					+ tableName + "_pkey PRIMARY KEY (trigger_id)" + ")";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("creating listner table error");
		}
		return tableName;
	}

	public boolean createTrigger(String action, String tableName,
			String dbName, String toTableName, String pkValue, int mapperFieldId) {
		try {
			
			String triggerName = "gmapper_trig_"
					+ new SimpleDateFormat("yyyyMMddhhmm").format(Calendar
							.getInstance().getTime());
			String functionName = "gmapper_func_"
					+ new SimpleDateFormat("yyyyMMddhhmm").format(Calendar
							.getInstance().getTime());

			Statement stmt = conn.createStatement();

			String sql3 = "CREATE FUNCTION " + functionName
					+ "() RETURNS TRIGGER AS $_$ " + "BEGIN " + "INSERT INTO "
					+ toTableName + "(table_name,pk_val,time,mapper_field_id) VALUES('"
					+ tableName + "',NEW." + pkValue + ",NOW()"+mapperFieldId+");"
					+ "return OLD;" + "END $_$ LANGUAGE 'plpgsql'; ";

			String sql4 = "CREATE TRIGGER " + triggerName + " AFTER "+action+" ON "
					+ tableName + " FOR EACH ROW EXECUTE PROCEDURE "
					+ functionName + "();";

			stmt.executeUpdate(sql3);
			stmt.executeUpdate(sql4);
		} catch (SQLException e) {
			System.out.println("creating listner table error");
			return false;
		}
		return true;
	}

}
