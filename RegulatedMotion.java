package main;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;

/*
 * author:Xu Ke
 * function:电机驱动，具有前进、转向、后退、停止等功能
 * date:2015/2/2
 * */
public class RegulatedMotion {
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	RegulatedMotion(Port left,Port right){
		leftMotor = new EV3MediumRegulatedMotor(left);
		rightMotor = new EV3MediumRegulatedMotor(right);
	}
	void setLeft(int speed){
		if(speed < 0){
			leftMotor.setSpeed(-speed);
			leftMotor.backward();
		}
		else{
			leftMotor.setSpeed(speed);
			leftMotor.forward();
		}
	}
	void setRight(int speed){
		if(speed < 0){
			rightMotor.setSpeed(-speed);
			rightMotor.backward();
		}
		else{
			rightMotor.setSpeed(speed);
			rightMotor.forward();
		}
	}
	void forward(int speed){
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.forward();
		rightMotor.forward();
	}
	void backward(int speed){
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.backward();
		rightMotor.backward();
	}
	void turnRight(int speed){
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.forward();
		rightMotor.backward();
	}
	void turnLeft(int speed){
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.backward();
		rightMotor.forward();
	}
	void turnByAngleSpeed(int angleSpeed){
		if(angleSpeed < 0){
			turnLeft(-angleSpeed);
		}
		else{
			turnRight(angleSpeed);
		}
	}
	void combinedMotion(int speed, int angle){
		int leftSpeed = speed + angle;
		int rightSpeed = speed - angle;
		if(leftSpeed > 0){
			leftMotor.setSpeed(leftSpeed);
			leftMotor.forward();
		}
		else{
			leftMotor.setSpeed(-leftSpeed);
			leftMotor.backward();
		}
		if(rightSpeed > 0){
			rightMotor.setSpeed(rightSpeed);
			rightMotor.forward();
		}
		else{
			rightMotor.setSpeed(-rightSpeed);
			rightMotor.backward();
		}
	}
	void stop(){//会产生抖动，不建议使用
		leftMotor.setSpeed(0);
		rightMotor.setSpeed(0);
	}
	void close(){
		leftMotor.close();
		rightMotor.close();
	}
}
