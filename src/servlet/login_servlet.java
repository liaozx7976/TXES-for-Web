package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class login_servlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public login_servlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��ȡ���û����˻�����
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("user_name");//�����û���
		String password = request.getParameter("password");//��������
		String flag = "no";//���ص�״ֵ̬
		System.out.println(name);
		System.out.println(password);	
		
		// ִ�����ݿ�Ĳ�ѯ
		PreparedStatement ps=null;
		Connection conn=null;
		ResultSet rs=null;
		try {
			//��������
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//��������
			conn=DriverManager.getConnection
			//ǰ���ǵ�ַ���м������ݿ⣬�������˺����롣
			("jdbc:sqlserver://(���IP):1433;DatabaseName=emailremind","���ݿ��˺�","����");
			ps=conn.prepareStatement("select * from customer where name=? and password=?");
			ps.setString(1, name);//�����1�����һ���ʺţ��ж�����Ͷ�����֣������Ǳ���
			ps.setString(2, password);
			rs=ps.executeQuery();
				while(rs.next()){
					flag="ok";
				}
			  // ��ɺ�ر�
            rs.close();
            ps.close();
            conn.close();
			
		} catch (Exception e) {
			System.out.print(e.getMessage());	
		}
		
		response.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html;charset=UTF-8");
		//����һ������
		System.out.print(flag);
		if(flag.equals("no")){
			response.sendRedirect("/txes/index.jsp?flag=no");
		}else if(flag.equals("ok")){
			request.getSession().setAttribute("name",name);
			response.sendRedirect("/txes/remind.jsp");
			//request.getRequestDispatcher( "/remind.jsp").forward(request,response);
		}
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
