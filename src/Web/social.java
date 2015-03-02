package Web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import DataPackage.Social;

public class social extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public social() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	static String token="2.001w_8jB0fY3UZ21362af4d2fwf1_B";
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd=request.getRequestDispatcher("./social.jsp");
		rd.forward(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String type=request.getParameter("type");
		
		Social so=new Social(token);
		if(type.equalsIgnoreCase("info"))
		{
			String name1=request.getParameter("leftid");
			String name2=request.getParameter("rightid");
			so.get(name1, name2);
		
			String json=so.tojs();
		
			System.out.println(json);
			out.print(json);
		}
		else if(type.equalsIgnoreCase("myMap"))
		{
			String uid1=request.getParameter("uid1");
			String uid2=request.getParameter("uid2");
			System.out.println(uid1+"|"+uid2);
			String p1=request.getParameter("p1");
			String p2=request.getParameter("p2");
			String str=so.getMap(uid1, uid2, p1, p2);
			System.out.println(str);
			out.print(str);
		}
		else if(type.equalsIgnoreCase("soci"))
		{
			String uid1=request.getParameter("uid1");
			String uid2=request.getParameter("uid2");
			String name1=request.getParameter("name1");
			String name2=request.getParameter("name2");
			System.out.println(uid1+"|"+uid2);
			
			String str=so.getSoci(uid1, uid2, name1, name2);
			System.out.println(str);
			out.print(str);
		}
		
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
