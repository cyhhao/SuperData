package Tojson;

import java.util.ArrayList;

import jxl.common.LengthConverter;

import DataPackage.Data;

import com.google.gson.Gson;



import cn.nh121.echarts.datarange.DataRange;
import cn.nh121.echarts.legend.Legend;
import cn.nh121.echarts.legend.LegendItem;
import cn.nh121.echarts.pubenum.EOrient;
import cn.nh121.echarts.series.MapSeries;
import cn.nh121.echarts.title.Title;
import cn.nh121.echarts.toolbox.Toolbox;
import cn.nh121.echarts.toolbox.feature.DataView;
import cn.nh121.echarts.toolbox.feature.Feature;
import cn.nh121.echarts.toolbox.feature.MagicType;
import cn.nh121.echarts.toolbox.feature.Mark;
import cn.nh121.echarts.toolbox.feature.Restore;
import cn.nh121.echarts.toolbox.feature.SaveAsImage;
import cn.nh121.echarts.toolbox.feature.MagicType.EMagicType;
import cn.nh121.echarts.tooltip.ETrigger;
import cn.nh121.echarts.tooltip.Tooltip;

public class MapChart {
	Title title;
	Tooltip tooltip;
	Legend legend;
	DataRange dataRange;
	Toolbox toolbox;
	ArrayList<mapSeries>series;
	
	public MapChart(Data da){
		//title
		title=new Title();
		title.setText(da.onlyFileName);
		title.setSubText("");
		title.setX("center");
		//tooltip
		tooltip=new Tooltip();
		tooltip.setTrigger(ETrigger.ITEM);
		//legend
		legend=new Legend();
		legend.setOrient(EOrient.VERTICAL);
		legend.setX("left");
		ArrayList<LegendItem>data1;
		data1=new ArrayList<LegendItem>();
		for(int i=1;i<da.colNum;i++){
			data1.add(new LegendItem().setName(da.Head.get(i)));
		}
		legend.setData(data1);
		
		//datarange
		dataRange=new DataRange();
		dataRange.setMin(0);
		dataRange.setMax(2500);
		dataRange.setX("left");
		dataRange.setY("bottom");
		dataRange.setText("¸ß", "µÍ");
		dataRange.setCalculable(true);
		
		//toolbox.feature
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
		saveAsImage.setLang("µã»÷±£´æ");
		Feature feature=new Feature();
		feature.setMark(mark);
		feature.setDataView(dataView);
		feature.setRestore(restore);
		feature.setSaveAsImage(saveAsImage);
		//toolbox
		toolbox=new Toolbox();
		toolbox.setShow(true);
		toolbox.setOrient(EOrient.VERTICAL);
		toolbox.setX("right");
		toolbox.setY("center");
		toolbox.setFeature(feature);
		
		//series
		series=new ArrayList<mapSeries>();
        mapSeries mapseries = null;
        
        for(int i=1;i<da.colNum;i++){
        	mapseries=new mapSeries();
        	mapseries.setName(da.Head.get(i));
        	ArrayList<mapData>data=new ArrayList<mapData>();
        	for(int j=0;j<da.rowNum;j++){
        		mapData mapdata=new mapData(da.Map.get(j).list.get(0), Long.valueOf(da.Map.get(j).list.get(i)));
        		data.add(mapdata);
        	}
        	mapseries.setData(data);
        	series.add(mapseries);
        }
		
	}
	public static void main(String []args){
		Data da=new Data();
		MapChart mc=new MapChart(da);
		System.out.println(mc.toString());
	}
	public String toString(){
    	Gson g=new Gson();
    	String str=g.toJson(this);
    	return str;
    	
    }
	

}

class mapSeries{
	String name;
	String type="map";
	String mapType="china";
	boolean roam=true;
	ItemStyle itemStyle=new ItemStyle();
	ArrayList<mapData>data;
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
	public boolean isRoam() {
		return roam;
	}
	public void setRoam(boolean roam) {
		this.roam = roam;
	}
	public ItemStyle getItemStyle() {
		return itemStyle;
	}
	public void setItemStyle(ItemStyle itemStyle) {
		this.itemStyle = itemStyle;
	}
	public ArrayList<mapData> getData() {
		return data;
	}
	public void setData(ArrayList<mapData> data) {
		this.data = data;
	}	
}
class mapData{
	String name;
	long value;
	public mapData(String name,long l){
		this.name=name;
		this.value=l;
	}
}
class ItemStyle{
	Normal normal=new Normal();
	Emphasis emphasis=new Emphasis();
	
}
class Normal{
	Label label=new Label();
	
}
class Emphasis{
	Label label=new Label();
}
class Label{
	boolean show=true;
}
