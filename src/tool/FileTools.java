package tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileTools {
	public File file;
	
	public FileTools() {
		
	}
	
	public FileTools(String str) {
		file = new File(str);
		String path = null;
//	    try {
//	       path = directory.getCanonicalPath();//获取当前路径
//	    } catch (IOException e) {
//	        // TODO Auto-generated catch block
//	        e.printStackTrace();
//	    } 
//	    path +="\\Test";
		if(!file.exists()) {//如果文件不存在
			try {
				file.createNewFile();
				System.out.println("创建成功");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("创建失败");
			}
		}
	}
	
	public boolean writeFile(String str) {
		FileOutputStream fos = null;
	    try {
			fos = new FileOutputStream(file,true);
			//转换为byte数组
			byte[] b = str.getBytes();
			fos.write(b);
			fos.flush();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("文件未找到");
			return false;
		} catch (IOException e) {
			System.out.println("IO错误");
			e.printStackTrace();
			return false;
		}finally {
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
        
	}
	
	public String readFile() {
		FileInputStream fis = null;                 
        try {
			fis = new FileInputStream(file);
			//读取数据，并将读取到的数据存储到数组中
			byte[] data = new byte[1024]; //数据存储的数组
		    int i = 0; //当前下标
            //读取流中的第一个字节数据
            int n = fis.read();
            //依次读取后续的数据
            while(n != -1){ //未到达流的末尾
                //将有效数据存储到数组中
                data[i] = (byte)n;
                //下标增加
                i++;
                //读取下一个字节的数据
                n = fis.read();
            }
           
            //解析数据
            String s = new String(data,0,i);
            //输出字符串
            System.out.println(s);
            return s;
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("文件未找到");
			return "";
		} catch (IOException e) {
			System.out.println("IO错误");
			e.printStackTrace();
			return "";
		}finally{
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
