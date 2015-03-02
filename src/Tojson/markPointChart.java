package Tojson;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import DataPackage.RserveBegin;

import com.google.gson.Gson;

import cn.nh121.echarts.core.EColor;
import cn.nh121.echarts.legend.Legend;
import cn.nh121.echarts.legend.LegendItem;
import cn.nh121.echarts.pubenum.EOrient;
import cn.nh121.echarts.pubenum.ESelectedMode;
import cn.nh121.echarts.style.TextStyle;
import cn.nh121.echarts.title.Title;
import cn.nh121.echarts.toolbox.Toolbox;
import cn.nh121.echarts.toolbox.feature.DataView;
import cn.nh121.echarts.toolbox.feature.Feature;
import cn.nh121.echarts.toolbox.feature.Mark;
import cn.nh121.echarts.toolbox.feature.Restore;
import cn.nh121.echarts.toolbox.feature.SaveAsImage;

public class markPointChart {
	String backgroundColor="#1b1b1b";
	MyTitle title;
	Legend legend;
	Toolbox toolbox;
	ArrayList<mySeries>series;
	public  markPointChart(RserveBegin da) throws IOException {
		title=new MyTitle();
		title.setText("");
		title.setX("center");
		legend=new Legend();
		legend.setOrient(EOrient.VERTICAL);
		legend.setX("left");
		legend.setTextStyle(new TextStyle().setColor(EColor.WHITE));
		legend.setSelectedMode(ESelectedMode.SINGLE);
		ArrayList<LegendItem>data1;
		data1=new ArrayList<LegendItem>();
		Set<String> set = da.itemStaMap_location.keySet();
		/*
		for (String ch : set) {
			if( da.existedMap.get( ch ) != null ){
				data1.add(new LegendItem().setName(ch));
			}
			
		}
		*/

		
		
		for (String ch : set) {
			if(da.existedMap.get(ch)!=null){
				data1.add(new LegendItem().setName(ch));
			}
			
		}
		
		legend.setData(data1);
		
		
		//Feature
		Mark mark=new Mark();
		mark.setShow(true);
		DataView dataView=new DataView();
		dataView.setShow(true);
		dataView.setReadOnly(false);
		dataView.setLang("Data View", "close", "refresh");
		Restore restore=new Restore();
		restore.setShow(true);
		SaveAsImage saveAsImage=new SaveAsImage();
		saveAsImage.setShow(true);
		saveAsImage.setLang("�������");
		Feature feature=new Feature();
		feature.setMark(mark);
		feature.setDataView(dataView);
		feature.setRestore(restore);
		feature.setSaveAsImage(saveAsImage);
		// toolbox
		toolbox=new  Toolbox();
		toolbox.setX("right");
		toolbox.setY("center");
		toolbox.setShow(true);
		toolbox.setOrient(EOrient.VERTICAL);
		toolbox.setFeature(feature);
		//series
		HashMap<String, ArrayList<Double>>placeList=readPlace();
		series=new ArrayList<mySeries>();
	//	count=5;
		for (String ch : set) {
			//if(da.existedMap.get( ch ) != null ){
			if(da.existedMap.get(ch)!=null){
				mySeries ms=new mySeries();
				ms.setName(ch);
				MarkPoint mp=new MarkPoint();
				for(int j=0;j<da.itemStaMap_location.get(ch).list.size();j++){
					for(int k=0;k<da.itemStaMap_location.get(ch).list.get(j).time;k++){
						markData md=new markData();
						md.setName(da.itemStaMap_location.get(ch).list.get(j).info );
						ArrayList<Double>arrlist=new ArrayList<Double>();
						if(placeList.get(da.itemStaMap_location.get(ch).list.get(j).info).get(1)>25){
							arrlist.add(placeList.get(da.itemStaMap_location.get(ch).list.get(j).info).get(0)+Math.random() * 5 * -1);		
							arrlist.add(placeList.get(da.itemStaMap_location.get(ch).list.get(j).info).get(1)+Math.random() * 3 * -1);
							md.setGeoCoord(arrlist);
						    mp.data.add(md);
						}
						
						
						
					}	
				}
				ms.setMarkPoint(mp);
				series.add(ms);	
			}
			}
			
	}
	
	
	public String toString(){
     	Gson g=new Gson();
     	String str=g.toJson(this);
     	return str;
     	
     }


	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public MyTitle getTitle() {
		return title;
	}
	public void setTitle(MyTitle title) {
		this.title = title;
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
	public ArrayList<mySeries> getSeries() {
		return series;
	}
	public void setSeries(ArrayList<mySeries> series) {
		this.series = series;
	}
    public static HashMap<String, ArrayList<Double>>readPlace() throws IOException{
    	String Place="����,121.15,31.89 ������˹,109.781327,39.608266 ��Զ,120.38,37.35 ��ɽ,122.207216,29.985295 �������,123.97,47.33 �γ�,120.13,33.38 ���,118.87,42.28 �ൺ,120.33,36.07 ��ɽ,121.52,36.89 ���,102.188043,38.520089 Ȫ��,118.58,24.93 ����,120.53,36.86 ����,119.46,35.42 ����,119.97,35.88 ��ͨ,121.05,32.08 ����,91.11,29.97 �Ƹ�,112.02,22.93 ÷��,116.1,24.55 �ĵ�,122.05,37.2 �Ϻ�,121.48,31.22 ��֦��,101.718637,26.582347 ����,122.1,37.5 �е�,117.93,40.97 ����,118.1,24.46 ��β,115.375279,22.786211 ����,116.63,23.68 ����,124.37,40.13 ̫��,121.1,31.45 ����,103.79,25.51 ��̨,121.39,37.52 ����,119.3,26.08 �߷���,121.979603,39.627114 ��ī,120.45,36.38 ��˳,123.97,41.97 ��Ϫ,102.52,24.35 �żҿ�,114.87,40.82 ��Ȫ,113.57,37.85 ����,119.942327,37.177017 ����,120.1,30.86 ��ͷ,116.69,23.39 ��ɽ,120.95,31.39 ����,121.56,29.86 տ��,110.359377,21.270708 ����,116.35,23.55 �ٳ�,122.41,37.16 ���Ƹ�,119.16,34.59 ��«��,120.836932,40.711052 ����,120.74,31.64 ��ݸ,113.75,23.04 ��Դ,114.68,23.73 ����,119.15,33.5 ̩��,119.9,32.49 ����,108.33,22.84 Ӫ��,122.18,40.65 ����,114.4,23.09 ����,120.26,31.91 ����,120.75,37.8 �ع�,113.62,24.84 ������,98.289152,39.77313 ����,113.23,23.16 �Ӱ�,109.47,36.6 ̫ԭ,112.53,37.87 ��Զ,113.01,23.7 ��ɽ,113.38,22.52 ����,102.73,25.04 �ٹ�,118.73,36.86 �̽�,122.070714,41.119997 ����,113.08,36.18 ����,114.07,22.62 �麣,113.52,22.3 ��Ǩ,118.3,33.96 ����,108.72,34.36 ͭ��,109.11,35.09 ƽ��,119.97,36.77 ��ɽ,113.11,23.05 ����,110.35,20.02 ����,113.06,22.61 ����,117.53,36.72 ����,112.44,23.05 ����,121.62,38.92 �ٷ�,111.5,36.08 �⽭,120.63,31.16 ʯ��ɽ,106.39,39.04 ����,123.38,41.8 ����,120.62,31.32 ï��,110.88,21.68 ����,120.76,30.77 ����,125.35,43.88 ����,120.03336,36.264622 ����,106.27,38.47 �żҸ�,120.555821,31.875428 ����Ͽ,111.19,34.76 ����,121.15,41.13 �ϲ�,115.89,28.68 ����,109.4,24.33 ����,109.511909,18.252847 �Թ�,104.778442,29.33903 ����,126.57,43.87 ����,111.95,21.85 ����,105.39,28.91 ����,101.74,36.56 �˱�,104.56,29.77 ���ͺ���,111.65,40.82 �ɶ�,104.06,30.67 ��ͬ,113.3,40.12 ��,119.44,32.2 ����,110.28,25.29 �żҽ�,110.479191,29.117096 ����,119.82,31.36 ����,109.12,21.49 ����,108.95,34.27 ��̳,119.56,31.74 ��Ӫ,118.49,37.46 ĵ����,129.58,44.6 ����,106.9,27.7 ����,120.58,30.01 ����,119.42,32.39 ����,119.95,31.79 Ϋ��,119.1,36.62 ����,106.54,29.59 ̨��,121.420757,28.656386 �Ͼ�,118.78,32.04 ����,118.03,37.36 ����,106.71,26.57 ����,120.29,31.59 ��Ϫ,123.73,41.3 ��������,84.77,45.59 μ��,109.5,34.52 ��ɽ,118.48,31.56 ����,107.15,34.38 ����,113.21,35.24 ����,119.16,31.95 ����,116.46,39.92 ����,117.2,34.26 ��ˮ,115.72,37.72 ��ͷ,110,40.58 ����,104.73,31.48 ��³ľ��,87.68,43.77 ��ׯ,117.57,34.86 ����,120.19,30.26 �Ͳ�,118.05,36.78 ��ɽ,122.85,41.12 ����,119.48,31.43 �����,86.06,41.68 ����,114.35,36.1 ����,114.35,34.79 ����,117,36.65 ����,104.37,31.13 ����,120.65,28.01 �Ž�,115.97,29.71 ����,114.47,36.6 �ٰ�,119.72,30.23 ����,103.73,36.03 ����,116.83,38.33 ����,118.35,35.05 �ϳ�,106.110698,30.837793 ���,117.2,39.13 ����,119.95,30.07 ̩��,117.13,36.18 ����,120.23,29.71 ֣��,113.65,34.76 ������,126.63,45.75 �ĳ�,115.97,36.45 �ߺ�,118.38,31.33 ��ɽ,118.02,39.63 ƽ��ɽ,113.29,33.75 ��̨,114.48,37.05 ����,116.29,37.45 ����,116.59,35.38 ����,112.239741,30.335165 �˲�,111.3,30.7 ����,120.06,29.32 ��ˮ,119.92,28.45 ����,112.44,34.7 �ػʵ�,119.57,39.95 ����,113.16,27.83 ʯ��ׯ,114.48,38.03 ����,117.67,36.19 ����,111.69,29.05 ����,115.48,38.85 ��̶,112.91,27.87 ��,119.64,29.12 ����,113.09,29.37 ��ɳ,113,28.21 ����,118.88,28.97 �ȷ�,116.7,39.53 ����,115.480656,35.23375 �Ϸ�,117.27,31.86 �人,114.31,30.52 ����,125.03,46.58";
    	HashMap<String, ArrayList<Double>>placeList=new HashMap<String, ArrayList<Double>>();
    	String []tmp=Place.split(" ");
    	for(int i=0;i<tmp.length;i++){
    		String[] str=tmp[i].split(",");
    		ArrayList<Double>arr=new ArrayList<Double>();
    		arr.add(Double.valueOf(str[1]));
    		arr.add(Double.valueOf(str[2]));
    		placeList.put(str[0], arr);
    	}
    	return placeList;
    }
}

class MyTitle{
	String text;
	String x;
	TextStyle textStyle=new TextStyle();
	class TextStyle{
		String color="#fff";

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public TextStyle getTextStyle() {
		return textStyle;
	}
	public void setTextStyle(TextStyle textStyle) {
		this.textStyle = textStyle;
	}
	
}
class mySeries{
	String name;
	String type="map";
	String mapType="china";
	boolean roam=true;
	boolean hoverable=false;
	myItemStyle itemStyle=new myItemStyle();
	ArrayList<Double>data=new ArrayList<Double>();
	MarkPoint markPoint=new MarkPoint();
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
	public String getMapType() {
		return mapType;
	}
	public void setMapType(String mapType) {
		this.mapType = mapType;
	}
	public myItemStyle getItemStyle() {
		return itemStyle;
	}
	public void setItemStyle(myItemStyle itemStyle) {
		this.itemStyle = itemStyle;
	}
	public ArrayList<Double> getData() {
		return data;
	}
	public void setData(ArrayList<Double> data) {
		this.data = data;
	}
	public MarkPoint getMarkPoint() {
		return markPoint;
	}
	public void setMarkPoint(MarkPoint markPoint) {
		this.markPoint = markPoint;
	}
	
}
class MarkPoint{
	int symbolSize=2;
	boolean large=true;
	Effect effect=new Effect();
	ArrayList<markData>data=new ArrayList<markData>();
	public int getSymbolSize() {
		return symbolSize;
	}
	public void setSymbolSize(int symbolSize) {
		this.symbolSize = symbolSize;
	}
	public boolean isLarge() {
		return large;
	}
	public void setLarge(boolean large) {
		this.large = large;
	}
	public Effect getEffect() {
		return effect;
	}
	public void setEffect(Effect effect) {
		this.effect = effect;
	}
	public ArrayList<markData> getData() {
		return data;
	}
	public void setData(ArrayList<markData> data) {
		this.data = data;
	}

}
class Effect{
    boolean show= true;
}
class markData{
	String name;
	ArrayList<Double>geoCoord;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Double> getGeoCoord() {
		return geoCoord;
	}
	public void setGeoCoord(ArrayList<Double> geoCoord) {
		this.geoCoord = geoCoord;
	}
}
class myItemStyle{
	Normal normal=new Normal();
	class Normal{
		 String borderColor="rgba(100,149,237,1)";
		 double borderWidth=1.5;
		 AreaStyle areaStyle=new AreaStyle();
		 class AreaStyle{
             String color= "#1b1b1b";
         }
	}
}


