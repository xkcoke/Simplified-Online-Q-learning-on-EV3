package world;

public class Grid {
	private char leftAction;
	private char rightAction;
	private double reward;
	Grid(){
		leftAction = '0';
		rightAction = '0';
		reward = 0.0;
	}
	public void setLeftAction(char a){
		leftAction = a;
	}
	public void setRightAction(char a){
		rightAction = a;
	}
	public void setReward(double r){
		reward = r;
	}
	public char getLeftAction(){
		return leftAction;
	}
	public char getRightAction(){
		return rightAction;
	}
	public double getReward(){
		return reward;
	}
}
