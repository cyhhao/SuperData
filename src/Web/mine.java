package Web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import DataPackage.Data;
import DataPackage.Data.Association;
import DataPackage.RserveBegin;
import Tojson.ChartFactory;

public class mine extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public mine() {
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd=request.getRequestDispatcher("./mine.jsp");
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
	protected String configPath = "attached/";
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String filename=request.getParameter("filename");
		//String chart=request.getParameter("chart");
		String minetype=request.getParameter("mine");
		
		String path=getServletContext().getRealPath("/") + configPath;
		System.out.println(filename+"|"+minetype);
		
		System.out.println("***$$*************************");
		try 
		{
			Gson gson=new Gson();
			if(minetype.equalsIgnoreCase("cluster")) 
			{
				Data data=new Data();
				RserveBegin Rdata=new RserveBegin();
				
				data.Read(path+filename, filename);
				Rdata.setFileName(path+filename, filename);
				Rdata.getCluster();
				
				List<Object> objlist=new ArrayList<Object>();
				Object obj1=ChartFactory.Minecreator("scatter", data,Rdata);
				Object obj2=ChartFactory.Minecreator("pie", data,Rdata);
				objlist.add(obj2);
				objlist.add(obj1);
				
				System.out.println(gson.toJson(objlist));
				out.print(gson.toJson(objlist));
				
			}
			else if(minetype.equalsIgnoreCase("wordcloud"))
			{
				RserveBegin Rdata=new RserveBegin();
				Rdata.setFileName(path+filename, filename);
				System.out.println(path);
				System.out.println(filename);
				Rdata.callRScript();
				
				System.out.println(gson.toJson(Rdata.sortList));
				out.print(gson.toJson(Rdata.sortList));
			}
			else if(minetype.equalsIgnoreCase("apriori"))
			{
				RserveBegin Rdata=new RserveBegin();
				Rdata.setFileName(path+filename, filename);
				Rdata.Apriori();
				
				Tojson.Association ass=new Tojson.Association(Rdata);
				
				System.out.println(gson.toJson(ass));
				out.print(gson.toJson(ass));
			}
			
			
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
