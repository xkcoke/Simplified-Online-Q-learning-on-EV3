package main;

import java.io.IOException;

import world.Grid;
import world.World;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.TimerListener;
import lejos.hardware.LED;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;

public class InterruptByTime implements TimerListener{
	private boolean stopFlag = false;
	private float[] dataSample;
	private int channel;
	private int speed = 50;
	private int deadZone = 3;
	private int forwardZone = 60;
	private int backwardZone = 40;
	private int sampleSpace = 5;
	private RegulatedMotion m;
	private SampleProvider averageData;
	private Float fAngle;
	private Float fDistance;
	private World w;

	// 构造函数
	InterruptByTime(SampleProvider sensorData, int sensorChannel)
			throws IOException {
		averageData = new MeanFilter(sensorData, sampleSpace);
		dataSample = new float[averageData.sampleSize()];
		channel = sensorChannel;
		w = new World();
		m = new RegulatedMotion(MotorPort.B, MotorPort.C);
	}

	InterruptByTime(SampleProvider sensorData, int sensorChannel,
			int robotSpeed, int angleDeadZone) throws IOException {
		averageData = new MeanFilter(sensorData, sampleSpace);
		dataSample = new float[averageData.sampleSize()];
		channel = sensorChannel;
		speed = robotSpeed;
		deadZone = angleDeadZone;
		w = new World();
		m = new RegulatedMotion(MotorPort.B, MotorPort.C);
	}

	public void timedOut() {
		// TODO Auto-generated method stub
		//跟踪信标
		LED l = LocalEV3.get().getLED();
		if(stopFlag){
			l.setPattern(0);
		}
		else l.setPattern(1);
		averageData.fetchSample(dataSample, 0);
		fAngle = new Float(dataSample[channel*2-2]);
		int intAngle = fAngle.intValue();
		LCD.drawInt(intAngle, 3, 2, 5);
		fDistance = new Float(dataSample[channel*2-1]);
		int intDistance = fDistance.intValue();
		LCD.drawInt(intDistance, 3, 2, 6);
		if(intDistance > 10){
			if(!stopFlag){
				Grid g = w.findState(intAngle, intDistance);
				motionInGrid(g);
			}
		}
		else{
			if(!stopFlag){
				stopFlag = true;
				m.close();
				try {
					w.summary();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void motionInGrid(Grid g){
		char a = g.getLeftAction();
		if(a == '0')m.setLeft(0);
		else if(a == '1')m.setLeft(speed);
		else if(a == '2')m.setLeft(speed*2);
		a = g.getRightAction();
		if(a == '0')m.setRight(0);
		else if(a == '1')m.setRight(speed);
		else if(a == '2')m.setRight(speed*2);
	}
}
