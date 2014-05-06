package controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * Application Lifecycle Listener implementation class PoolContextListener
 * 
 */
@WebListener
public class PoolContextListener implements ServletContextListener {
	private BoneCPDataSource ds;
	private static final String ATTRIBUTE_NAME="config";

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("driver not load");
		}
		ds = new BoneCPDataSource();
		ds.setJdbcUrl("jdbc:mysql://mydbinstance.cwdrbr1yvdww.us-west-1.rds.amazonaws.com:3306/mydb");
		ds.setUsername("cmpe");
		ds.setPassword("password");
		
		sc.setAttribute(ATTRIBUTE_NAME, ds);
		System.out.println("context listner ds established");

	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if (ds!=null)
		ds.close();
	}
	
//	public BoneCPDataSource getDataSource(){
//		return ds;
//	}
	
//	public static PoolContextListener getInstance(ServletContext sc){
//		return (PoolContextListener) sc.getAttribute(ATTRIBUTE_NAME);
//	}

}
