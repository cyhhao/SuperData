package Tojson;

import java.util.ArrayList;

import DataPackage.Data;

import com.google.gson.Gson;

import cn.nh121.echarts.timeline.Timeline;

public class timelinePie {
     Timeline timeline;
     ArrayList<Pie>options;
    
     public timelinePie(Data da){
    	 timeline=new Timeline();
    	 ArrayList<String>dataList=new ArrayList<String>();
    	 for(int i=1;i<da.colNum;i++){
    			dataList.add(da.Head.get(i)); 
    	 }
    	 timeline.setdata(dataList);
    	 options=new ArrayList<Pie>();
    	 Pie pie=new Pie(da);
    	 options.add(pie);
    	 for(int i=2;i<da.colNum;i++){
    		 pie=new Pie();
    		 pie.series=new ArrayList<Series>();
    		 Series ps=new Series();
    		 ps.center=new ArrayList<String>();
    		 ps.center.add("50%");
    		 ps.center.add("60%");
    		 ps.data=new ArrayList<inData>();
    		 for(int j=0;j<da.rowNum;j++){
    				inData id=new inData(da.Map.get(j).list.get(i), da.Map.get(j).list.get(0));
    			    ps.data.add(id);
    		}
    		 pie.series.add(ps);
    		 options.add(pie);
    	 }
     }
     public String toString(){
     	Gson g=new Gson();
     	String str=g.toJson(this);
     	return str;
     	
     }

     
}

 
