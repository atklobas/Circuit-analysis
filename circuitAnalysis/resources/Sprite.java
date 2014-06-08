package resources;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Sprite{
	int x,y,width,height,offsetX,offsetY;
	private double rotation;
//	Image image;
	ImageResource resource;
	

	Sprite(int x, int y, int width, int height, ImageResource resource) {
		
		this(x,y,width,height,0,0,resource);
		
	}

	Sprite(int x, int y, int width, int height, int offsetX, int offsetY,double rotation, ImageResource resource) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.offsetX=offsetX;
		this.offsetY=offsetY;
		this.resource=resource;
		this.rotation=rotation;
	}
	Sprite(int x, int y, int width, int height, int offsetX, int offsetY, ImageResource resource) {
		this(x,y,width,height,offsetX,offsetY,0,resource);
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

	
	public int getWidth() {
		return width;
	}

	
	public int getHeight() {
		return height;
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
		g.translate(x-(int)(offsetX*scale), y-(int)(offsetY*scale));
		g.scale(scale, scale);
		if(rotation!=0)
			g.rotate(rotation,offsetX*scale,offsetY*scale);
		resource.draw(g,this,0,0,(int)(width),(int)(height));
	}
	public void DrawFromtopCorner(Graphics g){
		resource.draw(g,this,0,0,(int)(width),(int)(height));
	}

	public void draw(Graphics2D g, int x, int y, double scale, int rotation) {
		g.translate(x-(int)(offsetX*scale), y-(int)(offsetY*scale));
		g.scale(scale, scale);
		if(this.rotation!=0||rotation!=0)
			g.rotate(Math.toRadians(rotation)+this.rotation,offsetX*scale,offsetY*scale);
		resource.draw(g,this,0,0,(int)(width),(int)(height));
		
	}
	
	

}
