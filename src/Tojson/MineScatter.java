package Tojson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import DataPackage.Data;
import DataPackage.RserveBegin;
import cn.nh121.echarts.axis.Axis;
import cn.nh121.echarts.axis.EAxisType;
import cn.nh121.echarts.core.XAxes;
import cn.nh121.echarts.core.YAxes;
import cn.nh121.echarts.legend.Legend;
import cn.nh121.echarts.legend.LegendItem;
import cn.nh121.echarts.series.ASeries;
import cn.nh121.echarts.series.ASeriesData;
import cn.nh121.echarts.series.CartesianSeries;
import cn.nh121.echarts.series.ESeriesType;
import cn.nh121.echarts.style.AreaStyle.EType;
import cn.nh121.echarts.style.ItemStyle;
import cn.nh121.echarts.style.LineStyle;
import cn.nh121.echarts.style.LineStyle.ELineType;
import cn.nh121.echarts.style.itemstyle.Normal;
import cn.nh121.echarts.title.Title;
import cn.nh121.echarts.toolbox.Toolbox;
import cn.nh121.echarts.toolbox.feature.DataView;
import cn.nh121.echarts.toolbox.feature.DataZoom;
import cn.nh121.echarts.toolbox.feature.Feature;
import cn.nh121.echarts.toolbox.feature.Mark;
import cn.nh121.echarts.toolbox.feature.Restore;
import cn.nh121.echarts.toolbox.feature.SaveAsImage;
import cn.nh121.echarts.tooltip.AxisPointer;
import cn.nh121.echarts.tooltip.ETrigger;
import cn.nh121.echarts.tooltip.Tooltip;



public class MineScatter {
	@Override
	public String toString() {
		Gson gson=new Gson();
		String str=gson.toJson(this);
		return str;
		
	}
	Title title;
	Tooltip tooltip;
	Legend legend;
	Toolbox toolbox;
	Axis xAxis;
	Axis yAxis;
	
	List<CartesianSeries> series;
	public Title getTile() {
		return title;
	}
	public void setTile(Title tile) {
		this.title = tile;
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
	public Axis getxAxis() {
		return xAxis;
	}
	public void setxAxis(Axis xAxis) {
		this.xAxis = xAxis;
	}
	public Axis getyAxis() {
		return yAxis;
	}
	public void setyAxis(Axis yAxis) {
		this.yAxis = yAxis;
	}
	public List<CartesianSeries> getSeries() {
		return series;
	}
	public void setSeries(List<CartesianSeries> series) {
		this.series = series;
	}
	
	public MineScatter(Data data,RserveBegin Rdata)
	{
		title=new Title();
		title.setText(data.onlyFileName);
		title.setX(new String("center"));
		
		tooltip=new Tooltip();
		tooltip.setTrigger(ETrigger.AXIS);
		tooltip.setShowDelay(200);
		AxisPointer axp=new AxisPointer();
		axp.setType(cn.nh121.echarts.tooltip.AxisPointer.EType.CROSS);
		axp.setLineStyle(new LineStyle().setType(ELineType.DASHED).setWidth(1));	
		tooltip.setAxisPointer(axp);
		
		legend=new Legend();
		ArrayList<LegendItem> legendlist=new ArrayList<LegendItem>();
		for(int i=0;i<Rdata.clusterCount.length;i++)
		{
			legendlist.add(new LegendItem().setName("第"+i+"类"));
		}
		legend.setData(legendlist);
		
		toolbox=new Toolbox();
		toolbox.setShow(true);
		Feature feature=new Feature();
		feature.setMark(new Mark().setShow(true));
		feature.setDataZoom(new DataZoom().setShow(true));
		feature.setDataView(new DataView().setShow(true).setReadOnly(true));
		feature.setRestore(new Restore().setShow(true));
		feature.setSaveAsImage(new SaveAsImage().setShow(true));
		toolbox.setFeature(feature);
		
		xAxis=new Axis();
		xAxis.setType(EAxisType.value);
		xAxis.setPower(1);
		xAxis.setPrecision(2);
		xAxis.setScale(true);
		yAxis=new Axis();
		yAxis.setType(EAxisType.value);
		yAxis.setPower(1);
		yAxis.setPrecision(2);
		yAxis.setScale(true);
		
		xAxis.setName(data.Head.get(0));
		yAxis.setName(data.Head.get(1));
		System.out.println(new Gson().toJson(data.Head));
		
		
		series=new ArrayList<CartesianSeries>();
		
		for(int i=0;i<Rdata.arrCluster.length;i++)
		{
			CartesianSeries cs=new CartesianSeries();
			cs.setName("第"+i+"类");
			cs.setType(ESeriesType.SCATTER);
			
			ArrayList<ASeriesData> aslist=new ArrayList<ASeriesData>();
			
			for(int j=0;j<data.rowNum;j++)
			{
				if(Rdata.arrCluster[j]==i)
				{
					
					ScatterData asdata=new ScatterData();
					
					ASeriesData ddd=new ASeriesData() {
						
					};
					
					Double[] xy=new Double [2];
					
					xy[0]=Double.valueOf(data.getElement(j,0));
					xy[1]=Double.valueOf(data.getElement(j,1));
					
					asdata.setValue(xy);
					
					
					/*
					ASeriesData asdata=new ASeriesData(){};
					asdata.setValue("[1,5]");
					*/

					aslist.add(asdata);
					//Gson gson=new Gson();
					//String str=gson.toJson(asdata);
					
					//System.out.println(str);
				}
			}
			cs.setData(aslist);
			
			series.add(cs);
		}
	}
	
	public class ScatterData extends ASeriesData{
			@Override
			public Double[] getValue() {
				
				return (Double[])super.getValue();
			}

			@Override
			public ASeriesData setValue(Object value) {
				value=(Double[])value;
				return super.setValue(value);
			}
	}
	
}
