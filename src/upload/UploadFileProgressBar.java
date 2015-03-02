package upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadFileProgressBar extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//定义允许上传的文件扩展名
	protected HashMap<String, String> extMap = new HashMap<String, String>();
	//�?��文件大小 100 M  --测试�?
	protected long  maxSize = 100 * 1024 * 1024;
	//上传文件的保存路�?
	protected String configPath = "attached/";

	protected String dirTemp = "attached/temp/";
	
	protected String dirName = "file";
	
	public void init() throws ServletException {
		
		//定义允许上传的文件扩展名
		//extMap.put("image", "gif,jpg,jpeg,png,bmp");
		//extMap.put("flash", "swf,flv");
		//extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,csv,arff"); 
		
		
		
	}

	
	/**
	 * 处理上传文件
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void processFileUpload(HttpServletRequest request, PrintWriter out)
		throws ServletException, IOException {
	 
		//文件保存目录路径
		String savePath = getServletContext().getRealPath("/") + configPath;
		
		// 临时文件目录 
		String tempPath = getServletContext().getRealPath("/") + dirTemp;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String ymd = sdf.format(new Date());
		//savePath += "/" + ymd + "/";
		//创建文件�?
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
		tempPath += "/" + ymd + "/";
		//创建临时文件�?
		File dirTempFile = new File(tempPath);
		if (!dirTempFile.exists()) {
			dirTempFile.mkdirs();
		}
		
		DiskFileItemFactory  factory = new DiskFileItemFactory();
		factory.setSizeThreshold(20 * 1024 * 1024); //设定使用内存超过5M时，将产生临时文件并存储于临时目录中�?  
		factory.setRepository(new File(tempPath)); //设定存储临时文件的目录�?   

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		
		//创建�?��进度监听�?
		FileUploadProgressListener progressListener = new FileUploadProgressListener(request);
		upload.setProgressListener(progressListener);
		
		try {
			List items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				String fileName = item.getName();
				long fileSize = item.getSize();
				if (!item.isFormField()) {
					//�?��文件大小
					if(item.getSize() > maxSize){
						setStatusMsg(request, "1", "上传文件大小超过限制");
						return;
					}
					//�?��扩展�?
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
						setStatusMsg(request, "1", "上传文件扩展名是不允许的扩展名"+ extMap.get(dirName) + "格式");
						return;
					}

					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					
					//String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
					String newFileName =item.getName();
					try{
						File uploadedFile = new File(savePath, newFileName);
						
						/*
						 * 第一种方�?
						 * 
						 * 好处�?�?��了然..�?���?..
						 * 弊端�?这种方法会导致上传的文件大小比原来的文件要大
						 * 
						 * 推荐使用第二�?
						 */
						//item.write(uploadedFile);
						//--------------------------------------------------------------------
						//第二种方�?
	                    OutputStream os = new FileOutputStream(uploadedFile);  
	                    InputStream is = item.getInputStream();  
	                    byte buf[] = new byte[1024];//可以修改 1024 以提高读取�?�?
	                    int length = 0;  
	                    while( (length = is.read(buf)) > 0 ){  
	                        os.write(buf, 0, length);  
	                    }  
	                    //关闭�? 
	                    os.flush();
	                    os.close();  
	                    is.close();  
	                      
					}catch(Exception e){
						setStatusMsg(request, "1", "上传文件失败");
						return;
					}
					setStatusMsg(request, "2", "文件上传成功");
				}
			}
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 
	 * 错误信息的处�?
	 * 
	 * @param request
	 * @param error -- 1 �?错误  0 �?正常  2 : 上传完成
	 * @param message
	 */
	private void setStatusMsg(HttpServletRequest request, String error, String message) {
		HttpSession session = request.getSession();
		FileUploadStatus status = (FileUploadStatus) session.getAttribute("upladeStatus");
		status.setError(error);
		status.setStatusMsg(message);
	}
	
	/**
	 * 
	 * 获取状�?信息
	 * 
	 * @param request
	 * @param out
	 */

	private void getStatusMsg(HttpServletRequest request,PrintWriter out){
		HttpSession session = request.getSession();
		FileUploadStatus status = (FileUploadStatus) session.getAttribute("upladeStatus");
		System.out.println("输出信息对象"+status);
		out.println(status.toJSon());
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();
		//�?��输入请求是否为multipart表单数据�?  
		boolean isMultipart= FileUpload.isMultipartContent(request);   
		if (isMultipart) {
			processFileUpload(request, out);
		}else {
			if (request.getParameter("uploadStatus") != null) {
				//response.setContentType("text/xml");
				//response.setHeader("Cache-Control", "no-cache");
				System.out.println("ajax 读取状�?····");
				getStatusMsg(request, out);
			}
		}
		
		out.flush();
		out.close();
	}
	
	 

}
