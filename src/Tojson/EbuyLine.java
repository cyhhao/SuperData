package Tojson;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.*;

import DataPackage.Data.Row;
import DataPackage.RserveBegin;
import DataPackage.RserveBegin.ItemList;

import cn.nh121.echarts.legend.LegendItem;

import com.google.gson.Gson;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

class lineMarkPoint{
	List<Data_oth>data;
	public lineMarkPoint(){
		data=new ArrayList<Data_oth>();
		data.add(new Data_oth("max","最大值"));
		data.add(new Data_oth("min","最小值"));
		
	}
	public List<Data_oth> getData() {
		return data;
	}
	public void setData(List<Data_oth> data) {
		this.data = data;
	}
	
}

public class EbuyLine {
	Title title;
	Tooltip tooltip;
	Legend legend;
	Toolbox toolbox;
    boolean calculable;
    XAxis xAxis;
    YAxis yAxis;
    List<inSeries>series;
    public EbuyLine(RserveBegin data){
    	
    	String []str=new String[16];
    	HashMap<String, Integer>dataList=new HashMap<String, Integer>();
    	for(int i=1;i<17;i++){
    		dataList.put("2005/11/"+i, i-1);
    		str[i-1]="2005/11/"+i;
    	}
    	List<String>legendList=new ArrayList<String>();
    	Set<String> set = data.itemStaMap_location.keySet();

/*
		for (String ch : set) {
			if(data.existedMap.get(ch)!=null){
				legendList.add(ch);
				System.out.println(legendList);
			}
			
		}
		*/
    	title=new Title("Title");
    	tooltip=new Tooltip();
   // 	legend=new Legend(legendList);
    	
    	toolbox=new Toolbox();
    	calculable=true;
    	
    	xAxis=new XAxis(str);
    	yAxis=new YAxis();
    	series=new ArrayList<inSeries>();

		for (String ch : set) {
			if(data.existedMap.get(ch)!=null){
				Double []tmp=new Double[16];
				ItemList tmp_map=data.itemStaMap_time.get(ch);
				for(int k=0;k<16;k++){
					tmp[k]=(double) 0;
				}
	    		for(int j=0;j<tmp_map.list.size() ;j++){  //j表示列表的第几行
	    			tmp[ dataList.get(tmp_map.list.get(j).info)]=(double) tmp_map.list.get(j).time;
	        	}
	    		
	    		inSeries iS=new inSeries(ch,tmp);	
	    		legendList.add(ch);
	    	   	series.add(iS);
			}
		}
		legend=new Legend(legendList);
    	
    }
    
    public String toString(){
    	Gson g=new Gson();
    	String str=g.toJson(this);
    	return str;    	
    }
  
    ///////////////////////////
    //////////////////////////
	public Title getTitle() {
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public Tooltip getTooltip() {
		return tooltip;
	}
	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}
	public Legend getLegend() {
		return legend;
	}
	public void setLegend(Legend legend) {
		this.legend = legend;
	}
	public Toolbox getToolbox() {
		return toolbox;
	}
	public void setToolbox(Toolbox toolbox) {
		this.toolbox = toolbox;
	}
	public boolean isCalculable() {
		return calculable;
	}
	public void setCalculable(boolean calculable) {
		this.calculable = calculable;
	}
	public XAxis getxAxis() {
		return xAxis;
	}
	public void setxAxis(XAxis xAxis) {
		this.xAxis = xAxis;
	}
	public YAxis getyAxis() {
		return yAxis;
	}
	public void setyAxis(YAxis yAxis) {
		this.yAxis = yAxis;
	}
	public List<inSeries> getSeries() {
		return series;
	}
	public void setSeries(List<inSeries> series) {
		this.series = series;
	}
     
}  


