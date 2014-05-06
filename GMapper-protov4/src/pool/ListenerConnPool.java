package pool;

import com.jolbox.bonecp.BoneCPDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ListenerConnPool {


	
	public static BoneCPDataSource run(String className,String db, String user,String password){
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.println("driver not load");
		}
		
		BoneCPDataSource ds = new BoneCPDataSource();
		ds.setJdbcUrl(db);
		ds.setMinConnectionsPerPartition(2);
		ds.setMaxConnectionsPerPartition(100);
		ds.setPartitionCount(2);
		ds.setUsername(user);
		ds.setPassword(password);
		
		return ds;
		}
}
