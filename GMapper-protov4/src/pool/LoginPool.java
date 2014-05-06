package pool;

import com.jolbox.bonecp.BoneCPDataSource;

public class LoginPool {
public static BoneCPDataSource run(){
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		System.out.println("driver not load");
	}
	
	BoneCPDataSource ds = new BoneCPDataSource();
	ds.setJdbcUrl("jdbc:mysql://mydbinstance.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:3306/mydb");
	ds.setUsername("cmpe");
	ds.setPassword("password");
	
	return ds;
	}
}
