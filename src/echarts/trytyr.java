package echarts;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class trytyr {
	
	public static void main(String[] args) {
		class sss{
			public List<Double> getList() {
				return list;
			}

			public void setList(List<Double> list) {
				this.list = list;
			}

			List<Double> list;
			
		}

		Gson gson=new Gson();
		List<Double> dou=new ArrayList<Double>();
		dou.add(1.2);
		dou.add(1.3);
		sss ss=new sss();
		ss.setList(dou);
		System.out.print(gson.toJson(ss));
	}
}


