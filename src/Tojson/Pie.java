package Tojson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import DataPackage.Data;



public class Pie {
	Title title;
	Tooltip tooltip;
	Legend legend;
	Toolbox toolbox;
	boolean calculable;
	List<Series>series;
	public Pie(){
		
	}
	public Pie(Data da){
		title=new Title("");
		tooltip=new Tooltip();
		legend=new Legend(da);
		toolbox=new Toolbox();
		calculable=true;
		series=new ArrayList<Series>();
		series.add(new Series(da));
		
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
	public boolean isCalculable() {
		return calculable;
	}
	public void setCalculable(boolean calculable) {
		this.calculable = calculable;
	}
	public List<Series> getSeries() {
		return series;
	}
	public void setSeries(List<Series> series) {
		this.series = series;
	}
	
	class Title{
		String text;
		String subtext;
		String  x="center";
		
		public Title(String str){
			this.text=new String(str);
			this.subtext=new String("");
			
		}
		public String getX() {
			return x;
		}
		public void setX(String x) {
			this.x = x;
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
		String trigger="item" ;
		String formatter= "{a} <br/>{b} : {c} ({d}%)";

		public String getFormatter() {
			return formatter;
		}

		public void setFormatter(String formatter) {
			this.formatter = formatter;
		}

		public String getTrigger() {
			return trigger;
		}

		public void setTrigger(String trigger) {
			this.trigger = trigger;
		}
	}

	class Legend{
		String orient ="vertical";
		String x = "left";
		List<String>data;
		public Legend(Data da){
			data=new ArrayList<String>();
			for(int i=0;i<da.rowNum;i++){
				data.add(da.Map.get(i).list.get(0));
			}
			
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



}
class Series{
	String name="name";
	String type="pie";
	String radius = "55%";
	List<String>center;
	List<inData>data;
	public Series(Data da){
		center=new ArrayList<String>();
		center.add("50%");
		center.add("60%");
		data=new ArrayList<inData>();
		for(int i=0;i<da.rowNum;i++){
			inData id=new inData(da.Map.get(i).list.get(1), da.Map.get(i).list.get(0));
		    data.add(id);
		}
		
	}
	public Series(){
		
	}
}
class inData{
	String value;
	String name;
	public inData(String V,String N){
		this.value=new String(V);
		this.name=new String(N);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

