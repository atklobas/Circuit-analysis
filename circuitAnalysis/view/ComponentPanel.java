package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import resources.Sprite;
import Components.Component;

public class ComponentPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	public ComponentPanel(){
		Dimension pref=new Dimension(200,-1);
		this.setPreferredSize(pref);
	}
	
	public void addAvaliableComponent(Component p) {
		this.add(new ComponentPane(p));
		
	}
	
	
	
	
	
	
	
	private class ComponentPane extends java.awt.Component{
		private static final long serialVersionUID = 1L;
		private int width=95,height=95;
		private Component c;
		
		public ComponentPane(Component c){
			this.c=c;
			Dimension d=new Dimension(width,height);
			this.setPreferredSize(d);
			this.setMaximumSize(d);
			this.setMinimumSize(d);
			this.setSize(d);
		}
		
		public void paint(Graphics g){
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.gray);
			g.drawRect(0, 0, width-1, height-1);
			Sprite temp=c.getSprite();
			g.translate(width/2-temp.getWidth()/2, height/2-temp.getHeight()/2);
			temp.DrawFromtopCorner(g);

			
		}
		public void update(Graphics g){
			this.paint(g);
		}
		
		
	}






	
}
