package MySpider;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.zju.ccnt.spider.parser.FollowParser;
import cn.edu.zju.ccnt.spider.parser.bean.Page;
import cn.edu.zju.ccnt.spider.queue.FollowUrlQueue;
import cn.edu.zju.ccnt.spider.queue.VisitedFollowUrlQueue;
import cn.edu.zju.ccnt.spider.utils.Constants;
import cn.edu.zju.ccnt.spider.utils.FetcherType;
import cn.edu.zju.ccnt.spider.utils.Utils;
import cn.edu.zju.ccnt.spider.worker.BasicWorker;

public class getGet extends BasicWorker{
	
	/**
	 * @param args
	 */
	static CookieStore cookie;
	
	public getGet()
	{
		//static±ÜÃâÖØ¸´µÇÂ¼
		if(cookie==null) cookie = loginForCookie("516365710@qq.com", pas);
		if(cookie==null) System.out.println("**************null*************");
	}
	
	public List<perUser> getFollowFans(String uid,String type)
	{
		
		List<perUser> list=new ArrayList<perUser>();
		
		int PageNum=2;
		
		for(int j=1;j<=PageNum;j++)
		{
			String html=getContentFromUrl("http://weibo.cn/"+uid+"/"+type+"?page="+j,cookie);
			//System.out.println(html);
			System.out.println("http://weibo.cn/"+uid+"/"+type+"?page="+j);
			Document doc = Jsoup.parse(html);
			//»ñµÃ×ÜÒ³Êý
			if(j==1)
			{
				String Tpage1=doc.getElementsByClass("pa").get(0).getElementsByTag("div").get(0).text();
				System.out.println(Tpage1);
				String pageNumstr=Tpage1.split("/")[1].split("Ò³")[0];
				PageNum=1;//Integer.valueOf(pageNumstr);
			}
			
			Elements tablelist=doc.getElementsByTag("table");
			for(int i=0;i<tablelist.size();i++)
			{
				perUser per=new perUser();
				//System.out.println(tablelist.get(i).html());
				Elements as=tablelist.get(i).getElementsByTag("a");
				//Í·Ïñ
				per.setImg(as.get(0).getElementsByTag("img").get(0).attr("src"));
				//System.out.println(per.getImg());
				//name
				per.setName(as.get(1).html());
				System.out.println(per.getName());
				
				//id
				String Tstr1=per.getImg();
				String relid=Tstr1.split("cn/")[1].split("/")[0];
				per.setId(relid);
				System.out.println(relid);
				
				list.add(per);
				
			}
		}
		return list;
	}
	
	public void getInfo(String uid,perUser per)
	{
		String html=getContentFromUrl("http://weibo.cn/"+uid+"/info",cookie);
		Document doc = Jsoup.parse(html);
		try
		{
			Element div=doc.getElementsByClass("c").get(2);
			String infoStr=div.text();
			//System.out.println(infoStr);
			per.setSex(infoStr.split("ÐÔ±ð:")[1].split(" ")[0]);
			//System.out.println(per.getSex());
			String po=infoStr.split("µØÇø:")[1];
			per.setProvince(po.split(" ")[0]);
			//System.out.println(po);
		
			per.setCity(po.split(" ")[1].split(" ÉúÈÕ")[0]);
		}
		catch(Exception r)
		{
			per.setCity("ÆäËû");
		}
		//System.out.println(per.getSex());
		//System.out.println(per.getProvince());
		//System.out.println(per.getCity());
		
	}
	
	@Override
	protected String dataHandler(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getContentFromUrl(String url, CookieStore cookie){
		String content = null;
		Document contentDoc = null;
		
		// ÉèÖÃGET³¬Ê±Ê±¼ä
		HttpParams params = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
	    HttpConnectionParams.setSoTimeout(params, 10 * 1000);	    
	    
		AbstractHttpClient httpClient = new DefaultHttpClient(params);
		httpClient.setCookieStore(cookie);
		
		HttpGet getHttp = new HttpGet(url);	
		
		// ÉèÖÃHTTP Header
		getHttp.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:16.0) Gecko/20100101 Firefox/16.0");
		HttpResponse response;
		
		try{
			// »ñµÃÐÅÏ¢ÔØÌå
			response = httpClient.execute(getHttp);
			HttpEntity entity = response.getEntity();				  
			
			if(entity != null){
				// ×ª»¯ÎªÎÄ±¾ÐÅÏ¢, ÉèÖÃÅÀÈ¡ÍøÒ³µÄ×Ö·û¼¯£¬·ÀÖ¹ÂÒÂë
				content = EntityUtils.toString(entity, "UTF-8");

			}
		}
		catch(Exception e){
			
			//FollowUrlQueue.addFirstElement(url);
			return null;
		}
		
		VisitedFollowUrlQueue.addElement(url);

		return content;
	}
	
	public List<perUser> getFans(String uid)
	{
		List<perUser> fans=getFollowFans(uid,"fans");
		for(int i=0;i<fans.size();i++)
		{
			perUser per=fans.get(i);
			getInfo(per.id,per);
		}
		return fans;
	}
	
	public List<perUser> getFollow(String uid)
	{
		List<perUser> follow=getFollowFans(uid,"follow");
		for(int i=0;i<follow.size();i++)
		{
			perUser per=follow.get(i);
			getInfo(per.id,per);
		}
		return follow;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
																											//723958032d7f5asfd9dasf8dsf12426da9sfdsaf24sdafas6fdsf6156204639027498375
	/*243343243rqwerewrqwerqwerqwerqwerqe134124324wrqwer124134q32e4w34r34e343w4q1r4w12e34124qrweqrqwerewqrweqrweqrqwer*/static String  pas="******";//hjds32a413h1234f1k24j34dh124skfhddsfsadfdsf
																										//214325qer3e4r5weqr4e6r5q4r57wer6w547546254354ewrq35eqwre43524
	
}
