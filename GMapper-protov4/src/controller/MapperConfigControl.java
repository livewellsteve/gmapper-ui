package controller;

import java.io.IOException;
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
import controller.model.GetFullAccessName;
import controller.model.MysqlMeta;
import controller.model.PostgresMeta;

/**
 * Servlet implementation class MapperConfigControl
 */
@WebServlet(urlPatterns="/MapperConfigControl", name="MapperConfigControl")
public class MapperConfigControl extends HttpServlet {
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
		
		bean.setSourceObject(request.getParameter("sourceObject"));
		bean.setTargetObject(request.getParameter("targetObject"));
		bean.setMapperName(request.getParameter("mapperName"));
		bean.setDescription(request.getParameter("description"));
		
		
		if (bean.getSourceType().equals("mysql")){
			 bean.setSourcePk(new MysqlMeta(GetFullAccessName.getNameMysql(bean.getSourceEndpoint(), bean.getSourceDbName()),
					bean.getSourceUser(),bean.getSourcePassword()).getPk(bean.getSourceObject()));	
		}
		else if (bean.getSourceType().equals("postgres")){
			bean.setSourcePk(new PostgresMeta(GetFullAccessName.getNamePostgres(bean.getSourceEndpoint(), bean.getSourceDbName()),
					bean.getSourceUser(),bean.getSourcePassword()).getPk(bean.getSourceObject()));
		}

		if (bean.getTargetType().equals("mysql")){
			bean.setTargetPk(new MysqlMeta(GetFullAccessName.getNameMysql(bean.getTargetEndpoint(), bean.getTargetDbName()),
					bean.getTargetUser(),bean.getTargetPassword()).getPk(bean.getTargetObject()));
		}
		else if (bean.getTargetType().equals("postgres")){
			bean.setTargetPk(new PostgresMeta(GetFullAccessName.getNamePostgres(bean.getTargetEndpoint(), bean.getTargetDbName()),
					bean.getTargetUser(),bean.getTargetPassword()).getPk(bean.getTargetObject()));
		}
		
		Connection conn = null;
		//here takes into account for composite primary keys but only working with single pk from on
		//need to work with composite pks; get(0) for single first pk
		
		try{
			conn = ds.getConnection();
			String sql = "INSERT into mapper_config(mapper_name,description,source_adaptor_id,source_object,source_pk,target_adaptor_id,target_object,target_pk,userid) values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getMapperName());
			ps.setString(2, bean.getDescription());
			ps.setInt(3, bean.getSourceAdaptorId());
			ps.setString(4, bean.getSourceObject());
			ps.setString(5, bean.getSourcePk().get(0));
			ps.setInt(6, bean.getTargetAdaptorId());
			ps.setString(7, bean.getTargetObject());
			ps.setString(8, bean.getTargetPk().get(0));
			ps.setInt(9, bean.getUserId());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				bean.setMapperId(rs.getInt(1));
			}
			
		}catch (SQLException e) {
			System.out.println("sql exception from mapper config");
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("connection close fail");
			}
		}
		
		session.setAttribute("bean", bean);
		request.getRequestDispatcher("createfield.jsp").forward(request, response);
		
	}

}
