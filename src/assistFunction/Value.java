package assistFunction;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Value {
	public static String dd="[{'001011':'����'},{'001012':'���'},{'001013':'�ӱ�'},{'001014':'ɽ��'},{'001015':'���ɹ�'},{'001021':'����'},{'001022':'����'},{'001023':'������'},{'001031':'�Ϻ�'},{'001032':'����'},{'001033':'�㽭'},{'001034':'����'},{'001035':'����'},{'001036':'����'},{'001037':'ɽ��'},{'001041':'����'},{'001042':'����'},{'001043':'����'},{'001044':'�㶫'},{'001045':'����'},{'001046':'����'},{'001050':'����'},{'001051':'�Ĵ�'},{'001052':'����'},{'001053':'����'},{'001054':'����'},{'001061':'����'},{'001062':'����'},{'001063':'�ຣ'},{'001064':'����'},{'001065':'�½�'},{'001071':'̨��'},{'001081':'���'},{'001082':'����'}]";
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
