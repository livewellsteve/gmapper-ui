package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mapper.control.MapperListener;

import pool.ListenerConnPool;
import pool.ListenerThreadPool;

import com.jolbox.bonecp.BoneCPDataSource;

import controller.model.ConfigData;
import controller.model.MysqlAdaptor;
import controller.model.PostgresAdaptor;

/**
 * Servlet implementation class ListenerTriggerControl
 */
@WebServlet(urlPatterns = "/ListenerTriggerControl", name = "ListenerTriggerControl")
public class ListenerTriggerControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	public void init() throws ServletException {
		//runs mapper consumer and producer pair + mapper logic instance
		ListenerThreadPool.runMapperPool();
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("mappers.jsp")
		.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ConfigData bean = (ConfigData) session.getAttribute("bean");
		// create listener table
		// create trigger
		// kicks off producer instance
		// kicks off consumer instance for other end
		
		String action = bean.getAction();
		String tableName = bean.getSourceObject();
		String dbName = bean.getSourceDbName();
		String pkValue = bean.getSourcePk().get(0);
		
		String pkType ="";//dont need pktype now
		//int mapperFieldId = bean.getMapperFieldId();
		int mapperId = bean.getMapperId();
		
		String sourceDb = null;
		String sourceUser = bean.getSourceUser();
		String sourcePass = bean.getSourcePassword();
		
		//for multiple fields later
		String sourceFieldName = null;
		String sourceFieldType = null;
		for (Map.Entry<String, String> entry: bean.getSourceField().entrySet()){
			sourceFieldName = entry.getKey();
			sourceFieldType = entry.getValue();			
		}
		
		String targetDb = null;
		String targetUser = bean.getTargetUser();
		String targetPass = bean.getTargetPassword();
		
		//for multiple fields later
		String targetFieldName = null;
		String targetFieldType = null;
		for (Map.Entry<String, String> entry: bean.getTargetField().entrySet()){
			targetFieldName = entry.getKey();
			targetFieldType = entry.getValue();
		}
		
		String targetObject = bean.getTargetObject();
		//for multipe pks later
		String targetPk = bean.getTargetPk().get(0);
		String targetPkType = ""; //dont need 
		String targetDbType = bean.getTargetType();
		
		String className = null;
		String toTableName = null;
		String classNameFinal = null;
		
	
		if (bean.getSourceType().equals("mysql")) {
			sourceDb = "jdbc:mysql://"+bean.getSourceEndpoint()
					+ "/" + bean.getSourceDbName();
			className ="com.mysql.jdbc.Driver";
					
			MysqlAdaptor mysql = new MysqlAdaptor(sourceDb, sourceUser,
					sourcePass);
		
			toTableName = mysql.createListenerTable();
			
			mysql.createTrigger(action, tableName, dbName, toTableName,
					pkValue,mapperId);
			mysql.closeConn();
			
		
		}
		
		else if(bean.getSourceType().equals("postgres")){
			sourceDb ="jdbc:postgresql://"+bean.getSourceEndpoint()
					+ "/" + bean.getSourceDbName();
			className ="org.postgresql.Driver";
			PostgresAdaptor postgres = new PostgresAdaptor(sourceDb, sourceUser, sourcePass);
			toTableName = postgres.createListenerTable();
			
			postgres.createTrigger(action, tableName, dbName, toTableName, pkValue, mapperId);
			postgres.closeConn();
			
		}
		
		BoneCPDataSource ds = ListenerConnPool.run(className, sourceDb, sourceUser, sourcePass);
		ListenerThreadPool.runListenerPool(toTableName, sourceFieldType, sourceFieldName, mapperId, ds, pkType, pkValue, action, targetFieldType, targetFieldName, targetObject, targetPk,targetPkType,targetDbType);
		
		
		if (bean.getTargetType().equals("mysql")){
			targetDb ="jdbc:mysql://"+bean.getTargetEndpoint()
					+ "/" + bean.getTargetDbName();
			classNameFinal ="com.mysql.jdbc.Driver";
			
			
		}
		else if(bean.getTargetType().equals("postgres")){
			targetDb ="jdbc:postgresql://"+bean.getTargetEndpoint()
					+ "/" + bean.getTargetDbName();
			classNameFinal ="org.postgresql.Driver";
			
		}
		
		BoneCPDataSource dsFinal = ListenerConnPool.run(classNameFinal, targetDb, targetUser, targetPass);
		ListenerThreadPool.runListenerFinalPool(dsFinal);
		
		request.getRequestDispatcher("mappers.jsp")
		.forward(request, response);

	}

}
