package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.List;

import resources.Renderable;


public class GridDrawingPanel extends Component implements ComponentListener{
	private static final long serialVersionUID = 2211479314105403140L;
	
	private LocationUpdater ml=new LocationUpdater();
	int gridsize;
	int currentImage=1;
	VolatileImage[] image=new VolatileImage[4];
	Graphics2D g;
	//this list will be shared with and edited externally
	private List<Renderable> rendered=null;
	int x=0, y=0;
	
	AffineTransform id=new AffineTransform();
	public GridDrawingPanel(int gridsize){
		super();
		this.gridsize=gridsize;
		this.addComponentListener(this);
		this.addMouseListener(ml);
		this.addMouseMotionListener(ml);
	}
	public void setCenterPoint(int x, int y){
		this.x=x;
		this.y=y;
		this.repaint();
	}
	/**
	 * This method sets the list of components this panel will draw, 
	 * the list will never be modified by this class
	 * @param rendered a list containing everything to be drawn
	 */
	public void setRenderingList(List<Renderable> rendered){
		this.rendered=rendered;
		this.repaint();
	}
	
	
	
	
	public void paint(Graphics g){
		this.render();
		g.drawImage(image[(currentImage-1)%image.length], 0, 0, this);
		
		
	}
	public void update(Graphics g){
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
	
	
	
	public void render() {
		
		
		if(image[currentImage%image.length]==null){
			image[currentImage%image.length]=this.createVolatileImage(this.getWidth(), this.getHeight());
		}
		
		g=image[currentImage%image.length].createGraphics();
		
		if(g!=null){
			g.setTransform(id);
			
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.LIGHT_GRAY);
			for(int i=0-x%gridsize, col=x/gridsize;i<this.getWidth();i+=gridsize,col++){
				if(col%100==0){
					g.setColor(Color.RED);
					g.drawLine(i, 0, i, this.getHeight());
					g.setColor(Color.LIGHT_GRAY);
				}else if(col%10==0){
					g.setColor(Color.GRAY);
					g.drawLine(i, 0, i, this.getHeight());
					g.setColor(Color.LIGHT_GRAY);
				}else
				g.drawLine(i, 0, i, this.getHeight());
			}
			for(int i=0-y%gridsize, row=y/gridsize;i<this.getHeight();i+=gridsize,row++){
				if(row%100==0){
					g.setColor(Color.RED);
					g.drawLine(0,i, this.getWidth(), i);
					g.setColor(Color.LIGHT_GRAY);
				}else if(row%10==0){
					g.setColor(Color.GRAY);
					g.drawLine(0,i, this.getWidth(), i);
					g.setColor(Color.LIGHT_GRAY);
				}else
				g.drawLine(0,i, this.getWidth(), i);
			}
			AffineTransform id=new AffineTransform();
			id.translate(-x, -y);
			
			if(rendered!=null){
				for(Renderable r:rendered){
					g.setTransform(id);
					r.getSprite().draw(g, r.getX(), r.getY(), r.getScale());
				}
			}
		}
		currentImage++;
		
		
	}
	public void display(){
		this.repaint();
	}
	@Override
	public void componentHidden(ComponentEvent arg0) {}
	@Override
	public void componentMoved(ComponentEvent arg0) {}
	@Override
	public void componentResized(ComponentEvent arg0) {
		image=new VolatileImage[4];
	}
	@Override
	public void componentShown(ComponentEvent arg0) {}
	
	private class LocationUpdater implements MouseMotionListener, MouseListener{
		int clickedX,clickedY;
		int x=0,y=0;
		boolean held=false;
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getButton()==MouseEvent.BUTTON1){
				clickedX=e.getX();
				clickedY=e.getY();
				held=true;
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getButton()==MouseEvent.BUTTON1){
				held=false;
				x=x+clickedX-e.getX();
				y=y+clickedY-e.getY();
				setCenterPoint(x,y);
			}
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if(held){
				setCenterPoint(x+clickedX-e.getX(), y+clickedY-e.getY());
			}
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
		
}
