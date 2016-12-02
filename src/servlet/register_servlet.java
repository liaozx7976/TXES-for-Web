package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class register_servlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public register_servlet() {
		super();
	}

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
		String flag = null;
		String regiter_name = request.getParameter("register_user_name");//����ע���û���
		String regiter_password = request.getParameter("register_password");//����ע������
		PreparedStatement ps = null;
		Connection conn=null;
		try{
            //��������
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //��������
            conn=DriverManager.getConnection
            //ǰ���ǵ�ַ���м������ݿ⣬�������˺����롣
	    ("jdbc:sqlserver://(���IP):1433;DatabaseName=emailremind","���ݿ��˺�","����");
            ps=conn.prepareStatement("EXEC add_user ?,?");
            ps.setString(1, regiter_name);//�����1�����һ���ʺţ��ж�����Ͷ�����֣������Ǳ���
            ps.setString(2, regiter_password);
            ps.executeQuery();
		}catch (Exception e) {
			String return_Str = e.getMessage();
			System.out.print(return_Str);
			flag=return_Str;
			//����ps.executeQuery();��ʱ�򣬿϶��ᱨ��Ȼ�����catch��������2������
			if(return_Str.equals("�û����Ѿ����ڣ������ע������һ�����")){
				flag = return_Str;
			}
			//��仰����˼�ʹ���û�з���ֵ��ע��ɹ���
			if(return_Str.equals("�����û�з��ؽ������")){
				flag = "ok";
			}
		}finally{
			  // ��ɺ�ر�
				try {
					ps.close();
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		response.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html;charset=UTF-8");
		//����һ������
		PrintWriter out = response.getWriter();
		
		System.out.print(flag);
		//�ж��û����Ƿ��Ѿ�����
		if(flag.equals("�û����Ѿ����ڣ������ע������һ�����")){
			response.sendRedirect("/txes/register.jsp?flag=already");
		}
		//�ɹ�ע�ᣬ���ص�¼ҳ��
		else if(flag.equals("ok")){
			response.sendRedirect("/txes/index.jsp?flag=ok");
		}
		else{
			response.sendRedirect("/txes/register.jsp?flag=error");
		}
}


	public void init() throws ServletException {
		// Put your code here
	}

}
