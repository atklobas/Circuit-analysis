package circuitAnalysis;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Components.Component;
import Components.Resistor;
import resources.ImageResource;
import resources.Renderable;
import resources.ResourceLoader;
import resources.Sprite;
import view.GridDrawingPanel;

public class Main implements MouseListener, MouseMotionListener, KeyListener,Runnable {
	public static final int width = 800;
	public static final int height = 600;
	public static final int gridSize = 10;

	int x, y;//location of the viewport;
	GridDrawingPanel panel = new GridDrawingPanel(width, height, gridSize);
	JFrame frame;
	private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	private ImageResource graphics;
	private ResourceLoader loader = new ResourceLoader();

	private ArrayList<Renderable> components = new ArrayList<Renderable>();

	public Main() throws IOException {
		frame = new JFrame();
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.addMouseListener(panel);
		frame.addMouseMotionListener(panel);
		frame.addKeyListener(panel);

		graphics = new ResourceLoader().LoadImageResource("graphics.png");
		//graphics.setTransparent(0, 0);
		sprites.put("Resistor", graphics.createSprite(32, 13, 58, 13, 3, 7));
		//sprites.put("Resistor", graphics.createSprite(32, 13, 58, 13, 0, 0));
		
		Resistor.setSprite(sprites.get("Resistor"));
		Resistor r=new Resistor(100);
		components.add(r);
		r=new Resistor(100);
		r.setX(50);
		components.add(r);
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		panel.addKeyListener(this);
		
		repaint();
		frame.setVisible(true);
		new Thread(this).start();
		panel.requestFocus();

	}
	public void repaint(){
		panel.render(components, x, y);
		panel.repaint();
	}

	
	int clickedX,clickedY;
	boolean clicked=false;
	@Override
	public void mouseClicked(MouseEvent e) {
		

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		clickedX=e.getX();
		clickedY=e.getY();
		if(e.getButton()==MouseEvent.BUTTON1){
			clicked=true;
			
		}else{
			Resistor r =new Resistor(100);
			r.setX(x+e.getX());
			r.setY(y+e.getY());
			System.out.println(r.getX()+","+r.getY());
			this.components.add(r);
			this.repaint();
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		clicked=false;
		x=x+clickedX-e.getX();
		y=y+clickedY-e.getY();
		repaint();

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(clicked){
			panel.render(components, x+clickedX-e.getX(), y+clickedY-e.getY());
			panel.repaint();
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();
				} catch (IOException e) {
					System.err.println("IO Exception" + e.getMessage());
					e.printStackTrace();
					System.exit(1);
				}
			}
		});

	}
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(50);
				if(!clicked){
					repaint();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("key event"+e.getKeyChar());
		if(e.getKeyCode()==KeyEvent.VK_C){
			this.components.clear();
		}
		
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
