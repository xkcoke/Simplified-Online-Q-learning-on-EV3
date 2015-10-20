package main;

import java.io.IOException;

import lejos.hardware.Keys;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.utility.Timer;

public class FirstRL {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		LCD.drawString("Welcome~", 0, 1);
		//初始化系统参数
		int speed = 75;
		int deadZone = 3;
		int channel = 1;
		channel = ChooseParam("Choose Channel:", channel, 2);
		speed = ChooseParam("Choose Speed:", speed, 2);
		deadZone = ChooseParam("Choose DeadZone:", deadZone, 2);
		//初始化传感器端口
		Port port = LocalEV3.get().getPort("S4");
		EV3IRSensor sensor = new EV3IRSensor(port);
		SampleProvider direction = sensor.getSeekMode();
		//初始化定时中断
		InterruptByTime el = new InterruptByTime(direction, channel, speed, deadZone);
		Timer t = new Timer(100, el);
		LCD.drawString("Let's Go!", 0, 1);
		t.start();
		//按下esc键退出，否则循环等待程序进入定时中断
		Keys myKey = LocalEV3.get().getKeys();
		while(Keys.ID_ESCAPE != myKey.readButtons()){
			
		}
		t.stop();
		Delay.msDelay(150);
		LCD.clear();
		LCD.drawString("Bye Bye~", 0, 1);
		Delay.msDelay(2000);
	}
	//参数选择函数
	private static int ChooseParam(String Text,int param,int places){
		LCD.drawString(Text, 0, 2);
		LCD.drawInt(param, places, 2, 3);
		Keys myKey = LocalEV3.get().getKeys();
		while(Keys.ID_ENTER != myKey.readButtons()){
			if(Keys.ID_UP == myKey.readButtons()){
				param++;
				LCD.drawInt(param, places, 2, 3);
				Delay.msDelay(500);
			}
			else if(Keys.ID_DOWN == myKey.readButtons()){
				param--;
				LCD.drawInt(param, places, 2, 3);
				Delay.msDelay(500);
			}
			else if(Keys.ID_LEFT == myKey.readButtons()){
				param = param - 10;
				LCD.drawInt(param, places, 2, 3);
				Delay.msDelay(500);
			}
			else if(Keys.ID_RIGHT == myKey.readButtons()){
				param = param + 10;
				LCD.drawInt(param, places, 2, 3);
				Delay.msDelay(500);
			}
		}
		if(Keys.ID_ENTER == myKey.readButtons()){
			Delay.msDelay(500);
		}
		LCD.clear();
		return param;
	}
}
