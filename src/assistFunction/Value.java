package assistFunction;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Value {
	public static String dd="[{'001011':'北京'},{'001012':'天津'},{'001013':'河北'},{'001014':'山西'},{'001015':'内蒙古'},{'001021':'辽宁'},{'001022':'吉林'},{'001023':'黑龙江'},{'001031':'上海'},{'001032':'江苏'},{'001033':'浙江'},{'001034':'安徽'},{'001035':'福建'},{'001036':'江西'},{'001037':'山东'},{'001041':'河南'},{'001042':'湖北'},{'001043':'湖南'},{'001044':'广东'},{'001045':'广西'},{'001046':'海南'},{'001050':'重庆'},{'001051':'四川'},{'001052':'贵州'},{'001053':'云南'},{'001054':'西藏'},{'001061':'陕西'},{'001062':'甘肃'},{'001063':'青海'},{'001064':'宁夏'},{'001065':'新疆'},{'001071':'台湾'},{'001081':'香港'},{'001082':'澳门'}]";
	Map<String ,String > provinceMap;
	
	public static String RreadDataPath="H:/kc2.0/myeclipse/cnsoftbei/path/nameOfFileToBeMined.txt";
	public static String scriptRootPath = "H:/kc2.0/myeclipse/cnsoftbei/path/";
	
	public Map<String ,String > getProvinceMap()
	{
		provinceMap=new HashMap<String, String>();
		Gson gson=new Gson();
		return gson.fromJson(dd,new TypeToken<Map<String ,String >>(){}.getType());
	}
}
