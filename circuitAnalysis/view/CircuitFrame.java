package view;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import circuitAnalysis.Model;
import resources.Renderable;
import Commands.Command;
import Commands.CommandListener;
import Commands.PlaceComponent;
import Components.Component;

public class CircuitFrame extends JFrame{
	ComponentPanel panel=new ComponentPanel();
	GridDrawingPanel canvas;
	DNDListener listener=new DNDListener();
	private Model m;
	
	private ArrayList<CommandListener> cmdListeners= new ArrayList<CommandListener>();
	private CircuitFrame frame=this;
	public CircuitFrame(Model m){
		this.m=m;
		this.setSize(800, 600);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(new KeyInput());
		panel.addMouseListener(listener);
		
		canvas=new GridDrawingPanel(m.getGridSize());
		
		this.add(BorderLayout.WEST,panel);
		this.add(BorderLayout.CENTER,canvas);
		this.setVisible(true);
		
	}
	
	public void addAvaliableComponent(Component p){
		panel.addAvaliableComponent(p);
	}
	/**
	 * This method sets the list of components this panel will draw, 
	 * the list will never be modified by this class
	 * @param rendered a list containing everything to be drawn
	 */
	public void setRenderingList(List<Renderable> rendered){
		System.out.println("setting rendering list");
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
	
	
	private class DNDListener implements MouseListener{
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

}
