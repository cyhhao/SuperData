package assistFunction;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Get_ip {
	public static String get_host()
	{
		String host="";
		
		Enumeration allNetInterfaces;
		try 
		{
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			int i=0;
			while (allNetInterfaces.hasMoreElements())
			{
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				//System.out.println(netInterface.getName());
				Enumeration addresses = netInterface.getInetAddresses();
				
				while (addresses.hasMoreElements())
				{
					
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address)
					{
						i++;
						System.out.println("±¾»úµÄIP = " + ip.getHostAddress());
						if(i==2) host=ip.getHostAddress();
					} 
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return host;
	}
	
	
	public static String get_win_host()
	{

		 String str="";
		try 
		{
			InetAddress addr = InetAddress.getLocalHost();
			str=addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;

	}
	
	public static String get_url()
	{
		String url="http://"+get_host();
		return url;
	}
	
}
