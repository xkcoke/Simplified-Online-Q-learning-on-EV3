package memory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
/*
 * author:Xu Ke
 * function:和storeData类配套，读取其write方法中写入的数据
 * Date：2015/2/3
 * */
public class readData {
	File f;
	FileInputStream fin;
	public readData(String filename) throws FileNotFoundException{
		f = new File(filename);
		fin = new FileInputStream(f);
	}
	
}
