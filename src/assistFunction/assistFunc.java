package assistFunction;

import java.io.*;

public class assistFunc {
	public static boolean isLetter( char c )
	{
		return ( c >= 'a' && c <= 'z' ) || ( c >= 'A' && c <= 'Z' );
		
	}
	public static boolean StringToFile(String url,String str)
	{
		try 
		{
			File file = new File( url );
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"utf-8"); 
			
			Writer writer = new BufferedWriter(write);
			
			writer.write(str);
			writer.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	public static String read(String fileName) {
		String data=new String();
		File file = new File(fileName);
        BufferedReader reader = null;
        try {
           // System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            String tempString = null;
            int line = 1;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null) {
                // ��ʾ�к�
                data+=tempString+"\n";
                line++;
            }
            reader.close();
        }catch(FileNotFoundException e){
        	
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // �½��ļ����������������л���
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // �½��ļ���������������л���
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // ��������
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // ˢ�´˻���������
            outBuff.flush();
        } finally {
            // �ر���
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    // �����ļ���
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // �½�Ŀ��Ŀ¼
        (new File(targetDir)).mkdirs();
        // ��ȡԴ�ļ��е�ǰ�µ��ļ���Ŀ¼
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // Դ�ļ�
                File sourceFile = file[i];
                // Ŀ���ļ�
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // ׼�����Ƶ�Դ�ļ���
                String dir1 = sourceDir + "/" + file[i].getName();
                // ׼�����Ƶ�Ŀ���ļ���
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }
	
}
