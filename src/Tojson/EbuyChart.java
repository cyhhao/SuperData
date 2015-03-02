package Tojson;

import java.io.IOException;

import DataPackage.Data;
import DataPackage.RserveBegin;

public class EbuyChart {
	public static Object ChartFactorycreater(String chart,RserveBegin rdata)
	{
		if(chart.equalsIgnoreCase("line"))
		{
			return new EbuyLine(rdata);
		}
		else if(chart.equalsIgnoreCase("map"))
		{
			try 
			{
				return new markPointChart(rdata);
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(chart.equalsIgnoreCase("Association"))
		{
			return new Association(rdata);
		}
		return null;
	}
}
