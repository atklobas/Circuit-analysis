package resources;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.ImageCapabilities;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageResource{
	
	private boolean accelerated;
	private int imageType=VolatileImage.BITMASK;
	private ImageCapabilities caps=new ImageCapabilities(true);
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	GraphicsConfiguration gc;
	private VolatileImage image;
	private BufferedImage presistent;
	private String name;
	
	public ImageResource(String location, String name) throws IOException{
		this(location);
		this.name=name;
	}
	public ImageResource(String location) throws IOException{
		File temp=(new File(location));
		gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage tempi=ImageIO.read(temp);
		presistent=new BufferedImage(tempi.getWidth(),tempi.getHeight(),BufferedImage.TYPE_INT_ARGB);
		presistent.getGraphics().drawImage(tempi, 0, 0, null);
		createImage();
		redraw();
		name=temp.getName();
	}
	private void ensureImage(){
		switch(image.validate(gc)){
		case VolatileImage.IMAGE_INCOMPATIBLE:
			createImage();
		case VolatileImage.IMAGE_RESTORED:
			redraw();
		default:
			
		}
	}
	
	public Sprite createSprite(int x, int y, int width, int height,int offsetX, int offsetY,int rotation) {
		return new Sprite(x,y,width,height,offsetX,offsetY,Math.toRadians(rotation),this);
	}
	public Sprite createSprite(int x, int y, int width, int height,int offsetX, int offsetY,double rotation) {
		return new Sprite(x,y,width,height,offsetX,offsetY,rotation,this);
	}
	public Sprite createSprite(int x, int y, int width, int height,int offsetX, int offsetY) {
		return new Sprite(x,y,width,height,offsetX,offsetY,this);
	}
	
	public Sprite createSprite(int x, int y, int width, int height) {
		return new Sprite(x,y,width,height,this);
	}
	

	public String getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getName() {
		return name;
	}

	
	public int getID() {
		// TODO Auto-generated method stub
		return name.hashCode();
	}

	public void draw(Graphics g, Sprite s, int x, int y, int width, int height) {
		
		//don't even worry about it... really
		if(this.accelerated){
			ensureImage();
			g.drawImage(image,x,y,x+width,y+height,s.x,s.y,s.x+s.width,s.y+s.height,null);
		}else;
			g.drawImage(presistent,x,y,x+width,y+height,s.x,s.y,s.x+s.width,s.y+s.height,null);
		
	}
	public BufferedImage getImage() {
		return this.presistent;
	}
	
	public void setTransparent(int xloc, int yloc) {
		this.imageType=VolatileImage.BITMASK;
		createImage();
		
		int color=presistent.getRGB(xloc, yloc);
		Graphics2D persistentG=presistent.createGraphics();
		persistentG.setComposite(AlphaComposite.DstOut);
		persistentG.setColor(new Color(0xFF0000FF));
		for (int y = 0; y < presistent.getHeight(); y++) {
		    for (int x = 0; x < presistent.getWidth(); x++) {
		    	
		         int argb = presistent.getRGB(x, y);
		         
		         if (argb==color)
		         {
		        	 persistentG.fillRect(x, y, 1, 1);
		         }
		    }
		}
		redraw();
		
	}
	public void redraw(){
		if(image!=null){
		Graphics2D temp=image.createGraphics();
		temp.setComposite(AlphaComposite.DstOut);
		
	    temp.fillRect(0, 0, image.getWidth(), image.getHeight());
	    temp=image.createGraphics();
	    temp.drawImage(presistent,0,0,null);
		}
	}
	public void createImage(){
		System.out.println("creating new volitile image()");
		try {
			this.accelerated=true;
			image=gc.createCompatibleVolatileImage(presistent.getWidth(), presistent.getHeight(),caps,imageType);
		} catch (AWTException e) {
			this.accelerated=false;
			System.err.println("could not create accelerated image"+e.getMessage());
		}
	}
	




}
