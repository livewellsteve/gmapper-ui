package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * Servlet implementation class NewLoginControl
 */
@WebServlet(name="NewLoginControl", urlPatterns="/NewLoginControl")
public class NewLoginControl extends HttpServlet {
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
		response.sendRedirect("login.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Connection conn=null;
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		
	
			try {
				conn = ds.getConnection();
				String sql = "INSERT into login (user, password) values(?,?)";
				PreparedStatement ps = conn.prepareStatement(sql);
			
				ps.setString(1, user);
				ps.setString(2, password);
				ps.executeUpdate();
				
				
			} catch (SQLException e) {
				out.println("existing name");
			}
			
		
		
		if (conn!=null){
		try {
			conn.close();
		} catch (SQLException e) {
			out.println("connection close fail");
		}
		}
		
		response.sendRedirect("login.jsp");
	}

}
