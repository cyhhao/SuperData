package DataPackage;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import MySpider.getGet;
import MySpider.perUser;
import Tojson.ChartFactory;

import assistFunction.Value;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import weibo4j.Users;
import weibo4j.model.User;
import weibo4j.model.WeiboException;




public class Social {
	
	

	private String token;
	private Data PersonInfo;
	
	private rejson obj;
	
	private perMap map1;
	private perMap map2;
	
	//Map<String,String> proMap;
	
	public Social(String token)
	{
		this.token=token;
		//proMap=new HashMap<String,String>();
		//TODO:
		
	}
	
	public void get(String name1,String name2)
	{
		Users um = new Users(token);
		try 
		{
			User user1 = um.showUserByScreenName(name1);//um.showUserById(uid);
			User user2 = um.showUserByScreenName(name2);
			System.out.println(name1+"|"+name2);
			//获取(填充)信息
			PersonInfo=creatPersonInfo(user1,user2);

			
			obj=new rejson();
			//获得头像
			obj.setImage_url1(user1.getAvatarLarge());
			obj.setImage_url2(user2.getAvatarLarge());
			
			//uid
			obj.setUid1(user1.getId());
			obj.setUid2(user2.getId());
			
			//获得省份
			obj.setProvince1(user1.getLocation().split(" ")[0]);
			obj.setProvince2(user2.getLocation().split(" ")[0]);
			
			//Personinfo图表化
			obj.setPersoninfo(ChartFactory.creator("bar",PersonInfo));
			
			
			
		} 
		catch (WeiboException e) 
		{
			e.printStackTrace();
		}
	}
	public String tojs()
	{
		return obj.toString();
	}
	
	public Data creatPersonInfo(User user1,User user2)
	{
		Data PersonInfo=new Data();
		
		PersonInfo.onlyFileName="";
		
		List<String>head=new ArrayList<String>();
		head.add("信息");
		head.add("粉丝数");
		head.add("关注数");
		head.add("互粉数");
		head.add("微博数");
		head.add("收藏数");
		PersonInfo.Head=head;
		
		PersonInfo.Map=new ArrayList<Data.Row>();
		Data.Row row1=new Data.Row();
		row1.list.add(user1.getName());
		row1.list.add(""+user1.getFollowersCount());
		row1.list.add(""+user1.getFriendsCount());
		row1.list.add(""+user1.getbiFollowersCount());
		row1.list.add(""+user1.getStatusesCount());
		row1.list.add(""+user1.getFavouritesCount());
		
		PersonInfo.Map.add(row1);
		
		Data.Row row2=new Data.Row();
		row2.list.add(user2.getName());
		row2.list.add(""+user2.getFollowersCount());
		row2.list.add(""+user2.getFriendsCount());
		row2.list.add(""+user2.getbiFollowersCount());
		row2.list.add(""+user2.getStatusesCount());
		row2.list.add(""+user2.getFavouritesCount());
		
		PersonInfo.Map.add(row2);
		
		
		
		PersonInfo.rowNum=2;
		PersonInfo.colNum=6;
		
		return PersonInfo;
	}
	
	public class rejson{
		public String getUid1() {
			return uid1;
		}
		public void setUid1(String uid1) {
			this.uid1 = uid1;
		}
		public String getUid2() {
			return uid2;
		}
		public void setUid2(String uid2) {
			this.uid2 = uid2;
		}
		@Override
		public String toString() {
			
			Gson gson=new Gson();
			return gson.toJson(this);
		}
		public String getImage_url1() {
			return image_url1;
		}
		public void setImage_url1(String image_url1) {
			this.image_url1 = image_url1;
		}
		public String getImage_url2() {
			return image_url2;
		}
		public void setImage_url2(String image_url2) {
			this.image_url2 = image_url2;
		}
		public String getProvince1() {
			return province1;
		}
		public void setProvince1(String province1) {
			this.province1 = province1;
		}
		public String getProvince2() {
			return province2;
		}
		public void setProvince2(String province2) {
			this.province2 = province2;
		}
		public Object getPersoninfo() {
			return personinfo;
		}
		public void setPersoninfo(Object personinfo) {
			this.personinfo = personinfo;
		}
		
		
		
		private String image_url1;
		private String image_url2;
		
		private String province1; 
		private String province2; 
		
		private Object personinfo;

		private String uid1;
		private String uid2;

	}
	
	public class perMap {
		
		public saveFont getLeft() {
			return left;
		}
		public void setLeft(saveFont left) {
			this.left = left;
		}
		public saveFont getRight() {
			return right;
		}
		public void setRight(saveFont right) {
			this.right = right;
		}
		private saveFont left;
		private saveFont right;
		
		
	}
	
	public class saveFont
	{
		public List<List<point>> getFollowl() {
			return followl;
		}
		public void setFollowl(List<List<point>> followl) {
			this.followl = followl;
		}
		public List<point> getFollow() {
			return follow;
		}
		public void setFollow(List<point> follow) {
			this.follow = follow;
		}
		public List<List<point>> getFansl() {
			return fansl;
		}
		public void setFansl(List<List<point>> fansl) {
			this.fansl = fansl;
		}
		public List<point> getFans() {
			return fans;
		}
		public void setFans(List<point> fans) {
			this.fans = fans;
		}
		List<List<point>> followl;
		List<point> follow;
		List<List<point>> fansl;
		List<point> fans;

	}
	class point
	{
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		String name;
		int value;
		
	}
	
	public String getMap(String uid1,String uid2,String p1,String p2)
	{

		//构建大Map
		perMap All=new perMap();
		//获得工具
		getGet tools=new getGet();
		
		saveFont left=new saveFont();
		//获得uid1的Follow
		List<perUser> FollowList1=tools.getFollow(uid1);
		//统计
		Map<String,Integer> follow1=countF(FollowList1);
		//填saveFont
		getfol(follow1, p1, left);
		
		//获得uid1的Fans
		List<perUser> Fans1=tools.getFans(uid1);
		//统计
		Map<String,Integer> fans1=countF(Fans1);
		//填saveFont
		getfan(fans1, p1, left);
		
		saveFont right=new saveFont();
		//获得uid2的Follow
		List<perUser> Follow2=tools.getFollow(uid2);
		//统计
		Map<String,Integer> follow2=countF(Follow2);
		//填saveFont
		getfol(follow2, p2, right);
		
		//获得uid2的Fans
		List<perUser> Fans2=tools.getFans(uid2);
		//统计
		Map<String,Integer> fans2=countF(Fans2);
		//填saveFont
		getfan(fans2, p2, right);
		
		All.setLeft(left);
		All.setRight(right);
		
		Gson gson=new Gson();
		return gson.toJson(All);
		
	}
	public Map<String,Integer> countF(List<perUser> FollowList)
	{
		Map<String,Integer> follow=new HashMap<String, Integer>();
		for(int i=0;i<FollowList.size();i++)
		{
			String str=FollowList.get(i).getProvince();
			int k=0;
			if(follow.get(str)!=null) k=follow.get(str);
			follow.put(str,k+1);
		}
		return follow;
	}
	public void getfol(Map<String,Integer> follow,String org,saveFont save)
	{
		//遍历Maps生成 saveFont
		List<point> list=new ArrayList<point>();
		List<List<point>> list2=new ArrayList<List<point>>();
		for (String key : follow.keySet()) 
		{
			point p=new point();
			p.setName(key);
			p.setValue(follow.get(key));
			list.add(p);
			
			List<point> tem=new ArrayList<point>();
			point pp=new point();
			pp.setName(org);
			pp.setValue(follow.get(key));
			
			tem.add(pp);
			tem.add(p);
			
			list2.add(tem);
		}
		save.setFollow(list);
		save.setFollowl(list2);
	}
	public void getfan(Map<String,Integer> follow,String org,saveFont save)
	{
		//遍历Maps生成 saveFont
		List<point> list=new ArrayList<point>();
		List<List<point>> list2=new ArrayList<List<point>>();
		for (String key : follow.keySet()) 
		{
			point p=new point();
			p.setName(key);
			p.setValue(follow.get(key));
			list.add(p);
			
			List<point> tem=new ArrayList<point>();
			point pp=new point();
			pp.setName(org);
			pp.setValue(follow.get(key));
			
			tem.add(p);
			tem.add(pp);
			
			list2.add(tem);
		}
		save.setFans(list);
		save.setFansl(list2);
	}

	public String getSoci(String uid1,String uid2,String name1,String name2)
	{
		//获得工具
		getGet tools=new getGet();
		
		List<perUser> follow1=tools.getFollowFans(uid1,"follow");
		List<perUser> follow2=tools.getFollowFans(uid2,"follow");
		
		List<perUser> fans1=tools.getFollowFans(uid1,"fans");
		List<perUser> fans2=tools.getFollowFans(uid2,"fans");
		
		List<node> nodes=new ArrayList<node>();
		List<link> links=new ArrayList<link>();
		
		nodes.add(new node(0,name1,10));
		nodes.add(new node(0,name2,10));
		
		for(int i=0;i<follow1.size();i++)
		{
			String target=follow1.get(i).getName();
			if(!target.equalsIgnoreCase(name1)&&!target.equalsIgnoreCase(name2))
				nodes.add(new node(1,target,5));
			links.add(new link(name1,target,1));
		}
		
		for(int i=0;i<follow2.size();i++)
		{
			String target=follow2.get(i).getName();
			if(!target.equalsIgnoreCase(name1)&&!target.equalsIgnoreCase(name2))
				nodes.add(new node(1,target,5));
			links.add(new link(name2,target,1));
		}
		
		for(int i=0;i<fans1.size();i++)
		{
			String source=fans1.get(i).getName();
			if(!source.equalsIgnoreCase(name1)&&!source.equalsIgnoreCase(name2))
				nodes.add(new node(2,source,5));
			links.add(new link(source,name1,1));
		}
		
		for(int i=0;i<fans2.size()-1;i++)
		{
			String source=fans2.get(i).getName();
			if(!source.equalsIgnoreCase(name1)&&!source.equalsIgnoreCase(name2))
				nodes.add(new node(2,source,5));
			links.add(new link(source,name2,1));
		}
		
		String source=fans2.get(fans2.size()-1).getName();
		if(!source.equalsIgnoreCase(name1)&&!source.equalsIgnoreCase(name2))
			nodes.add(new node(2,source,5));
		links.add(new link(source,name2,10));
		
		List<Object> list=new ArrayList<Object>();
		list.add(nodes);
		list.add(links);
		
		
		Gson gson=new Gson();

		return gson.toJson(list);
		
	}
	
	class node{
		int category;
		String name;
		int value;
		
		public node(int category, String name, int value) {
			super();
			this.category = category;
			this.name = name;
			this.value = value;
		}
		public int getCategory() {
			return category;
		}
		public void setCategory(int category) {
			this.category = category;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		
	}
	
	class link{
		String source;
		String target;
		int weight;
		public String getSource() {
			return source;
		}
		public void setSource(String source) {
			this.source = source;
		}
		public String getTarget() {
			return target;
		}
		public void setTarget(String target) {
			this.target = target;
		}
		public int getWeight() {
			return weight;
		}
		public void setWeight(int weight) {
			this.weight = weight;
		}
		public link(String source, String target, int weight) {
			super();
			this.source = source;
			this.target = target;
			this.weight = weight;
		}
		
	}

}
