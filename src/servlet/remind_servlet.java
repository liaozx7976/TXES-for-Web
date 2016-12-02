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

public class remind_servlet extends HttpServlet {


	private static final long serialVersionUID = 1L;


	public remind_servlet() {
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
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				String flag = null;
				//�����û���
				String add_name = request.getParameter("remind_name");
				//������������
				String add_type = request.getParameter("remind_type");
				//�������ѱ���
				String add_title = request.getParameter("remind_title");
				//�������ѵ�����
				String add_date = request.getParameter("remind_date");
				//��������ʱ��
				String add_time = request.getParameter("remind_time");
				//������������
				String add_conten = request.getParameter("remind_content");
				String mail_content = request.getParameter("mail_content");//������������			
				String mail_pyte = request.getParameter("mail_pyte");//������������
				String add_mail = mail_content+mail_pyte;
				PrintWriter out = response.getWriter();
				String year = add_date.substring(0,4);
				String month = add_date.substring(5,7);
				String day = add_date.substring(8,10);
				add_time=year+"/"+month+"/"+day +" " + add_time + ":00";
				System.out.println(add_name);
				PreparedStatement ps = null;
				Connection conn=null;
				try{
		            //��������
		            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		            //��������
		            conn=DriverManager.getConnection
		            //ǰ���ǵ�ַ���м������ݿ⣬�������˺����롣
		            ("jdbc:sqlserver://(���IP):1433;DatabaseName=emailremind","���ݿ��˺�","����");
		            ps=conn.prepareStatement("EXEC add_remind ?,?,?,?,?,?,?");
		            //ps=conn.prepareStatement("EXEC add_remind 'tomxin','456','�𴲿��鿩','Ҫ������','2016/8/26 12:05:15' ,'865498311@163.com','no'");
		            ps.setString(1, add_name);//�����1�����һ���ʺţ��ж�����Ͷ�����֣������Ǳ���
		            ps.setString(2, add_type);
		            ps.setString(3, add_title);
		            ps.setString(4, add_conten);
		            ps.setString(5, add_time);
		            ps.setString(6, add_mail);
		            ps.setString(7, "no");
		            ps.executeQuery();
				}catch (Exception e) {
					String return_Str = e.getMessage();
					if(return_Str.equals("�����û�з��ؽ������")){
						return_Str = "�����Ѿ���ӳɹ�";
					}
					flag=return_Str;
				}finally{
					  // ��ɺ�ر�
						try {
							ps.close();
							ps.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
				}
			
				System.out.print(flag);
				if(flag.equals("�����Ѿ���ӳɹ�")){
					response.sendRedirect("/txes/remind.jsp?flag=ok");
				}
}


	public void init() throws ServletException {
		// Put your code here
	}

}
