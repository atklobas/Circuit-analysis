package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import circuitAnalysis.Model;
import resources.Renderable;
import Commands.Command;
import Commands.CommandListener;
import Commands.DeleteComponent;
import Commands.MoveComponent;
import Commands.PlaceComponent;
import Commands.RotateComponent;
import Components.Component;

public class CircuitFrame extends JFrame{
	ComponentPanel panel=new ComponentPanel();
	GridDrawingPanel canvas;
	DNDListener dndl=new DNDListener();
	ModificationListener modl= new ModificationListener();
	private Model m;
	drawingPanel glassPane=new drawingPanel();
	private ArrayList<CommandListener> cmdListeners= new ArrayList<CommandListener>();
	private CircuitFrame frame=this;
	public CircuitFrame(Model m){
		this.setGlassPane(glassPane);
		this.getGlassPane().setVisible(true);
		//this.getGlassPane().setBackground(new Color(0xFF000000));
		this.m=m;
		this.setSize(800, 600);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(new KeyInput());
		panel.addMouseListener(dndl);
		panel.addMouseMotionListener(dndl);
		
		canvas=new GridDrawingPanel(m.getGridSize());
		canvas.addMouseListener(modl);
		canvas.addMouseMotionListener(modl);
		
		this.add(BorderLayout.WEST,panel);
		this.add(BorderLayout.CENTER,canvas);
		this.setVisible(true);
		
	}
	
	public void addAvaliableComponent(Component p){
		panel.addAvaliableComponent(p);
	}
	public void innerPaint(Graphics g){
		Point p=MouseInfo.getPointerInfo().getLocation() ;
		Point p2=glassPane.getLocationOnScreen();
		p.translate(-p2.x, -p2.y);
		
		Component selected=null;
		if(dndl.current!=null){
			selected=dndl.current;
		}else if(modl.moving&&this.modl.selected!=null){
			selected=modl.selected;
			p.translate(-modl.xoff, -modl.yoff);
			p.translate(-canvas.getCenterX(), -canvas.getCenterY());
		}
		
		if(selected!=null){
			selected.getSprite().draw(((Graphics2D)g), p.x, p.y, 1.,selected.getAngle());
		}
	}

	
	/**
	 * This method sets the list of components this panel will draw, 
	 * the list will never be modified by this class
	 * @param rendered a list containing everything to be drawn
	 */
	public void setRenderingList(List<Renderable> rendered){
		this.canvas.setRenderingList(rendered);
	}
	public void addCommandListener(CommandListener l){
		cmdListeners.add(l);
	}
	private void fireCommand(Command c){
		for(CommandListener l: cmdListeners){
			l.performCommand(c);
		}
		this.repaint();
	}
	
	/*
	 * Listeners
	 */
	private class ModificationListener implements MouseListener, MouseMotionListener{
		//TODO find assurances that events are dispatched to listeners in the order they were added;
		private Component selected;
		private boolean moving;
		private int xoff, yoff;
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(!e.isConsumed()){
				moving=true;
				selected=m.getComponentAt(e.getX()+canvas.getCenterX(), e.getY()+canvas.getCenterY());
				if(selected!=null){
					xoff=(e.getX()-selected.getX());
					yoff=(e.getY()-selected.getY());
				}
			}
				
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(!e.isConsumed()){
				if(selected!=null){
					fireCommand(new MoveComponent(selected,e.getX()-xoff,e.getY()-yoff));
					moving=false;
					repaint();
				}
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if(!e.isConsumed()){
				repaint();
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		
		
	}
	private class DNDListener implements MouseListener, MouseMotionListener{
		Component current=null;
		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getComponent()instanceof ComponentPanel.ComponentPane){
				current=((ComponentPanel.ComponentPane)e.getComponent()).getComponent();
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(current!=null){
				Point p=canvas.getLocationOnScreen();
				int x=e.getXOnScreen()-p.x+canvas.getCenterX();
				int y=e.getYOnScreen()-p.y+canvas.getCenterY();
				fireCommand(new PlaceComponent(x,y,current));
				System.out.println("place "+current+" at ("+x+","+y+")");
			}
			current=null;
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			repaint();
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {

			
		}
	}
	private class KeyInput implements KeyListener{
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.isControlDown()){
				switch(e.getKeyCode()){
				case KeyEvent.VK_Z:
					m.undoLastCommand();
					break;
				case KeyEvent.VK_Y:
					m.redoLastCommand();
					break;
				}
			}else{
				switch(e.getKeyCode()){
				case KeyEvent.VK_DELETE:
				case KeyEvent.VK_BACK_SPACE:
					if(!modl.moving&&modl.selected!=null){
						fireCommand(new DeleteComponent(modl.selected));
					}
					break;
				case KeyEvent.VK_SPACE:
					if(!modl.moving&&modl.selected!=null){
						fireCommand(new RotateComponent(modl.selected));
					}
					break;
					
				}
			}
			repaint();
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

	
	
	
	
	/*
	 * Useful Objects
	 */
	
	private class drawingPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		public drawingPanel(){
			this.setOpaque(false);
		}
		public void paintComponent(Graphics g)  
		   {  
			innerPaint(g);
		}
	}

	
}
