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

import assistFunction.Value;

import com.google.gson.Gson;

import Tojson.EbuyChart;

import DataPackage.Data;
import DataPackage.RserveBegin;

public class ebuy extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ebuy() {
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
		RequestDispatcher rd=request.getRequestDispatcher("./ebuy.jsp");
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
		String path=getServletContext().getRealPath("/") + configPath;
		String file=request.getParameter("file");
		System.out.println(file);
		//读文件初始化Data和RData+处理
		RserveBegin Rdata=new RserveBegin();
		//Data data=new Data();
		try 
		{
			//data.Read(path+file,file);
			Rdata.setFileName(path+file, file);
			Rdata.calcStaForEachItem(path+file);
			Rdata.setFileName(Value.scriptRootPath+"output.csv","output.csv");
			Rdata.Apriori();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//一个List<Object>存所以要返回的数据
		List<Object> returnList=new ArrayList<Object>();
		
		//加入返回List中
		Object objLine=EbuyChart.ChartFactorycreater("line",Rdata);
		returnList.add(objLine);
		
		Object objMap=EbuyChart.ChartFactorycreater("map", Rdata);
		returnList.add(objMap);
		
		
		Object objAss=EbuyChart.ChartFactorycreater("Association", Rdata);
		returnList.add(objAss);
		
		Gson gson=new Gson();
		out.print(gson.toJson(returnList));
		System.out.println(gson.toJson(returnList));
		
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
