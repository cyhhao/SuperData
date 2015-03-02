package DataPackage;



import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import weka.associations.Apriori;
import weka.associations.AprioriItemSet;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;

import com.csvreader.CsvReader;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import assistFunction.Value;
import assistFunction.assistFunc;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class RserveBegin {  
	public HashMap <String,Integer> haMap;
	public HashMap<String, Integer> stopMap;
	public HashMap< String, ItemList > itemStaMap_time; // ͳ��ÿ����Ʒ������ͼ
	public HashMap< String, ItemList > itemStaMap_location; // ͳ��ÿ����Ʒ������ͼ
	public List<SortItem> sortList; // ������callscript���õ���������ķֳ����Ĵʵ��б�
	public static List<String> itemList;
	public static RConnection rc=null;
	public int[] arrLeft;			 // ������apriori��ÿ�������������
	public int[] arrRight;			 // ������apriori��ÿ�������Ҳ�����
	public int[] arrCluster;         // 0,1,2,1,2,0,0,1,2
	public int[] clusterCount;        // [0]�м���
	public List<String> apItemList;
	public double[] support;
	
	
	public String filepath;
	public String onlyFileName;
	
	public HashMap<String,String> existedMap;
	
	public class ItemListNode
	{
		public String info;		// ���������Ʒ������
		public int time; // ��������ڳ��ֵĴ���
		ItemListNode( String d, int t )
		{
			info = d;
			time = t;
		}
		public void Add()
		{
			time ++;
		}
	}
	
	public class ItemList
	{
		public List< ItemListNode > list;
		public ItemList()
		{
			list = new ArrayList();
		}
		void Push( String d )   // ���ж���������Ƿ���֣������ֹ�time++����û�г��ֹ���add��ȥ
		{
			int i;
			for( i = 0 ; i < list.size() ; i ++ ){
				if( d.equals( list.get(i).info ) ){
			//		list.set( i , new ItemListNode( d, list.get(i).time + 1  ));
					list.get(i).Add();
					break;
				}
			}
			if( i >= list.size() ){
				list.add( new ItemListNode( d, 1 ) );
			}
		}
		void Print()
		{
		
			for( int i = 0 ; i < list.size() ; i ++ ){
				System.out.print( list.get(i).info + " " + list.get( i ).time );
			}
			System.out.println();
		}
	}
	
	public RserveBegin() 
	{
		try 
		{
			System.out.println("***connection*************************");
			if(rc==null) rc = new RConnection();
			System.out.println("***connectioned*************************");
		} 
		catch (RserveException e) 
		{
			System.out.println("***error*************************");
			e.printStackTrace();
		}
		haMap = new HashMap<String, Integer>();  
        stopMap = new HashMap<String,Integer>();
		List<String> stringList = new ArrayList();
		sortList = new ArrayList();
		itemStaMap_time = new HashMap<String,ItemList>();
		itemStaMap_location = new HashMap<String,ItemList>();
		apItemList = new ArrayList<String>();
		existedMap = new HashMap<String, String>();
	}
	public void calcStaForEachItem(String filepath) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		BufferedWriter writer = new BufferedWriter(new FileWriter(Value.scriptRootPath+"output.csv"));
		
		String str;
		String[] strArr;
		ItemList loc;
		while (( str = reader.readLine()) != null) {
			strArr = str.split(",");
			for( int i = 3 ; i < strArr.length ; i ++ ){
				if( i != 3 ){
					writer.write(",");
				}
				writer.write(strArr[i]);
			
				if( itemStaMap_time.get( strArr[i] ) == null ){
	 				itemStaMap_time.put( strArr[ i ], new ItemList());
	 			}
	 			ItemList val = itemStaMap_time.get( strArr[ i ] );
	 			val.Push( strArr[ 0 ] );
	 	//		itemStaMap_time.put( strArr[ i ], val );
				if( itemStaMap_location.get( strArr[ i ] ) == null ){
					itemStaMap_location.put( strArr[ i ] , new ItemList());
				}
				loc = itemStaMap_location.get( strArr[ i ] );
				loc.Push( strArr[ 2 ] );
		//		itemStaMap_location.put(strArr[i],loc);
			}
			writer.write("\r\n");
            // ��ǰ���зֳ���
			// ˳��Ѻ���Ĵ浽����һ���ļ�
			//��ÿһ����Ʒ��map�������map��Ͱ�ʱ��push��ȥ���������map�������
        }
		Set<String> set = itemStaMap_location.keySet();
		
		for (String ch : set) {
			System.out.println( ch + ":" );
			itemStaMap_location.get(ch).Print();
			
		}
		writer.close();
	}
	
	
	
	public void setFileName( String fileName,String only) throws IOException // ��Ϊû����Java��R���Դ��������Ƚ�Ҫ�趨���ļ���д��һ���ļ��У�����R���Դ��ļ��аѵ�ǰҪ������ļ���������
	{							// �ٽ��д���
		filepath=fileName;
		onlyFileName=only;
		
		BufferedWriter output = new BufferedWriter(new FileWriter(Value.RreadDataPath));
		output.write(fileName);
		output.close();
		
	}
	
	
	public static void getRandomGroceires()			// ����169��Item��������ɹ����������У�Ϊ����������׼��
	{
		try {
			itemList = new ArrayList();
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream("F:\\workspace\\Gro.csv"),"UTF-8"));
			String cur = null;
            while((cur = reader.readLine()) != null){
            	itemList.add(cur);
            }
            Random r=new Random();
            for( int i = 0 ; i < 100 ; i ++ ){
           // 	System.out.println( i + ":");
            	boolean[] hasGotten = new boolean[ 1000 ];
            	int rand;
            	int bond = Math.abs( r.nextInt() ) % 10 + 1;
            	for(int j=0;j<bond;j++){ 
            		if( j != 0 ){
            			System.out.print(",");
            		}
            	//	String prt = itemList.get( Math.abs( r.nextInt() % itemList.size() ) );
            		//prt = prt.replace(" ", "");
            		rand = Math.abs( r.nextInt() % itemList.size() );
            		while( hasGotten[ rand ] ){
            			rand =Math.abs( r.nextInt() % itemList.size() );
            		}
            		hasGotten[ rand ] = true;
            		String prt = "I" + rand;
            		System.out.print( prt ); 
            	}
            	System.out.println();
            }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
/*
    public static void main(String[]args) {  
        try {  
        	RserveBegin sb = new RserveBegin();
        	sb.Apriori();
        	//	getRandomGroceires();
          //sb.callRScript();		// �ִ�
        //    sb.Apriori();			// ��������
        	//getCluster();				
  //      	sb.calcStaForEachItem();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    */
    public class SortItem
    {
    	String key;
    	Integer value;
    	public SortItem( String I, Integer v )
    	{
    		this.key = I;
    		this.value = v;
    	}
    	public String getItem()
    	{
    		return key;
    	}
    	public void setItem( String str )
    	{
    		this.key = str;
    	}
    	public int getValue()
    	{	
    		return this.value;
    	}
    	public void setValue( Integer v )
    	{
    		this.value = v;
    	}
    }
    
    
    
    
    
    public class ComparatorItem implements Comparator{

    	 public int compare(Object arg0, Object arg1) {
    		 
    		 SortItem item0=(SortItem)arg0;
    		 SortItem item1=(SortItem)arg1;
    		 if( item0.getValue() < item1.getValue() ){
    			 return 1;
    		 }
    		 else if( item0.getValue() == item1.getValue() ){
    			 return 0;
    		 }
    		 else{
    			 return -1;
    		 }
    	 }
    }
    public void Apriori() throws RserveException
    { 
    	try {
    		System.out.println("source(\""+ Value.scriptRootPath +"newScript.R\")");
    		rc.eval("source(\""+ Value.scriptRootPath +"newScript.R\")");  
    		
    		REXP rexp = rc.eval("x@lhs@data@i");
    		arrLeft = rexp.asIntegers();
    		rexp = rc.eval("x@rhs@data@i");
    		arrRight = rexp.asIntegers();
			for( int i = 0 ; i < arrLeft.length ; i ++ ){
				System.out.print( arrLeft[ i ] + " " );
			}
			System.out.println();
			for( int i = 0 ; i < arrRight.length ; i ++ ){
				System.out.print( arrRight[ i ] + " " );
			}
			System.out.println();
			rexp = rc.eval("(x@quality)[,1]");
			support = rexp.asDoubles();
			for( int i = 0 ; i < support.length ; i ++ ){
				System.out.println( support[ i ] );
			}
			
			
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream("H:/kc2.0/myeclipse/cnsoftbei/path/console.csv"),"UTF-8"));
			String temp;
			temp = reader.readLine();
			while((temp = reader.readLine()) != null){
				temp = temp.substring(1, temp.length() - 1);
				apItemList.add(temp);
	        }
			for( int i = 0 ; i < apItemList.size() ; i ++ ){
				System.out.println( i + " " + apItemList.get(i));
			}
			
			for( int i = 0 ; i < arrLeft.length ; i ++ ){
				if( existedMap.get( apItemList.get( arrLeft[i] ) ) == null ){
					existedMap.put( apItemList.get( arrLeft[i] ),apItemList.get( arrLeft[i] ) );
	 			}
				if( existedMap.get( apItemList.get( arrRight[i] ) ) == null ){
					existedMap.put( apItemList.get( arrRight[i] ),apItemList.get( arrRight[i] ) );
	 			}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    public void getCluster() throws RserveException
    {
    	
    	try {
    		System.out.println("source(\""+ Value.scriptRootPath +"cluster.R\")");
    		rc.eval("source(\""+ Value.scriptRootPath +"cluster.R\")");  
    		REXP rexp = rc.eval("kc$cluster");
    		arrCluster = rexp.asIntegers();
			for( int i = 0 ; i < arrCluster.length ; i ++ ){
				System.out.println( arrCluster[ i ] );
				
			}
			System.out.println( arrCluster.length );
			
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	
    	int max=0;
    	for(int i=0;i<arrCluster.length;i++)
    	{
    		if(arrCluster[i]>max) max=arrCluster[i];
    	}
    	clusterCount=new int [max+1];
    	
    	for(int i=0;i<arrCluster.length;i++)
    	{
    		clusterCount[arrCluster[i]]++;
    	}
    	
    }
    
    
    
    
  /*  
    void getCluster() throws RserveException
    {
    	try {
    		rc.eval("source(\"F:/workspace/RJava/bin/cluster.R\")");  
    		REXP rexp = rc.eval("kc$cluster");
    		int[] arr = rexp.asIntegers();
			for( int i = 0 ; i < arr.length ; i ++ ){
				System.out.println( arr[ i ] );
				
			}
			System.out.println( arr.length );
			
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    }
    */
    boolean Meet( String str ) // ��ʾmap�еı����Ƿ�����Ҫ��
    {
    	if( str.length() < 2  ){
    		return false;
    	}
    	for( int i = 0 ; i < str.length() ; i ++ ){
    		if( str.charAt( i ) >= '0' && str.charAt( i ) <= '9'  ){
    			return false;
    		}
    	}
    	if( stopMap.get( str ) != null ){
			return false;
		}
    	return true;
    }
    public void callRScript() throws REXPMismatchException {  
        
        // source������Ҫ����R�ű�·��, ע�⴫��ת�������  
       // rc.eval("source(\"F:/workspace/rrrrTest/bin/area.R\")");  
    	System.out.println("source(\""+ Value.scriptRootPath +"script.R\")");
        try {
			rc.eval("source(\""+ Value.scriptRootPath +"script.R\")");
		
		} catch (RserveException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       
     //   REXP rexp = rc.eval("d");
       
   //         File file = new File("F:\\workspace\\rst.txt");
            
           // File stopFile = new File(Value.scriptRootPath+"stopwords.txt");  // ���벻��Ҫ�Ĵʻ�
            
            
         //   BufferedReader reader = null;
            try {
                System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
                BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(Value.scriptRootPath+"rst.txt"),"UTF-8")); 
                BufferedReader stopReader=new BufferedReader(new InputStreamReader(new FileInputStream(Value.scriptRootPath+"stopwords.txt"),"UTF-8"));
                String tempString = null;
                String stopString = null;
                while((stopString = stopReader.readLine()) != null){
                	if( stopMap.get( stopString ) == null ){
                		stopMap.put(stopString, 1);
                	}
                	
                }
        
                
                // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
                while ((tempString = reader.readLine()) != null) {
                    // ��ʾ�к�
           //         System.out.println(tempString);
                    if( haMap.get( tempString ) == null ){
        				haMap.put( tempString, 1);
        			}
        			else{
        				Integer val = haMap.get(tempString);
        				haMap.put(tempString, val.intValue() + 1 );
        			} 
                }
                
                reader.close();
                
                Set<String> set = haMap.keySet();
        		for (String ch : set) {
        			if( Meet( ch ) ){
        		//		System.out.println(ch + " " + haMap.get(ch));
        				sortList.add(new SortItem( ch, haMap.get(ch) ));
        			}
        			
        		}
        		ComparatorItem comparator=new ComparatorItem();
        		Collections.sort( sortList, comparator);
        		for( int i = 0 ; i < sortList.size() ; i ++ ){
        			System.out.println( sortList.get( i ).key + "," + sortList.get( i ).value );
        		}
        		
        		
            } catch (IOException e) {
                e.printStackTrace();
            } 
        

    }  
}  

