package Web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import assistFunction.HttpTookit;

public class postHelp extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public postHelp() {
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
		
		String title=request.getParameter("title");
		String msg=request.getParameter("msg");
		
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("fastloginfield","username"));
		params.add(new BasicNameValuePair("username","cyhhao"));
		params.add(new BasicNameValuePair("password","cyh223"));
		params.add(new BasicNameValuePair("quickforward","yes"));
		params.add(new BasicNameValuePair("handlekey","ls"));

		HttpTookit.doPost("http://localhost/discuz/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1", params);
		
		String html=HttpTookit.doGet("http://localhost/discuz/forum.php?mod=forumdisplay&fid=2");
		String formhash=html.split("formhash=")[1].split("\"")[0];
		System.out.println(html);
		System.out.println(formhash);
		params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("formhash",formhash));
		//params.add(new BasicNameValuePair("posttime","1408300598"));
		params.add(new BasicNameValuePair("wysiwyg","0"));
		params.add(new BasicNameValuePair("subject","hello word"));
		params.add(new BasicNameValuePair("checkbox","0"));
		params.add(new BasicNameValuePair("message","<iframe src=\"http://www.baidu.com\"></iframe>"));
		params.add(new BasicNameValuePair("replycredit_extcredits","0"));
		params.add(new BasicNameValuePair("replycredit_times","1"));
		params.add(new BasicNameValuePair("replycredit_membertimes","1"));
		params.add(new BasicNameValuePair("replycredit_random","100"));
		params.add(new BasicNameValuePair("allownoticeauthor","1"));
		params.add(new BasicNameValuePair("usesig","1"));
		params.add(new BasicNameValuePair("htmlon","1"));
		
		HttpTookit.doPost("http://localhost/discuz/forum.php?mod=post&action=newthread&fid=2&extra=&topicsubmit=yes", params);
		
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
		
		String title=request.getParameter("title");
		String msg=request.getParameter("msg");
		
		
		
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("fastloginfield","username"));
		params.add(new BasicNameValuePair("username","cyhhao"));
		params.add(new BasicNameValuePair("password","cyh223"));
		params.add(new BasicNameValuePair("quickforward","yes"));
		params.add(new BasicNameValuePair("handlekey","ls"));

		HttpTookit.doPost("http://localhost/discuz/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1", params);
		
		String html=HttpTookit.doGet("http://localhost/discuz/forum.php?mod=forumdisplay&fid=2");
		String formhash=html.split("formhash=")[1].split("\"")[0];
		System.out.println(html);
		System.out.println(formhash);
		params=new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("formhash",formhash));
		//params.add(new BasicNameValuePair("posttime","1408300598"));
		params.add(new BasicNameValuePair("wysiwyg","0"));
		params.add(new BasicNameValuePair("subject",title));
		params.add(new BasicNameValuePair("checkbox","0"));
		params.add(new BasicNameValuePair("message",msg));
		params.add(new BasicNameValuePair("replycredit_extcredits","0"));
		params.add(new BasicNameValuePair("replycredit_times","1"));
		params.add(new BasicNameValuePair("replycredit_membertimes","1"));
		params.add(new BasicNameValuePair("replycredit_random","100"));
		params.add(new BasicNameValuePair("allownoticeauthor","1"));
		params.add(new BasicNameValuePair("usesig","1"));
		params.add(new BasicNameValuePair("htmlon","1"));
		
		HttpTookit.doPost("http://localhost/discuz/forum.php?mod=post&action=newthread&fid=2&extra=&topicsubmit=yes", params);
		
		System.out.println(title+"|"+msg);
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
