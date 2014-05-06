package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jolbox.bonecp.BoneCPDataSource;

import controller.model.ConfigData;


/**
 * Servlet implementation class LoginControl
 */
@WebServlet(name = "LoginControl", urlPatterns = "/LoginControl")
public class LoginControl extends HttpServlet {
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

		String action = request.getParameter("action");
		if (action.equals("new"))
			request.getRequestDispatcher("createlogin.jsp").forward(request,
					response);
		else
			request.getRequestDispatcher("login.jsp").forward(request,
					response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		Connection conn=null;
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		
	
			try {
				conn = ds.getConnection();
				String sql = "SELECT userid FROM login WHERE user=? and password=?";
				PreparedStatement ps = conn.prepareStatement(sql);
			
				ps.setString(1, user);
				ps.setString(2, password);
				ResultSet rs = ps.executeQuery();
				if (rs.next()){
					//
					//do it in session
					int id = rs.getInt(1);
					
					ConfigData bean = new ConfigData();
					bean.setTurn("source");
					bean.setUserId(id);
					
					HttpSession session = request.getSession();
					session.setAttribute("bean", bean);
					//request.setAttribute("userId", id);
					//request.getRequestDispatcher("/WEB-INF/view/createadaptor.jsp").forward(request, response);
					request.getRequestDispatcher("dashboard.jsp").forward(request, response);
				}
				
				else
					response.sendRedirect("login.jsp");
				
			} catch (SQLException e) {
				out.println("sql exception");
				return;
			}
			
		
		
		if (conn!=null){
		try {
			conn.close();
		} catch (SQLException e) {
			out.println("connection close fail");
		}
		}
		
	}

}
