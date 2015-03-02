package Tojson;



import java.util.ArrayList;

import DataPackage.RserveBegin;

import com.google.gson.Gson;

import cn.nh121.echarts.axis.Axis;
import cn.nh121.echarts.axis.AxisLabel;
import cn.nh121.echarts.axis.EAxisPosition;
import cn.nh121.echarts.axis.EAxisType;
import cn.nh121.echarts.axis.SplitLine;
import cn.nh121.echarts.grid.Grid;
import cn.nh121.echarts.legend.Legend;
import cn.nh121.echarts.legend.LegendItem;
import cn.nh121.echarts.title.Title;
import cn.nh121.echarts.toolbox.Toolbox;
import cn.nh121.echarts.toolbox.feature.DataView;
import cn.nh121.echarts.toolbox.feature.Feature;
import cn.nh121.echarts.toolbox.feature.Mark;
import cn.nh121.echarts.toolbox.feature.Restore;
import cn.nh121.echarts.toolbox.feature.SaveAsImage;
import cn.nh121.echarts.tooltip.AxisPointer;
import cn.nh121.echarts.tooltip.AxisPointer.EType;
import cn.nh121.echarts.tooltip.ETrigger;
import cn.nh121.echarts.tooltip.Tooltip;

public class Association {
    Title title;
    Tooltip tooltip;
    Legend legend;
    Toolbox toolbox;
    myGrid grid;
    Axis xAxis;
    Axis yAxis;
    ArrayList<asocSeries>series;
    public Association(RserveBegin da){
    	title=new Title();
    	title.setText("");
    	tooltip=new Tooltip();
    	tooltip.setTrigger(ETrigger.AXIS);
    	tooltip.setAxisPointer(new AxisPointer().setType(EType.SHADOW));
    	tooltip.setFormatter( "{b}<br/>{a0}:{c0}%<br/>{a2}:{c2}%<br/>{a4}:{c4}%<br/>{a6}:{c6}%");
        legend=new Legend();
        int []dataLeft=new int[da.apItemList.size()];
        int []dataRight=new int[da.apItemList.size()];
        for(int i=0;i<da.apItemList.size();i++){
        	dataLeft[i]=dataRight[i]=0;
        }
        for(int i=0;i<da.arrLeft.length;i++){      //统计出现的关联种类
        	dataLeft[da.arrLeft[i]]=1;
        	dataRight[da.arrRight[i]]=1;
        }
        ArrayList<Integer >left=new ArrayList<Integer>();
        ArrayList<Integer> right=new ArrayList<Integer>();
        for(int i=0;i<dataLeft.length;i++){            //将左右两边的关联种类分别写为List
        	if(dataLeft[i]==1){
        		left.add(i);
        	}
        	if(dataRight[i]==1){
        		right.add(i);
        	}
        }
        double [][]result=new double[da.apItemList.size()][da.apItemList.size()];//
        for(int i=0;i<da.apItemList.size();i++){
        	for(int j=0;j<da.apItemList.size();j++){
        		result[i][j]=0;
        	}
        }
        for(int i=0;i<da.arrLeft.length;i++){
        	result[da.arrLeft[i]][da.arrRight[i]]=da.support[i];
        }
        double supportMax=da.support[0];
        for(int i=1;i<da.support.length;i++){
        	if(da.support[i]>supportMax){
        		supportMax=da.support[i];
        	}
        }
        
        
        ArrayList<LegendItem>data1;
        data1=new ArrayList<LegendItem>();
		for(int i=0;i<left.size();i++){
	
				data1.add(new LegendItem().setName(da.apItemList.get(left.get(i))));
	
		}
		legend.setData(data1);
		//
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
			toolbox.setShow(true);
			toolbox.setFeature(feature);
			grid=new myGrid();
			xAxis=new Axis();
			xAxis.setType(EAxisType.value);
			xAxis.setPosition(EAxisPosition.top);
			xAxis.setSplitLine(new SplitLine().setShow(false));
			xAxis.setAxisLabel(new AxisLabel().setShow(false));
			yAxis=new Axis();
			yAxis.setType(EAxisType.category);
			yAxis.setSplitLine(new SplitLine().setShow(false));
			ArrayList<Object>arr=new ArrayList<Object>();
			for(int i=0;i<right.size();i++){
				arr.add(da.apItemList.get(right.get(i)));
			}
			yAxis.setData(arr);
			series=new ArrayList<asocSeries>();
			for(int i=0;i<left.size();i++){
				asocSeries ser1=new asocSeries(2);
				asocSeries ser2=new asocSeries(1);
				ser1.setName(da.apItemList.get(left.get(i)));
				ser2.setName(da.apItemList.get(left.get(i)));
				ArrayList<Integer>da1=new ArrayList<Integer>();
				ArrayList<Integer>da2=new ArrayList<Integer>();
				for(int j=0;j<right.size();j++){
					da1.add((int)(result[left.get(i)][right.get(j)]/supportMax*100));
					da2.add(100-(int)(result[left.get(i)][right.get(j)]/supportMax*100));
				}
				ser1.setData(da1);
				ser2.setData(da2);
				System.out.println(ser1.itemStyle);
				series.add(ser1);
				series.add(ser2);
			}
		
        
    }
    public String toString(){
     	Gson g=new Gson();
     	String str=g.toJson(this);
     	return str;
     	
     }

    
}

class myGrid{
	int y=80;
	int y2=30;
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getY2() {
		return y2;
	}
	public void setY2(int y2) {
		this.y2 = y2;
	}
	
}
class asocSeries{
	String name;
	String type="bar";
	String stack="总量";
	asItemStyle  itemStyle;
	
	ArrayList<Integer>data=new ArrayList<Integer>();
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
	public String getStack() {
		return stack;
	}
	public void setStack(String stack) {
		this.stack = stack;
	}
	
	
	public ArrayList<Integer> getData() {
		return data;
	}
	public void setData(ArrayList<Integer> data) {
		this.data = data;
	}
	public asocSeries(int x){
		itemStyle=new asItemStyle();
		if(x==1){
			itemStyle.normal=new Normal();
		    itemStyle.normal.setBorderColor("rgba(0,0,0,0)");
		    itemStyle.normal.setColor("rgba(0,0,0,0)");
		    itemStyle.emphasis=new Emphasis();	
		}
		if(x==2){
			itemStyle.normal=new Normal();
			itemStyle.normal.label=new Normal().new Label();
		}
	}
	class asItemStyle{
		Emphasis emphasis;
		Normal normal;
	}
	
	 class Emphasis{
        String borderColor="rgba(0,0,0,0)";
        String color="rgba(0,0,0,0)";
    }
	class  Normal{
        String borderColor;
        String color;
        public String getBorderColor() {
			return borderColor;
		}
		public void setBorderColor(String borderColor) {
			this.borderColor = borderColor;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public Label getLabel() {
			return label;
		}
		public void setLabel(Label label) {
			this.label = label;
		}
		Label label;
        class Label{
        	 boolean show= true;
             String position="insideLeft";
             String formatter= "{c}%";
        }
    }
	
	
}
