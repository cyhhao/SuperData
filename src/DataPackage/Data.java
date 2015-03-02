package DataPackage;



import java.io.*;
import java.nio.charset.Charset;


import java.util.ArrayList;
import java.util.List;

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
import assistFunction.assistFunc;

public class Data {
	assistFunc assist; 
	public String onlyFileName;
	public List < Row > Map;			
	public List < String > Head;							/* 有10个数 */
	public List < AprioriData > AprioriList;
	public int rowNum;					// 行数
	public int colNum;					// 列数
	public int clusterNum;	// 聚类分了几个类别				/*分成3类*/
	public List classTag;   //  每一行都属于什么类别 			/* 0，0，0，1，2，1，2，1，0，1 */
	public List Proportion;	// 每个类别都有几个				/* 三个类分别有 4个，4个，2个 */
	public Cluster clusterer;	// 聚类类
	public Association association;
	public List headList;		//	the head of list for the charts
	public List <Integer>numOfEachHeadList ;  // how many values are there in each attribute list
	public String FileName;
	public static int openFileSerial = 0;
	public RserveBegin Rserve;
	
	public void ReadForMining( String fileName ) throws Exception // 挖掘专用度函数，只能读csv文件
	{
		
		BufferedWriter output = new BufferedWriter(new FileWriter("F:/workspace/nameOfFileToBeMined.txt"));
		output.write( fileName );
		output.close();
		Rserve = new RserveBegin();
		
	}

	
	
	public Data setChoosenData( Data dataSrc, int rowStart, int rowEnd, int colStart, int colEnd )
	{
		Data theDataChoosen = new Data();
		for( int i = colStart ; i < colEnd ; i ++ ){
			theDataChoosen.Head.add( dataSrc.Head.get(i) );
		}
		for( int i = rowStart ; i < rowEnd ; i ++ ){
			Row eachRow = new Row();
			for( int j = colStart ; j < colEnd ; i ++ ){
				eachRow.list.add( dataSrc.getElement(i, j) );
			}
			theDataChoosen.Map.add( eachRow );
		}
		return theDataChoosen;
	}
	public class AprioriMetaData
	{
		public int Tag;
		public int value;
		
	}
	
	public class AprioriData
	{
		public AprioriMetaData from;
		public AprioriMetaData to;
		AprioriData()
		{
			from = new AprioriMetaData();
			to = new AprioriMetaData();
		}
	}
	public Data()
	{
		Map = new ArrayList< Row >();
		Head = new ArrayList< String >();
		classTag = new ArrayList();
		Proportion = new ArrayList();
		clusterer = new Cluster();
		association = new Association();
		AprioriList = new ArrayList();
		headList = new ArrayList();
		numOfEachHeadList = new ArrayList< Integer >();
	}
	public class Association
	{
		public boolean AprioriResult( AprioriItemSet set, int[] rst )
		{
			
			int i;
			int non_negative_cnt = 0;
			int item_length = set.items().length; 
			
			for( i = 0 ; i < item_length ; i ++ ){
				if( set.itemAt( i ) != -1 ){
					non_negative_cnt ++;
					rst[ 0 ] = i;
					rst[ 1 ] = set.itemAt( i );
				}
			}
			
			if( non_negative_cnt > 1 ){
				return false;
			}
			else{
				return true;				
			}
		}		
		public void startAssociation()
		{
			FastVector[] fv;
			int[] rst_one = new int[ 2 ];
			int[] rst_two = new int[ 2 ];
			int last_one = 0;
			try{
				FileName = "weather.nominal.arff";
				Instances train = DataSource.read(FileName);
//					System.out.println( train.attribute(0).toString() );
				for( int i = 0 ; i < train.numAttributes() ; i ++ ){
					numOfEachHeadList.add( last_one );		
					last_one += train.attribute(i).numValues();
					
			
			
					for( int j = 0 ; j < train.attribute( i ).numValues() ; j ++ ){
		//				System.out.println( train.attribute( i ).value( j ) );
						headList.add( train.attribute( i ).value( j ) );
					}
				}
				//					System.out.println( train.attribute(0) );
				Apriori apriori = new Apriori();
				AprioriData data;
				apriori.buildAssociations( train );
				fv = apriori.getAllTheRules();	
				for( int j = 0 ; j < fv[ 0 ].size() ; j ++ ){
					AprioriItemSet set1 = ( AprioriItemSet )( fv[ 0 ].elementAt( j ) );
					AprioriItemSet set2 = ( AprioriItemSet )( fv[ 1 ].elementAt( j ) );
					if( AprioriResult(set1, rst_one) && AprioriResult(set2, rst_two) ){
						System.out.print( rst_one[ 0 ] + "   " +  rst_one[ 1 ] );
						data= new AprioriData();
						data.from.Tag = rst_one[ 0 ];
						data.from.value = rst_one[ 1 ];
						System.out.print( "   ->   ");
						System.out.println( rst_two[ 0 ] + "   " + rst_two[ 1 ]  );
						
						data.to.Tag = rst_two[ 0 ];
						data.to.value = rst_two[ 1 ];
						AprioriList.add( data );
					}
				}
				System.out.println( apriori.toString() );
				System.out.println( "*********" );
				for( int i = 0 ; i < numOfEachHeadList.size() ; i ++ ){
					System.out.println( numOfEachHeadList.get( i ) );
				}
				for( int i = 0 ; i < headList.size() ; i ++ ){
					System.out.println( headList.get( i ) );
				}
				
				for( int i = 0 ; i < AprioriList.size() ; i ++ ){
					data= new AprioriData();
					data = AprioriList.get( i );
					System.out.print( data.from.Tag + "   " +  data.from.value );
					System.out.print( "   ->   ");
					System.out.println( data.to.Tag + "   " + data.to.value   );
				}
				
				
				
				for( int i = 0 ; i < AprioriList.size() ; i ++ ){
					data= new AprioriData();
					data = AprioriList.get( i );
					System.out.print( /*data.from.Tag + "   " +  data.from.value*/ headList.get( numOfEachHeadList.get(data.from.Tag  ) + data.from.value )  );
					System.out.print( "   ->   ");
					System.out.println( /*data.to.Tag + "   " + data.to.value */ headList.get( numOfEachHeadList.get(data.to.Tag )   + data.to.value) );
				}
				
				
			}
			catch( Exception e )
			{
				
				
			}
		}
	}
	public class Cluster {
		public void startCluster()
		{
		   
	    // load data
		   try
		   {
			   Print();
			   Instances train = DataSource.read( FileName );
			   //"saveArffFile.arff"
			    // build clusterer
			    EM clusterer = new EM();
			    clusterer.buildClusterer(train);
			    ClusterEvaluation eval = new ClusterEvaluation();
			    eval.setClusterer(clusterer);
			    eval.evaluateClusterer( train );
			    System.out.println(eval.clusterResultsToString());
			    
			    int[] per = new int[ eval.getNumClusters() ];
			    
			    System.out.println("# - cluster - distribution");
			    System.out.println( train.numInstances() );
			    clusterNum = eval.getNumClusters();
			    for (int i = 0; i < train.numInstances(); i++) {
			      int cluster = clusterer.clusterInstance(train.instance(i));
			      classTag.add( cluster );
			      double[] dist = clusterer.distributionForInstance(train.instance(i));
			      System.out.print((i+1));
			      System.out.print(" - ");
			      System.out.print(cluster);
			      System.out.print(" - ");
			      System.out.print(Utils.arrayToString(dist));
			      System.out.println();
			      per[ cluster ] ++;
			    }
			    for( int i = 0 ; i < eval.getNumClusters() ; i ++ ){
			    	Proportion.add( per[ i ] );
			    	
			    }
			    Print();
		   }
		   catch( Exception e )
		   {
			   
			   
		   }
		}
	}
	public static class Row
	{
		public List < String > list;
		public Row()
		{
			list = new ArrayList();
		}
		
	}
	public String getElement( int x, int y )
	{
		return Map.get( x ).list.get( y );
		
	}
	public void setElement( int x, int y, String s )
	{
		Map.get( x ).list.set( y , s );
		
	}
	
	
	public void Print()
	{
		
		for( int i = 0 ; i < colNum ; i ++ ){
			System.out.print( Head.get( i ) + " " );
		}
		System.out.println();
		for( int i = 0 ; i < rowNum ; i ++ ){
			for( int j = 0 ; j < colNum ; j ++ ){
				System.out.print( Map.get( i ).list.get( j ) + " " );
			}
			System.out.println();
		}
	}
	public void Read( String fileName, String onlyFileName ) throws Exception
	{
		this.onlyFileName=onlyFileName;
		if( fileName.endsWith( "xls" ) ){
			ReadFromExcel( fileName );
			SaveAsArff( onlyFileName.substring(0, onlyFileName.length() - 4) + "_" + openFileSerial );
			FileName = onlyFileName.substring(0, onlyFileName.length() - 4) + "_" + openFileSerial + ".arff";
		}
		else if( fileName.endsWith( "csv" ) ){
			ReadFromCSV( fileName );

			SaveAsArff( onlyFileName.substring(0, onlyFileName.length() - 4) + "_" + openFileSerial);
			FileName = onlyFileName.substring(0, onlyFileName.length() - 4) + "_" + openFileSerial  + ".arff";
			System.out.println( FileName );
		}
		else if( fileName.endsWith( "arff" ) ){
			ReadFromARFF( fileName );
			int bytesum = 0; 
	        int byteread = 0;
			InputStream inStream = new FileInputStream(fileName); //读入原文件 
            FileOutputStream fs = new FileOutputStream(onlyFileName); 
            byte[] buffer = new byte[1444]; 
            int length; 
            while ( (byteread = inStream.read(buffer)) != -1) { 
                bytesum += byteread; //字节数 文件大小 
                System.out.println(bytesum); 
                fs.write(buffer, 0, byteread); 
            } 
            inStream.close(); 
			FileName = onlyFileName;
		}
		openFileSerial ++;
		
	}
	public void SaveAsArff( String saveFileName ) throws Exception
	{
		SaveAsCSV( saveFileName + ".csv" );
		CSVLoader loader = new CSVLoader();
	    loader.setSource(new File( saveFileName + ".csv" ));
	    Instances train = loader.getDataSet();
	    ArffSaver saver = new ArffSaver();
	    saver.setInstances( train );
	    saver.setFile(new File( saveFileName + ".arff" ));
	    saver.writeBatch();
		
	}
	public void SaveAsCSV( String saveFileName ) throws Exception
	{
		File saveFile = new File( saveFileName );
		BufferedWriter writer = new BufferedWriter( new FileWriter( saveFile ) );
		for( int i = 0 ; i < colNum ; i ++ ){
			writer.write( Head.get( i ) );
			if( i != Head.size() - 1 ){
				writer.write( ',' );
			}
		}
		writer.write('\n');
		for( int i = 0 ; i < rowNum ; i ++ ){
			for( int j = 0 ; j < colNum ; j ++ ){
				writer.write( getElement( i, j ) );
				if( j != colNum - 1 ){
					writer.write( ',' );
				}
			}
			writer.write( '\n' );
		}
		writer.close();
		
	}
	
	public void ReadFromARFF( String fileName )
	{
		String[] tempValue;
		char tempChar;
		int i;
		try{
			CsvReader reader = new CsvReader( fileName ,',',Charset.forName("utf-8") );
			while( reader.readRecord() ){
				tempValue = reader.getValues();
				if( tempValue[ 0 ].startsWith( "@attribute" ) || tempValue[ 0 ].startsWith( "@ATTRIBUTE" ) ){
					String headString = new String();
					
					for( i = 0 ; assist.isLetter( tempValue[ 0 ].charAt( i ) ) || tempValue[ 0 ].charAt( i ) == '@' ; i ++ );
					
					for( i = i + 1 ; assist.isLetter( tempValue[ 0 ].charAt( i ) ); i ++ ){
						
						headString += tempValue[ 0 ].charAt( i );
					}
					
					Head.add( headString );
				}
				if( tempValue[ 0 ].startsWith( "@DATA" ) || tempValue[ 0 ].startsWith( "@data" ) ){
					while( reader.readRecord() ){
						
						tempValue = reader.getValues();
						if( tempValue[ 0 ].charAt( 0 ) == '%' ){
							break;
						}
						Row eachRow = new Row();
						for( i = 0 ; i < tempValue.length ; i ++ ){
							eachRow.list.add( tempValue[ i ] );
						}
						Map.add( eachRow );
					}
				}
			}
			colNum = Head.size();
			rowNum = Map.size();
			reader.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
	}
	public void ReadFromCSV( String fileName )
	{
		String[] tempValue;
		int i;
		int j;
		try {
			//TODO: UTF-8
			CsvReader reader = new CsvReader( fileName, ',', Charset.forName( "GB2312") );
			
			for( i = 0 ; reader.readRecord() ; i ++ ){
				tempValue = reader.getValues();
				if( i == 0 ){
					if( tempValue[ 0 ].endsWith( "**" ) ){
						i --;
						continue;
					}
					for( j = 0 ; j < tempValue.length ; j ++ ){
						Head.add( tempValue[ j ] );
					}
				}
				else{
					Row eachRow = new Row();
					for( j = 0 ; j < tempValue.length ; j ++ ){
						eachRow.list.add( tempValue[ j ] );
					}
					Map.add( eachRow );
				}
			}
			colNum = Head.size();
			rowNum = Map.size();
			reader.close();
			System.out.println( "readCSVReady" );
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void ReadFromExcel( String fileName )
	{
		
		
		Workbook book;
		System.out.println();
		try {
			book = Workbook.getWorkbook( new File( fileName ) );
			Sheet sheet = book.getSheet(0);
			Cell cell;
			colNum = sheet.getColumns();
			rowNum = sheet.getRows();
			
			for( int i = 0 ; i < colNum ; i ++ ){
				Head.add( sheet.getCell(i, 0).getContents() );
			}
			
			for( int i = 1 ; i < rowNum ; i ++ ){
				Row eachRow = new Row();
				for( int j = 0 ; j < colNum ; j ++ ){
					eachRow.list.add( sheet.getCell( j, i ).getContents() );
				}
				Map.add( eachRow );
			}
			rowNum --;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
}
