package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jolbox.bonecp.BoneCPDataSource;

import controller.model.ConfigData;

/**
 * Servlet implementation class AdaptorAccessConfig
 */
@WebServlet(urlPatterns = "/AdaptorAccessConfig", name = "AdaptorAccessConfig")
public class AdaptorAccessConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoneCPDataSource ds;

	@Override
	public void init() throws ServletException {
		ds = (BoneCPDataSource) getServletContext().getAttribute("config");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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
		String[] endpoint = new String[2];
		String[] db = new String[2];
		String[] user = new String[2];
		String[] password = new String[2];
		String[] type = new String[2];

		if (request.getParameter("userS").isEmpty()) {
			int existingAdaptorSourceId = Integer.parseInt(request
					.getParameter("existingAdaptorSource"));
			List<String> dbAccessSource = MapperDB
					.getExistingAdaptors(existingAdaptorSourceId);
			endpoint[0] = dbAccessSource.get(0);
			db[0] = dbAccessSource.get(1);
			user[0] = dbAccessSource.get(2);
			password[0] = dbAccessSource.get(3);
			type[0] = dbAccessSource.get(4);
			
			bean.setSourceAdaptorId(existingAdaptorSourceId);
			bean.setSourceType(type[0]);
		}

		else if (!request.getParameter("userS").isEmpty()) {
			endpoint[0] = request.getParameter("endpointS");
			db[0] = request.getParameter("dbS");
			user[0] = request.getParameter("userS");
			password[0] = request.getParameter("passwordS");
			type[0] = request.getParameter("typeS");
			
			bean.setSourceType(type[0]);
		}

		if (request.getParameter("userT").isEmpty()) {
			int existingAdaptorTargetId = Integer.parseInt(request
					.getParameter("existingAdaptorTarget"));
			List<String> dbAccessTarget = MapperDB
					.getExistingAdaptors(existingAdaptorTargetId);
			endpoint[1] = dbAccessTarget.get(0);
			db[1] = dbAccessTarget.get(1);
			user[1] = dbAccessTarget.get(2);
			password[1] = dbAccessTarget.get(3);
			type[1] = dbAccessTarget.get(4);
			bean.setTargetAdaptorId(existingAdaptorTargetId);
			bean.setTargetType(type[1]);
			
		} else if (!request.getParameter("userT").isEmpty()) {
			endpoint[1] = request.getParameter("endpointT");
			db[1] = request.getParameter("dbT");
			user[1] = request.getParameter("userT");
			password[1] = request.getParameter("passwordT");
			type[1] = request.getParameter("typeT");
			bean.setTargetType(type[1]);
		}
		
		
		bean.setSourceEndpoint(endpoint[0]);
		bean.setSourceDbName(db[0]);
		bean.setSourceUser(user[0]);
		bean.setSourcePassword(password[0]);
		bean.setSourceType(type[0]);
		bean.setTargetEndpoint(endpoint[1]);
		bean.setTargetDbName(db[1]);
		bean.setTargetUser(user[1]);
		bean.setTargetPassword(password[1]);
		bean.setTargetType(type[1]);
		
		Connection conn = null;
		int key = -1;

		//need fix
		try {
			conn = ds.getConnection();
			if (!request.getParameter("userS").isEmpty()) {
				
				String sql = "INSERT into adaptor_access(endpoint,db_name,user,password,userid,type) values(?,?,?,?,?,?)";
				int i=0; 
					PreparedStatement ps = conn.prepareStatement(sql);

					ps.setString(1, endpoint[i]);
					ps.setString(2, db[i]);
					ps.setString(3, user[i]);
					ps.setString(4, password[i]);

					int id = bean.getUserId();

					ps.setInt(5, id);
					ps.setString(6, type[i]);
					ps.executeUpdate();

					ResultSet rs = ps.getGeneratedKeys();

					if (rs.next()) {
						key = rs.getInt(1);
					}

					bean.setSourceAdaptorId(key);
			}
			
			if (!request.getParameter("userT").isEmpty()){

				String sql = "INSERT into adaptor_access(endpoint,db_name,user,password,userid,type) values(?,?,?,?,?,?)";
				int i=1; 
					PreparedStatement ps = conn.prepareStatement(sql);

					ps.setString(1, endpoint[i]);
					ps.setString(2, db[i]);
					ps.setString(3, user[i]);
					ps.setString(4, password[i]);

					int id = bean.getUserId();

					ps.setInt(5, id);
					ps.setString(6, type[i]);
					ps.executeUpdate();

					ResultSet rs = ps.getGeneratedKeys();

					if (rs.next()) {
						key = rs.getInt(1);
					}

					bean.setSourceAdaptorId(key);

			}
		

		} catch (SQLException e) {
			System.out.println("sql failed");
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("connection close fail");
			}
		}

		session.setAttribute("bean", bean);
		 request.getRequestDispatcher("createobject.jsp")
		 .forward(request, response);

	}

}
