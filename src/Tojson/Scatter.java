package Tojson;

import java.util.ArrayList;
import java.util.List;

import DataPackage.Data;

import com.google.gson.Gson;


 

public class Scatter {
	Title title ;
	Tooltip tooltip;
	Legend legend;
	Toolbox toolbox;
	XAxis xAxis;
	YAxis yAxis;
	List<inseries>series;
	public Scatter(Data da){
		title=new Title(da.onlyFileName);
		tooltip=new Tooltip();
		toolbox=new Toolbox();
		legend=new Legend(da.Head);
		xAxis=new XAxis();
		xAxis.setName(da.Head.get(0));
		yAxis=new YAxis();
                yAxis.setName(da.Head.get(1));
		series=new ArrayList<inseries>();
		series.add(new inseries("年龄身高", da));	
	}
	public String toString(){
		Gson g=new Gson();
		String str=g.toJson(this);
		return str;
	}
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
	public List<inseries> getSeries() {
		return series;
	}
	public void setSeries(List<inseries> series) {
		this.series = series;
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
		int showDelay=0;
		AxisPointer axisPointer=new AxisPointer();
		class AxisPointer{
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			public LineStyle getLineStyle() {
				return lineStyle;
			}
			public void setLineStyle(LineStyle lineStyle) {
				this.lineStyle = lineStyle;
			}
			String type="cross";
			LineStyle lineStyle=new LineStyle();
			class LineStyle{
				String type="dashed";
				int width=1;
				public String getType() {
					return type;
				}
				public void setType(String type) {
					this.type = type;
				}
				public int getWidth() {
					return width;
				}
				public void setWidth(int width) {
					this.width = width;
				}
			}
		}
		public String getTrigger() {
			return trigger;
		}
		public void setTrigger(String trigger) {
			this.trigger = trigger;
		}
		public int getShowDelay() {
			return showDelay;
		}
		public void setShowDelay(int showDelay) {
			this.showDelay = showDelay;
		}
		public AxisPointer getAxisPointer() {
			return axisPointer;
		}
		public void setAxisPointer(AxisPointer axisPointer) {
			this.axisPointer = axisPointer;
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
		class Feature{
			Mark mark=new  Mark();
			DataZoom dataZoom=new DataZoom();
			DataView dataView=new DataView();
			Restore restore=new Restore();
			SaveAsImage saveAsImage=new SaveAsImage();
			class Mark{
				boolean show=true;

				public boolean isShow() {
					return show;
				}

				public void setShow(boolean show) {
					this.show = show;
				}
			}
			class DataZoom{
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
			public DataZoom getDataZoom() {
				return dataZoom;
			}
			public void setDataZoom(DataZoom dataZoom) {
				this.dataZoom = dataZoom;
			}
			public DataView getDataView() {
				return dataView;
			}
			public void setDataView(DataView dataView) {
				this.dataView = dataView;
			}
			public Restore getRestore() {
				return restore;
			}
			public void setRestore(Restore restore) {
				this.restore = restore;
			}
			public SaveAsImage getSaveAsImage() {
				return saveAsImage;
			}
			public void setSaveAsImage(SaveAsImage saveAsImage) {
				this.saveAsImage = saveAsImage;
			}
			
		}
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

	class XAxis{ 
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
                String name;
		String type="value";
		int power=1;
		int precision=2;
		boolean scale=true;
		
		AxisLabel axisLabel=new AxisLabel();
		class AxisLabel{
			String formatter="{value}";

			public String getFormatter() {
				return formatter;
			}

			public void setFormatter(String formatter) {
				this.formatter = formatter;
			}
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getPower() {
			return power;
		}
		public void setPower(int power) {
			this.power = power;
		}
		public int getPrecision() {
			return precision;
		}
		public void setPrecision(int precision) {
			this.precision = precision;
		}
		public boolean isScale() {
			return scale;
		}
		public void setScale(boolean scale) {
			this.scale = scale;
		}
		public AxisLabel getAxisLabel() {
			return axisLabel;
		}
		public void setAxisLabel(AxisLabel axisLabel) {
			this.axisLabel = axisLabel;
		}
		
	}

	class YAxis{
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
                String name;
		String type="value";
		int power=1;
		int precision=2;
		boolean scale=true;
		AxisLabel axisLabel=new AxisLabel();
		class AxisLabel{
			String formatter="{value}";

			public String getFormatter() {
				return formatter;
			}

			public void setFormatter(String formatter) {
				this.formatter = formatter;
			}
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getPower() {
			return power;
		}
		public void setPower(int power) {
			this.power = power;
		}
		public int getPrecision() {
			return precision;
		}
		public void setPrecision(int precision) {
			this.precision = precision;
		}
		public boolean isScale() {
			return scale;
		}
		public void setScale(boolean scale) {
			this.scale = scale;
		}
		public AxisLabel getAxisLabel() {
			return axisLabel;
		}
		public void setAxisLabel(AxisLabel axisLabel) {
			this.axisLabel = axisLabel;
		}
		
	}

	class inseries{
		String name;
		String type="scatter";
		List< List<Double> >data; 
		MarkPoint markPoint=new MarkPoint();
		MarkLine markline=new MarkLine();
		public inseries(String N,Data da){
			this.name=new String(N);
			data=new ArrayList<List<Double>>();
			List<Double>indata = null;
			for(int i=0;i<da.rowNum;i++){
				indata=new ArrayList<Double>();
				for(int j=0;j<da.colNum;j++){
				    
					indata.add( Double.valueOf(da.Map.get(i).list.get(j))  );
					
				}
				System.out.println(indata);
				data.add(indata);
			}
		}	
		class MarkPoint{
			List<Data_oth>data;
			public MarkPoint(){
				data=new ArrayList<Data_oth>();
				data.add(new Data_oth("max","最大值"));
				data.add(new Data_oth("min","最小值"));
				
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
			public List<Data_oth> getData() {
				return data;
			}
			public void setData(List<Data_oth> data) {
				this.data = data;
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
		public List<List<Double>> getData() {
			return data;
		}
		public void setData(List<List<Double>> data) {
			this.data = data;
		}
		public MarkPoint getMarkPoint() {
			return markPoint;
		}
		public void setMarkPoint(MarkPoint markPoint) {
			this.markPoint = markPoint;
		}
		public MarkLine getMarkline() {
			return markline;
		}
		public void setMarkline(MarkLine markline) {
			this.markline = markline;
		}
	  
	}
	
	

}
