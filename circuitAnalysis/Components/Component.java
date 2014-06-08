package Components;

import circuitAnalysis.Main;
import resources.Renderable;
import resources.Sprite;

public abstract class Component implements Renderable, Cloneable{
	int x,y;
	private boolean rotated=false;
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
	public boolean isInBounds(int x, int y){
		x-=this.x*Main.gridSize;
		y-=this.y*Main.gridSize;
		Sprite s=this.getSprite();
		if(!rotated){
			x+=s.getOffsetX();
			y+=s.getOffsetY();
		}else{
			x+=s.getHeight()-s.getOffsetY();
			y+=s.getOffsetX();
		}
		if(x>0&&y>0){
			if(!rotated&&x<s.getWidth()&&y<s.getHeight()){
				return true;
			}
			if(rotated&&x<s.getHeight()&&y<s.getWidth()){
				return true;
			}
		}
		return false;
	}
	public void rotate() {
		rotated=!rotated;
	}
	public int getAngle(){
		return rotated?90:0;
	}

}
