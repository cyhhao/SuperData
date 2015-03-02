package Tojson;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.*;

import DataPackage.Data;
import DataPackage.Data.Row;

import com.google.gson.Gson;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;



public class Bar{
	Title title;
	Tooltip tooltip;
	Legend legend;
	Toolbox toolbox;
    boolean calculable;
    XAxis xAxis;
    YAxis yAxis;
    List<inSeries>series;
    public Bar(Data data){
    	title=new Title(data.onlyFileName);
    	tooltip=new Tooltip();
    	legend=new Legend(data.Head);
    	System.out.println(data.Head);
    	toolbox=new Toolbox();
    	calculable=true;
    	String []str=new String[data.rowNum];
    	for(int i=0;i<data.rowNum;i++){
    		str[i]=data.Map.get(i).list.get(0);
    	}
    	xAxis=new XAxis(str);
        xAxis.setName(data.Head.get(0));
    	yAxis=new YAxis();
         yAxis.setName(data.Head.get(1));
    	series=new ArrayList<inSeries>();
    	System.out.println(data.rowNum);
    	for(int i=1;i<data.colNum;i++){   //i表示列表的第几列
    		Double []tmp=new Double[data.rowNum];
    		for(int j=0;j<data.rowNum;j++){  //j表示列表的第几行
    			data.Map.get(j);
        		tmp[j]=Double.valueOf(data.Map.get(j).list.get(i)) ;
        		System.out.println(tmp[j]+" "+j);
        	}
    		
    		inSeries iS=new inSeries(data.Head.get(i),tmp);	
    	   	series.add(iS);
    	}
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
    //////////////////
	/////////////
	class MarkPoint{
		List<Data_oth>data;
		public MarkPoint(){
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
	class Data_oth{
		String type;
		String name;
		public Data_oth(String T,String N){
			this.type=new String(T);
			this.name=new String(N);
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	class MarkLine{
		List<Data_oth>data;
		public MarkLine(){
			data=new ArrayList<Data_oth>();
			data.add(new Data_oth("average","平均值"));	
		}
		public List<Data_oth> getData() {
			return data;
		}
		public void setData(List<Data_oth> data) {
			this.data = data;
		}
	}
	class inSeries{
		String name;
		String type="bar";
		List< Double >data;
		MarkPoint markPoint=new MarkPoint();
		MarkLine markLine=new MarkLine();
	    public inSeries(String N , Double[]da){
			this.name=new String(N);
			this.data=new ArrayList<Double>();
			for(int i=0;i<da.length;i++){
				this.data.add(da[i]);
			}
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public List<Double> getData() {
			return data;
		}
		public void setData(List<Double> data) {
			this.data = data;
		}
		public MarkPoint getMarkPoint() {
			return markPoint;
		}
		public void setMarkPoint(MarkPoint markPoint) {
			this.markPoint = markPoint;
		}
		public MarkLine getMarkLine() {
			return markLine;
		}
		public void setMarkLine(MarkLine markLine) {
			this.markLine = markLine;
		}
			
	}
	class Title{
		String text;
		String subtext;
		public Title(String str){
			this.text=new String(str);
			this.subtext=new String("");
			
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getSubtext() {
			return subtext;
		}
		public void setSubtext(String subtext) {
			this.subtext = subtext;
		}
	}
	class Tooltip{
		String trigger="axis";

		public String getTrigger() {
			return trigger;
		}

		public void setTrigger(String trigger) {
			this.trigger = trigger;
		}
	}
	class Legend{
		List<String>data;
		public Legend(List<String>str){
			data=new ArrayList<String>();
			data.addAll(str.subList(1, str.size()));
			
		}
		public List<String> getLegend() {
			return data;
		}
		public void setLegend(List<String> data) {
			this.data = data;
		}
	}
	class Toolbox{
		boolean show=true;
		Feature feature=new Feature();
		public boolean isShow() {
			return show;
		}
		public void setShow(boolean show) {
			this.show = show;
		}
		public Feature getFeature() {
			return feature;
		}
		public void setFeature(Feature feature) {
			this.feature = feature;
		} 
	}
	class Mark{
		boolean show=true;

		public boolean isShow() {
			return show;
		}

		public void setShow(boolean show) {
			this.show = show;
		}
		
	}
	class DataView{
		boolean show=true;
		boolean readOnly=false;
		public boolean isShow() {
			return show;
		}
		public void setShow(boolean show) {
			this.show = show;
		}
		public boolean isReadOnly() {
			return readOnly;
		}
		public void setReadOnly(boolean readOnly) {
			this.readOnly = readOnly;
		}
		
	}
	class MagicType{
		boolean show=true;
		List<String>type;
		public MagicType(){
			this.type=new ArrayList<String>();
			this.type.add("line");
			this.type.add("bar");
			this.type.add("stack");
			this.type.add("tiled");
		}
		public boolean isShow() {
			return show;
		}
		public void setShow(boolean show) {
			this.show = show;
		}
		public List<String> getType() {
			return type;
		}
		public void setType(List<String> type) {
			this.type = type;
		}
	}

	class Restore{
		boolean show=true;

		public boolean isShow() {
			return show;
		}

		public void setShow(boolean show) {
			this.show = show;
		}
		
	}

	class SaveAsImage{
		boolean  show=true;

		public boolean isShow() {
			return show;
		}

		public void setShow(boolean show) {
			this.show = show;
		}
		
		
	}
	class Feature{
		Mark mark=new Mark();
		DataZoom dataZoom=new DataZoom();
		DataView dataView=new DataView();
		MagicType magicType=new MagicType();
		Restore restore=new Restore();
		SaveAsImage saveAsImage=new SaveAsImage();
		class DataZoom{
			boolean show=true;

			public boolean isShow() {
				return show;
			}

			public void setShow(boolean show) {
				this.show = show;
			}
		}
		public Mark getMark() {
			return mark;
		}
		public void setMark(Mark mark) {
			this.mark = mark;
		}
		public DataView getDataView() {
			return dataView;
		}
		public void setDataview(DataView dataView) {
			this.dataView = dataView;
		}
		public MagicType getMagicType() {
			return magicType;
		}
		public void setMagicType(MagicType magicType) {
			this.magicType = magicType;
		}
	}
	class XAxis{
                public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
                String name;
		String type;
		List <String>data;
		public XAxis(String[]da){
			this.type="category";
			this.data=new ArrayList<String>();
			for(int i=0;i<da.length;i++){
				this.data.add(da[i]);
			}
			
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public List<String> getData() {
			return data;
		}
		public void setData(List<String> data) {
			this.data = data;
		}
	}
	class YAxis{
	      String type="value";
              public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
                String name;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	}
}  


