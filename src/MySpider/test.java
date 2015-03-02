package MySpider;

import java.util.List;

import com.google.gson.Gson;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getGet getit=new getGet();
		//follow.getFollowFans("1657280670","fans");
		getit.getInfo("2490080994",new perUser());
		
		List<perUser> fans=getit.getFollowFans("1587579460","fans");
		for(int i=0;i<fans.size();i++)
		{
			perUser per=fans.get(i);
			getit.getInfo(per.id,per);
		}
		
		List<perUser> follow=getit.getFollowFans("1587579460","follow");
		for(int i=0;i<follow.size();i++)
		{
			perUser per=follow.get(i);
			getit.getInfo(per.id,per);
		}
		
		
		Gson gson=new Gson();
		System.out.println(gson.toJson(fans));
		System.out.println(gson.toJson(follow));
		
		
		//String str=new String("http://weibo.cn/attention/add?uid=2554668724&rl=1&st=171e");
		//System.out.println(str.split("uid=\\d*&")[1]);
	}

}
