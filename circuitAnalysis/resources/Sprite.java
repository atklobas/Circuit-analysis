package resources;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Sprite{
	int x,y,width,height,offsetX,offsetY;
	private double rotation;
//	Image image;
	ImageResource resource;
	

	public Sprite(int x, int y, int width, int height, ImageResource resource) {
		
		this(x,y,width,height,0,0,resource);
		
	}

	public Sprite(int x, int y, int width, int height, int offsetX, int offsetY,double rotation, ImageResource resource) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.offsetX=offsetX;
		this.offsetY=offsetY;
		this.resource=resource;
		this.rotation=rotation;
	}
	public Sprite(int x, int y, int width, int height, int offsetX, int offsetY, ImageResource resource) {
		this(x,y,width,height,offsetX,offsetY,0,resource);
	}

	public void draw(Graphics g,int x, int y) {
		
		resource.draw(g,this,x-offsetX,y-offsetY);
	}
	public void draw(Graphics g,int x, int y, int width,int height) {
		resource.draw(g,this,x-offsetX*width/this.width,y-offsetY*width/this.width,width,height);
	}
	public ImageResource getResource(){
		return this.resource;
	}
	
	public String getResourceName() {
		return resource.getName();
	}

	public int getResourceID() {
		return resource.getID();
	}

	
	public int SheetX() {
		return x;
	}

	
	public int SheetY() {
		return y;
	}

	
	public int SheetWidth() {
		return width;
	}

	
	public int SheetHeight() {
		return height;
	}
	public int offsetX(){
		return 0;
	}
	public int offsetY(){
		return 0;
	}

	
	public double getDuration() {
		// TODO Auto-generated method stub
		return 1;
	}

	
	public void setDuration(double dur) {
		// TODO Auto-generated method stub
		
	}

	
	public int getSheetX() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getSheetY() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getSheetWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getSheetHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getOffsetX() {
		// TODO Auto-generated method stub
		return this.offsetX;
	}

	
	public int getOffsetY() {
		return this.offsetY;
	}
	

	
	
	public void setSheetX(int x) {
		// TODO Auto-generated method stub
		
	}

	
	public void setSheetY(int y) {
		// TODO Auto-generated method stub
		
	}

	
	public void setSheetWidth(int width) {
		// TODO Auto-generated method stub
		
	}

	
	public void setSheetHeight(int height) {
		// TODO Auto-generated method stub
		
	}

	
	public void setOffsetX(int xOffset) {
		// TODO Auto-generated method stub
		
	}

	
	public void setOffsetY(int yOffset) {
		// TODO Auto-generated method stub
		
	}

	public void draw(Graphics2D g, int x, int y, double scale) {
		//g.translate(x-(int)(offsetX*scale), y-(int)(offsetY*scale));
		//g.scale(scale, scale);
		//if(rotation!=0)
		//	g.rotate(rotation,offsetX*scale,offsetY*scale);
		resource.draw(g,this,x-(int)(offsetX*scale), y-(int)(offsetY*scale),(int)(width),(int)(height));
	}
	

}
