package world;

import java.io.IOException;

import memory.storeData;

public class World {
	private Grid[][] grid;
	private int[][] path;
	private int count;
	private double r;
	private storeData s;
	public World() throws IOException{
		grid = new Grid[51][101];
		path = new int[100][2];
		s = new storeData("firstRL.csv",false);
		count = 0;
		r = -0.01;
		for(int i = 0;i < 51;i++){
			for(int j = 0;j<101;j++){
				initStrategy(i,j);
			}
		}
	}
	
	private void initStrategy(int i,int j){
		int angle = i - 25;
		Grid g = new Grid();
		if(j > 10){
			g.setReward(r);
			if(angle < -15){
				g.setLeftAction('0');
				g.setRightAction('2');
			}
			else if(angle < -3){
				g.setLeftAction('1');
				g.setRightAction('2');
			}
			else if(angle < 3){
				g.setLeftAction('2');
				g.setRightAction('2');
			}
			else if(angle < 15){
				g.setLeftAction('2');
				g.setRightAction('1');
			}
			else{
				g.setLeftAction('2');
				g.setRightAction('0');
			}
		}
		else{
			g.setLeftAction('0');
			g.setRightAction('0');
			g.setReward(1.0);
		}
		grid[i][j] = g;
	}
	
	public Grid findState(int angle,int distance){
		path[count][0] = angle;
		path[count][1] = distance;
		count++;
		return grid[angle+25][distance];
	}
	
	public void summary() throws IOException{
		for(int i = 0;i < count;i++){
			if(grid[ path[i][0]+25 ][ path[i][1] ].getReward() < 1.0+r*(count - i))
				grid[ path[i][0]+25 ][ path[i][1] ].setReward(1.0+r*(count - i));
			s.write(path[i][0], path[i][1],
					grid[ path[i][0]+25 ][ path[i][1] ].getReward());
		}
		s.close(count);
	}
}
