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
		saveAsImage.setLang("点击保存");
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
    	String Place="海门,121.15,31.89 鄂尔多斯,109.781327,39.608266 招远,120.38,37.35 舟山,122.207216,29.985295 齐齐哈尔,123.97,47.33 盐城,120.13,33.38 赤峰,118.87,42.28 青岛,120.33,36.07 乳山,121.52,36.89 金昌,102.188043,38.520089 泉州,118.58,24.93 莱西,120.53,36.86 日照,119.46,35.42 胶南,119.97,35.88 南通,121.05,32.08 拉萨,91.11,29.97 云浮,112.02,22.93 梅州,116.1,24.55 文登,122.05,37.2 上海,121.48,31.22 攀枝花,101.718637,26.582347 威海,122.1,37.5 承德,117.93,40.97 厦门,118.1,24.46 汕尾,115.375279,22.786211 潮州,116.63,23.68 丹东,124.37,40.13 太仓,121.1,31.45 曲靖,103.79,25.51 烟台,121.39,37.52 福州,119.3,26.08 瓦房店,121.979603,39.627114 即墨,120.45,36.38 抚顺,123.97,41.97 玉溪,102.52,24.35 张家口,114.87,40.82 阳泉,113.57,37.85 莱州,119.942327,37.177017 湖州,120.1,30.86 汕头,116.69,23.39 昆山,120.95,31.39 宁波,121.56,29.86 湛江,110.359377,21.270708 揭阳,116.35,23.55 荣成,122.41,37.16 连云港,119.16,34.59 葫芦岛,120.836932,40.711052 常熟,120.74,31.64 东莞,113.75,23.04 河源,114.68,23.73 淮安,119.15,33.5 泰州,119.9,32.49 南宁,108.33,22.84 营口,122.18,40.65 惠州,114.4,23.09 江阴,120.26,31.91 蓬莱,120.75,37.8 韶关,113.62,24.84 嘉峪关,98.289152,39.77313 广州,113.23,23.16 延安,109.47,36.6 太原,112.53,37.87 清远,113.01,23.7 中山,113.38,22.52 昆明,102.73,25.04 寿光,118.73,36.86 盘锦,122.070714,41.119997 长治,113.08,36.18 深圳,114.07,22.62 珠海,113.52,22.3 宿迁,118.3,33.96 咸阳,108.72,34.36 铜川,109.11,35.09 平度,119.97,36.77 佛山,113.11,23.05 海口,110.35,20.02 江门,113.06,22.61 章丘,117.53,36.72 肇庆,112.44,23.05 大连,121.62,38.92 临汾,111.5,36.08 吴江,120.63,31.16 石嘴山,106.39,39.04 沈阳,123.38,41.8 苏州,120.62,31.32 茂名,110.88,21.68 嘉兴,120.76,30.77 长春,125.35,43.88 胶州,120.03336,36.264622 银川,106.27,38.47 张家港,120.555821,31.875428 三门峡,111.19,34.76 锦州,121.15,41.13 南昌,115.89,28.68 柳州,109.4,24.33 三亚,109.511909,18.252847 自贡,104.778442,29.33903 吉林,126.57,43.87 阳江,111.95,21.85 泸州,105.39,28.91 西宁,101.74,36.56 宜宾,104.56,29.77 呼和浩特,111.65,40.82 成都,104.06,30.67 大同,113.3,40.12 镇江,119.44,32.2 桂林,110.28,25.29 张家界,110.479191,29.117096 宜兴,119.82,31.36 北海,109.12,21.49 西安,108.95,34.27 金坛,119.56,31.74 东营,118.49,37.46 牡丹江,129.58,44.6 遵义,106.9,27.7 绍兴,120.58,30.01 扬州,119.42,32.39 常州,119.95,31.79 潍坊,119.1,36.62 重庆,106.54,29.59 台州,121.420757,28.656386 南京,118.78,32.04 滨州,118.03,37.36 贵阳,106.71,26.57 无锡,120.29,31.59 本溪,123.73,41.3 克拉玛依,84.77,45.59 渭南,109.5,34.52 马鞍山,118.48,31.56 宝鸡,107.15,34.38 焦作,113.21,35.24 句容,119.16,31.95 北京,116.46,39.92 徐州,117.2,34.26 衡水,115.72,37.72 包头,110,40.58 绵阳,104.73,31.48 乌鲁木齐,87.68,43.77 枣庄,117.57,34.86 杭州,120.19,30.26 淄博,118.05,36.78 鞍山,122.85,41.12 溧阳,119.48,31.43 库尔勒,86.06,41.68 安阳,114.35,36.1 开封,114.35,34.79 济南,117,36.65 德阳,104.37,31.13 温州,120.65,28.01 九江,115.97,29.71 邯郸,114.47,36.6 临安,119.72,30.23 兰州,103.73,36.03 沧州,116.83,38.33 临沂,118.35,35.05 南充,106.110698,30.837793 天津,117.2,39.13 富阳,119.95,30.07 泰安,117.13,36.18 诸暨,120.23,29.71 郑州,113.65,34.76 哈尔滨,126.63,45.75 聊城,115.97,36.45 芜湖,118.38,31.33 唐山,118.02,39.63 平顶山,113.29,33.75 邢台,114.48,37.05 德州,116.29,37.45 济宁,116.59,35.38 荆州,112.239741,30.335165 宜昌,111.3,30.7 义乌,120.06,29.32 丽水,119.92,28.45 洛阳,112.44,34.7 秦皇岛,119.57,39.95 株洲,113.16,27.83 石家庄,114.48,38.03 莱芜,117.67,36.19 常德,111.69,29.05 保定,115.48,38.85 湘潭,112.91,27.87 金华,119.64,29.12 岳阳,113.09,29.37 长沙,113,28.21 衢州,118.88,28.97 廊坊,116.7,39.53 菏泽,115.480656,35.23375 合肥,117.27,31.86 武汉,114.31,30.52 大庆,125.03,46.58";
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


