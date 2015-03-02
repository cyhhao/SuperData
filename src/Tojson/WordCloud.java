package Tojson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import DataPackage.Data;

public class WordCloud {
	List<per> list=new ArrayList<per>();
	class per{
		String key;
		int value;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		
	}
	
	public WordCloud(Data data)
	{
		
		for(int i=0;i<data.rowNum;i++)
		{
			per p=new per();
			p.setKey(data.getElement(i,0));
			p.setValue(Integer.valueOf(data.getElement(i,1)));
			list.add(p);
		}
	}
	
	@Override
	public String toString() {
		Gson gson=new Gson();
		String str=gson.toJson(list);
		return str;
	}
	
}
