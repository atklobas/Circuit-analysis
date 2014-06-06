package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import resources.Renderable;


public class GridDrawingPanel extends Component implements MouseListener, MouseMotionListener, KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2211479314105403140L;
	private ArrayList<MouseListener> mouseListeners=new ArrayList<MouseListener>();
	private ArrayList<MouseMotionListener> mouseMotionListener=new ArrayList<MouseMotionListener>();
	private ArrayList<KeyListener> keyListeners=new ArrayList<KeyListener>();
	int width;
	int height;
	int gridsize;
	AffineTransform id=new AffineTransform();
	public GridDrawingPanel(int width,int height,int gridsize){
		super();
		super.addMouseListener(this);
		super.addMouseMotionListener(this);
		super.addKeyListener(this);
		this.gridsize=gridsize;
		this.width=width;
		this.height=height;
		glassPane=this.createClearVolatileImage(width, height);
		gg=glassPane.createGraphics();
		this.setSize(width, height);
		
		this.setPreferredSize(new Dimension(width, height));
	}
	int currentImage=1;
	VolatileImage[] image=new VolatileImage[4];
	VolatileImage glassPane;
	Graphics2D g;
	Graphics2D gg;
	
	
	
	public void paint(Graphics g){
		this.requestFocus();

		g.drawImage(image[(currentImage-1)%image.length], 0, 0, this);
		
		
		g.drawImage(glassPane, 0, 0, this);
		
	}
	public void update(Graphics g){
		this.requestFocus();
		this.paint(g);
	}
	
	
	public VolatileImage createClearVolatileImage(int width, int height) {
		VolatileImage ret= GraphicsEnvironment.getLocalGraphicsEnvironment().
	        getDefaultScreenDevice().getDefaultConfiguration().
	        createCompatibleVolatileImage(width, height, Transparency.TRANSLUCENT);
		 	Graphics2D g = ret.createGraphics();
		    g.setComposite(AlphaComposite.DstOut);
		    g.fillRect(0, 0, ret.getWidth(), ret.getHeight());
		    g.dispose();
		return ret;
	}
	
	public void addMouseListener(MouseListener ml){
		this.mouseListeners.add(ml);
	}
	public void addMouseMotionListener(MouseMotionListener ml){
		this.mouseMotionListener.add(ml);
	}
	
	public void addKeyListener(KeyListener k){
		this.keyListeners.add(k);
	}


	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	boolean resize=false;


	
	public void mousePressed(MouseEvent e) {
		if(!e.isConsumed()){
			for(MouseListener ml:this.mouseListeners){
				ml.mousePressed(e);
			}
		}
		
	}


	
	public void mouseReleased(MouseEvent e) {
		
		for(MouseListener ml:this.mouseListeners){
			ml.mouseReleased(e);
		}
		
	}


	
	public void keyPressed(KeyEvent e) {
		for(KeyListener k:this.keyListeners){
			if(!e.isConsumed()){
				k.keyPressed(e);
			}
				
		}
		
	}


	
	public void keyReleased(KeyEvent e) {
		for(KeyListener k:this.keyListeners){
			if(!e.isConsumed())
				k.keyReleased(e);
		}
		
	}


	
	public void keyTyped(KeyEvent e) {
		for(KeyListener k:this.keyListeners){
			if(!e.isConsumed())
				k.keyTyped(e);
		}
		
	}


	
	public void mouseDragged(MouseEvent e) {
		if(!e.isConsumed()){
			for(MouseMotionListener ml:this.mouseMotionListener){
				ml.mouseDragged(e);
			}
		}
		
	}


	
	public void mouseMoved(MouseEvent e) {
		if(!e.isConsumed()){
			for(MouseMotionListener ml:this.mouseMotionListener){
				ml.mouseMoved(e);
			}
		}
		
	}
	public void clear(Graphics2D g2){
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		g2.fillRect(0,0,width,height);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
	}
	
	public void render(List<Renderable> rendered, int x, int y) {
		
		
		if(image[currentImage%image.length]==null){
			image[currentImage%image.length]=this.createVolatileImage(width, height);
		}
		
		g=image[currentImage%image.length].createGraphics();
		
		if(g!=null){
			g.setTransform(id);
			
			g.setColor(Color.white);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.LIGHT_GRAY);
			for(int i=0-x%gridsize, col=x/gridsize;i<width;i+=gridsize,col++){
				if(col%100==0){
					g.setColor(Color.RED);
					g.drawLine(i, 0, i, height);
					g.setColor(Color.LIGHT_GRAY);
				}else if(col%10==0){
					g.setColor(Color.GRAY);
					g.drawLine(i, 0, i, height);
					g.setColor(Color.LIGHT_GRAY);
				}else
				g.drawLine(i, 0, i, height);
			}
			for(int i=0-y%gridsize, row=y/gridsize;i<height;i+=gridsize,row++){
				if(row%100==0){
					g.setColor(Color.RED);
					g.drawLine(0,i, width, i);
					g.setColor(Color.LIGHT_GRAY);
				}else if(row%10==0){
					g.setColor(Color.GRAY);
					g.drawLine(0,i, width, i);
					g.setColor(Color.LIGHT_GRAY);
				}else
				g.drawLine(0,i, width, i);
			}
			clear(gg);
			gg.setColor(Color.BLACK);
			AffineTransform id=new AffineTransform();
			id.translate(-x, -y);
			
			//int xx=(int)center.getElement(0)-width/2;
			//int yy=(int)center.getElement(1)-height/2;
			//g.translate(-centerX, -centerY);
			for(Renderable r:rendered){
				g.setTransform(id);
				r.getSprite().draw(g, r.getX(), r.getY(), r.getScale());
			}
		}
		currentImage++;
		
		
	}
	public void display(){
		
		this.repaint();
	}
		
}
