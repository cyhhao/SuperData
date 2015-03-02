package Tojson;


import java.util.ArrayList;

import DataPackage.Data;

import com.google.gson.Gson;

import cn.nh121.echarts.legend.Legend;
import cn.nh121.echarts.legend.LegendItem;
import cn.nh121.echarts.polar.Polar;
import cn.nh121.echarts.polar.PolarLabel;
import cn.nh121.echarts.pubenum.EOrient;
import cn.nh121.echarts.series.ASeriesData;
import cn.nh121.echarts.series.ESeriesType;
import cn.nh121.echarts.series.RadarSeries;
import cn.nh121.echarts.title.Title;
import cn.nh121.echarts.toolbox.Toolbox;
import cn.nh121.echarts.toolbox.feature.DataView;
import cn.nh121.echarts.toolbox.feature.Feature;
import cn.nh121.echarts.toolbox.feature.Mark;
import cn.nh121.echarts.toolbox.feature.Restore;
import cn.nh121.echarts.toolbox.feature.SaveAsImage;
import cn.nh121.echarts.tooltip.ETrigger;
import cn.nh121.echarts.tooltip.Tooltip;

public class Radar {
	Title title;
	Tooltip tooltip;
	Legend legend;
	Toolbox toolbox;
	ArrayList<Polar>polar;
	boolean calculable;
	ArrayList<radarSeries>series;
	public Radar(Data da){
		title=new Title();
		title.setText("");
		tooltip=new Tooltip();
		tooltip.setTrigger(ETrigger.AXIS);
		legend=new Legend();
		legend.setOrient(EOrient.VERTICAL);
		legend.setX("right");
		legend.setY("bottom");
		ArrayList<LegendItem>data1;
		data1=new ArrayList<LegendItem>();
		for(int i=1;i<da.colNum;i++){
			data1.add(new LegendItem().setName(da.Head.get(i)));
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
		saveAsImage.setLang("µã»÷±£´æ");
		Feature feature=new Feature();
		feature.setMark(mark);
		feature.setDataView(dataView);
		feature.setRestore(restore);
		feature.setSaveAsImage(saveAsImage);
		// toolbox
		toolbox=new  Toolbox();
		toolbox.setShow(true);
		toolbox.setFeature(feature);
		//polor
		polar=new ArrayList<Polar>();
		Polar po=new Polar();
		ArrayList<PolarLabel>indicator=new ArrayList<PolarLabel>();
		for(int i=0;i<da.rowNum;i++){
			PolarLabel pl=new PolarLabel();
			pl.setMax(Integer.valueOf(da.Map.get(i).list.get(da.colNum-1)));
			pl.setText(da.Map.get(i).list.get(0));
			indicator.add(pl);
		}
		po.setIndicator(indicator);
		polar.add(po);
		//cal
		calculable=true;
		//series
		series=new ArrayList<radarSeries>();
		radarSeries rs=new radarSeries();
		rs.setName("name");
		rs.setType("radar");
		ArrayList<radarData>data_tmp=new ArrayList<radarData>();
		double []v=new double[da.rowNum];
		System.out.println(da.colNum);
		for(int i=1;i<da.colNum-1;i++){
			v=new double[da.rowNum];
			for(int j=0;j<da.rowNum;j++){
				v[j]=Double.valueOf(da.Map.get(j).list.get(i));
			}
			radarData rd=new radarData();
			
			rd.setValue(v);
			rd.setName(da.Head.get(i));
			data_tmp.add(rd);

		}
		//System.out.println(data_tmp.get(1).getValue()[0]);
		rs.setData(data_tmp);
		series.add(rs);
		
		
			
		
	}
	public String toString(){
    	Gson g=new Gson();
    	String str=g.toJson(this);
    	return str;
    	
    }

	

}

class radarSeries{
	String name;
	String type;
	ArrayList<radarData>data;
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
	public ArrayList<radarData> getData() {
		return data;
	}
	public void setData(ArrayList<radarData> data) {
		this.data = data;
	}
	
	
}
class radarData{
	double[] value;
	
	String name;
	
	public double[] getValue() {
		return value;
	}
	public void setValue(double[] value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
