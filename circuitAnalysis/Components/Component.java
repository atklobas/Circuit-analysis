package Components;

import circuitAnalysis.Main;
import resources.Renderable;

public abstract class Component implements Renderable, Cloneable{
	int x,y;
	public Component(){
		this.x=0;
		this.y=0;
	}
	public void setX(int x){
		this.x=(x+Main.gridSize/2)/Main.gridSize;
		if(x<-Main.gridSize/2){
			this.x--;
		}
	}
	public void setY(int y){
		this.y=(y+Main.gridSize/2)/Main.gridSize;
		if(y<-Main.gridSize/2){
			this.y--;
		}
	}
	
	public int getX() {
		return x*Main.gridSize;
	}

	@Override
	public int getY() {
		return y*Main.gridSize;
	}

	@Override
	public boolean rendered() {
		return true;
	}

	@Override
	public double getScale() {
		return 1;
	}
	public abstract Component Clone();

}
