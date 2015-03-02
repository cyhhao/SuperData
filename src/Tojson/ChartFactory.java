package Tojson;

import weka.filters.unsupervised.instance.ReservoirSample;
import DataPackage.Data;
import DataPackage.RserveBegin;

public class ChartFactory {
	public static Object creator(String chart,Data data)
	{
		
		if(chart.equalsIgnoreCase("line"))
		{
			return new Line(data);
		}
		else if(chart.equalsIgnoreCase("bar"))
		{
			return new Bar(data);
		}
		else if(chart.equalsIgnoreCase("scatter"))
		{
			return new Scatter(data);
		}
		else if(chart.equalsIgnoreCase("pie"))
		{
			return new Pie(data);
		}
		else if(chart.equalsIgnoreCase("map"))
		{
			return new MapChart(data);
		}
		else if(chart.equalsIgnoreCase("radar"))
		{
			return new Radar(data);
		}
		else if(chart.equalsIgnoreCase("wordcloud"))
		{
			return new WordCloud(data);
		}
		else if(chart.equalsIgnoreCase("timelinePie"))
		{
			return new timelinePie(data);
		}
		return null;
		
	}
	
	public static Object Minecreator(String chart,Data data,RserveBegin Rdata)
	{
		if(chart.equalsIgnoreCase("scatter"))
		{
			return new MineScatter(data,Rdata);
		}
		else if(chart.equalsIgnoreCase("pie"))
		{
			return new MinePie(data,Rdata);
		}
		return null;
	}
}
