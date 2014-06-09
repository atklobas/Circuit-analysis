package Components;

import circuitAnalysis.Main;
import resources.Renderable;
import resources.Sprite;

public abstract class Component implements Renderable, Cloneable{
	int x,y;
	private int rotation=0;
	public Component(){
		this.x=0;
		this.y=0;
	}
	public void setX(int x){
		
		this.x=(x+Main.gridSize/2)/Main.gridSize;
		if(x<-Main.gridSize/2){
			//this.x--;
		}
		
	}
	public void setY(int y){
		this.y=(y+Main.gridSize/2)/Main.gridSize;
		if(y<-Main.gridSize/2){
			//this.y--;
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

		if(rotation==0){
			x+=s.getOffsetX();
			y+=s.getOffsetY();
		}else if(rotation==90){
			int temp=-x;
			x=y;
			y=temp;
			x+=s.getOffsetX();
			y+=s.getOffsetY();
		}else if(rotation==180){
			x=-x+s.getOffsetX();
			y=-y+s.getOffsetY();
		}else{
			int temp=-x;
			x=y;
			y=temp;
			x=-x+s.getOffsetX();
			y=-y+s.getOffsetY();
		}
		
		
		if(x>0&&y>0){
			if(x<s.getWidth()&&y<s.getHeight()){
				System.out.println("selected");
				return true;
			}
		}
		return false;
	}

	public int getAngle(){
		return rotation;
	}
	public void rotateClockwise() {
		rotation=(rotation+90)%360;
		
	}
	public void rotateAntiClockwise() {
		rotation=(rotation+270)%360;
		
	}

}
