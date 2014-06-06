package view;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import resources.Renderable;
import Commands.Command;
import Commands.CommandListener;
import Components.Component;

public class CircuitFrame extends JFrame{
	ComponentPanel panel=new ComponentPanel();
	GridDrawingPanel canvas;
	DNDListener listener=new DNDListener();
	private ArrayList<CommandListener> cmdListeners= new ArrayList<CommandListener>();
	private CircuitFrame frame=this;
	public CircuitFrame(int gridSize){
		this.setSize(800, 600);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.addMouseListener(listener);
		
		canvas=new GridDrawingPanel(gridSize);
		
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
		this.canvas.setRenderingList(rendered);
	}
	public void addCommandListener(CommandListener l){
		
	}
	private void fireCommand(Command c){
		
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
				int x=e.getXOnScreen()-p.x;
				int y=e.getYOnScreen()-p.y;
				System.out.println("place "+current+" at ("+x+","+y+")");
			}
			current=null;
		}
		
	}

}
