package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jolbox.bonecp.BoneCPDataSource;

import controller.model.ConfigData;

/**
 * Servlet implementation class FieldControl
 */
@WebServlet(urlPatterns="/FieldControl", name="FieldControl")
public class FieldControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoneCPDataSource ds;

	@Override
	public void init() throws ServletException {
		ds = (BoneCPDataSource) getServletContext().getAttribute("config");
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("mappers.jsp")
		.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ConfigData bean = (ConfigData) session.getAttribute("bean");
		bean.setAction(request.getParameter("action"));
		
		String sourceFieldString = request.getParameter("sourceField");
		String targetFieldString = request.getParameter("targetField");
		
		Map<String, String> mapSource = bean.getSourceField();
		Map<String, String> mapTarget = bean.getTargetField();
		
		String sourceFieldType = mapSource.get(sourceFieldString);
		String targetFieldType = mapTarget.get(targetFieldString);
		
		mapSource.clear();
		mapTarget.clear();
		
		mapSource.put(sourceFieldString, sourceFieldType);
		mapTarget.put(targetFieldString, targetFieldType);
		
//		for (Map.Entry<String,String> entry: mapSource.entrySet()){
//			System.out.println(entry.getKey()+" "+entry.getValue());
//		}
//		for (Map.Entry<String,String> entry: mapTarget.entrySet()){
//			System.out.println(entry.getKey()+" "+entry.getValue());
//		}
		
		Connection conn = null;
		try{
			conn = ds.getConnection();
			String sql = "INSERT into mapper_field_config(mapper_id,action,source_field_name,source_field_type,target_field_name,target_field_type) values(?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, bean.getMapperId());
			ps.setString(2, bean.getAction());
			
			ps.setString(3, sourceFieldString);
			ps.setString(4, sourceFieldType);
			ps.setString(5, targetFieldString);
			ps.setString(6, targetFieldType);
			
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				bean.setMapperFieldId((rs.getInt(1)));
			}
			
		}catch (SQLException e) {
			System.out.println("sql exception from mapper field config");
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("connection close fail");
			}
		}
		
		session.setAttribute("bean", bean);
		//request.getRequestDispatcher("/ListenerTriggerControl").forward(request, response);
		
		String submitType = request.getParameter("submitType");
		if (submitType.equalsIgnoreCase("complete")){
			request.getRequestDispatcher("/ListenerTriggerControl").forward(request, response);
		}
		else if (submitType.equalsIgnoreCase("repeat")){
			request.getRequestDispatcher("createfield.jsp").forward(request, response);
			
		}
	}

}
