package MySpider;

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
* HTTP������ 
* 
* @author leizhimin 2009-6-19 16:36:18 
*/ 
public final class HttpTookit { 
        private static Log log = LogFactory.getLog(HttpTookit.class); 

        /** 
         * ִ��һ��HTTP GET���󣬷���������Ӧ��HTML 
         * 
         * @param url                 �����URL��ַ 
         * @param queryString ����Ĳ�ѯ����,����Ϊnull 
         * @param charset         �ַ� 
         * @param pretty            �Ƿ����� 
         * @return ����������Ӧ��HTML 
         */ 
        public static String doGet(String url,CookieStore cookie) { 
        	HttpGet httpGet=new HttpGet(url);
        	List<NameValuePair> params=new ArrayList<NameValuePair>();
    		//����һ��NameValuePair���飬���ڴ洢���͵Ĳ���
    		params.add(new BasicNameValuePair("pwd","2544"));
    		//httpGet.getParams().setParameter("", arg1)
    		DefaultHttpClient httpclient= new DefaultHttpClient();
    		HttpResponse response;
    		String result="" ;
			try {
				httpclient.setCookieStore(cookie);
				httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
				response = httpclient.execute(httpGet);
				if(response.getStatusLine().getStatusCode()==200){//���״̬��Ϊ200,�������
					result=EntityUtils.toString(response.getEntity());
					result=new String(result.getBytes("UTF-8"),"UTF-8");
					//�õ����ص��ַ�
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
         * ִ��һ��HTTP POST���󣬷���������Ӧ��HTML 
         * 
         * @param url         �����URL��ַ 
         * @param params    ����Ĳ�ѯ����,����Ϊnull 
         * @param charset �ַ� 
         * @param pretty    �Ƿ����� 
         * @return ����������Ӧ��HTML 
         */ 
        public static CookieStore doPost(String url,List<NameValuePair> params) { 
        	//POST��URL
    		HttpPost httppost=new HttpPost(url);
    		//����HttpPost����
    		
    		//����һ��NameValuePair���飬���ڴ洢���͵Ĳ���
    		CookieStore cookie=null;
    		String result="";
    		//���ñ���
    		try {
    			httppost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
    			httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
    			DefaultHttpClient httpclient=new DefaultHttpClient();
				HttpResponse response=httpclient.execute(httppost);
				cookie=httpclient.getCookieStore();
				
				if(response.getStatusLine().getStatusCode()==200){//���״̬��Ϊ200,�������
					result=EntityUtils.toString(response.getEntity());
					result=new String(result.getBytes("UTF-8"),"UTF-8");
					//�õ����ص��ַ�
					//System.out.println(result);
					//��ӡ���
		                       //����������ļ�,������response.getEntity().getContent()����InputStream
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
         //POST��URL
    		HttpPost httppost=new HttpPost(url);
    		//����HttpPost����
    		List<NameValuePair> params=new ArrayList<NameValuePair>();
    		//����һ��NameValuePair���飬���ڴ洢���͵Ĳ���
    		params.add(new BasicNameValuePair("pwd","2544"));
    		//��Ӳ���

    		//���ñ���
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