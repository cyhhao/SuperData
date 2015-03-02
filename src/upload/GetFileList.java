package upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class GetFileList extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetFileList() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		List<String> list=new ArrayList<String>();
		
		String Path = getServletContext().getRealPath("/") + "attached/";
		File file = new File(Path);
		if (file.isDirectory()) 
		{
            System.out.println("ÎÄ¼þ¼Ð");
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++)
            {
                    File readfile = new File(Path + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) 
                    {
                            System.out.println("path=" + readfile.getPath());
                            System.out.println("absolutepath="
                                            + readfile.getAbsolutePath());
                            System.out.println("name=" + readfile.getName());
                            list.add(readfile.getName());
                    } 
                    /*else if (readfile.isDirectory()) {
                            readfile(Path + "\\" + filelist[i]);
                    }*/
            }
		}
		Gson gson=new Gson();
		String s=gson.toJson(list);
		System.out.println(s);
		out.print(s);
		
		out.flush();
		out.close();
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
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
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
