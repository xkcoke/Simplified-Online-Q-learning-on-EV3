package memory;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

/*
 * author:Xu Ke
 * function:将读取到的整数信息以字符串的形式存储在指定文件中
 * date:2015/2/2
 * */
public class storeData{
	private FileWriter fw;
	private int count;
	public storeData(String filename, boolean remain) throws IOException{
		fw = new FileWriter(filename, remain);
		count = 1;
	}
	public void write(int angle,int distance,double reward) throws IOException{
		double r = Number3(reward);
		if(count == 1){
			fw.write("\r\nstart:\r\n");
			fw.flush();
		}
		fw.write(String.valueOf(angle)+",");
		fw.flush();
		fw.write(String.valueOf(distance)+",");
		fw.flush();
		fw.write(String.valueOf(r)+"\r\n");
		fw.flush();
		count++;
	}
	public void close(int time) throws IOException{
		fw.write("total time:"+String.valueOf(time)+"\r\n");
		fw.write("end");
		fw.flush();
		fw.close();
	}
	private double Number3(double num){
		BigDecimal bd = new BigDecimal(num);
		BigDecimal bd1 = bd.setScale(3,BigDecimal.ROUND_HALF_UP);
		num = bd1.doubleValue();
//		long ll = Double.doubleToLongBits(num);
		return num;
	}
}
