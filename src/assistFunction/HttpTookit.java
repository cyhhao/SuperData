package assistFunction;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory; 
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;



import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map; 

/** 
* HTTP工具箱 
* 
* @author leizhimin 2009-6-19 16:36:18 
*/ 
public final class HttpTookit { 
        private static Log log = LogFactory.getLog(HttpTookit.class); 

        /** 
         * 执行一个HTTP GET请求，返回请求响应的HTML 
         * 
         * @param url                 请求的URL地址 
         * @param queryString 请求的查询参数,可以为null 
         * @param charset         字符集 
         * @param pretty            是否美化 
         * @return 返回请求响应的HTML 
         */ 
        static CookieStore cookie=null;
        public static String doGet(String url) { 
        	HttpGet httpGet=new HttpGet(url);
        	List<NameValuePair> params=new ArrayList<NameValuePair>();
    		//建立一个NameValuePair数组，用于存储欲传送的参数
    		params.add(new BasicNameValuePair("pwd","2544"));
    		//httpGet.getParams().setParameter("", arg1)
    		DefaultHttpClient httpclient= new DefaultHttpClient();
    		HttpResponse response;
    		String result="" ;
			try {
				httpclient.setCookieStore(cookie);
				httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
				response = httpclient.execute(httpGet);
				if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回
					result=EntityUtils.toString(response.getEntity());
					result=new String(result.getBytes("UTF-8"),"UTF-8");
					//得到返回的字符串
					//System.out.println(result);
	    		}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
			return result;
               
        } 

        /** 
         * 执行一个HTTP POST请求，返回请求响应的HTML 
         * 
         * @param url         请求的URL地址 
         * @param params    请求的查询参数,可以为null 
         * @param charset 字符集 
         * @param pretty    是否美化 
         * @return 返回请求响应的HTML 
         */ 
        public static CookieStore doPost(String url,List<NameValuePair> params) { 
        	//POST的URL
    		HttpPost httppost=new HttpPost(url);
    		//建立HttpPost对象
    		
    		//建立一个NameValuePair数组，用于存储欲传送的参数
    		
    		String result="";
    		//设置编码
    		try {
    			httppost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
    			httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
    			DefaultHttpClient httpclient=new DefaultHttpClient();
    			httpclient.setCookieStore(cookie);
				HttpResponse response=httpclient.execute(httppost);
				cookie=httpclient.getCookieStore();
				
				if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回
					result=EntityUtils.toString(response.getEntity());
					result=new String(result.getBytes("UTF-8"),"UTF-8");
					//得到返回的字符串
					//System.out.println(result);
					//打印输出
		                       //如果是下载文件,可以用response.getEntity().getContent()返回InputStream
				}
				else
				{
					System.out.println(""+response.getStatusLine().getStatusCode());
				}
				
				
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cookie;
        } 

       
        
        
        
        /*
         //POST的URL
    		HttpPost httppost=new HttpPost(url);
    		//建立HttpPost对象
    		List<NameValuePair> params=new ArrayList<NameValuePair>();
    		//建立一个NameValuePair数组，用于存储欲传送的参数
    		params.add(new BasicNameValuePair("pwd","2544"));
    		//添加参数

    		//设置编码
    		try {
    			httppost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
    			DefaultHttpClient httpclient=new DefaultHttpClient();
				HttpResponse response=httpclient.execute(httppost);
				CookieStore cookie=httpclient.getCookieStore();
				
				
				
				
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         */
        
        
        
}