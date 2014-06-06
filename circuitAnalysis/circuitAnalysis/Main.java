package circuitAnalysis;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Commands.Command;
import Components.AmperageSource;
import Components.Component;
import Components.DependantAmperageSource;
import Components.DependantVoltageSource;
import Components.Ground;
import Components.OpAmp;
import Components.Resistor;
import Components.VoltageSource;
import resources.ImageResource;
import resources.Renderable;
import resources.ResourceLoader;
import resources.Sprite;
import view.CircuitFrame;
import view.GridDrawingPanel;
import view.LoadingWindow;

public class Main {
	public static final int gridSize = 10;

	int x, y;//location of the viewport;
	//GridDrawingPanel panel = new GridDrawingPanel( gridSize);
	//JFrame frame;
	
	private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	private ImageResource graphics;
	private ResourceLoader loader = new ResourceLoader();
	

	private ArrayList<Renderable> components = new ArrayList<Renderable>();
	private Stack<Command> commands=new Stack<Command>();
	private Stack<Command> undone=new Stack<Command>();
	private Command currentCommand;
	
	public Main() throws IOException {
		
		LoadingWindow loading=new LoadingWindow();
		loading.update(100, "Loading... graphics.png");
		graphics = new ResourceLoader().LoadImageResource("graphics.png");
		loading.update(800, "Cropping Sprites...");
		sprites.put("Resistor", graphics.createSprite(32, 13, 58, 13, 3, 7));
		sprites.put("Op Amp", graphics.createSprite(24,76,90,70,4,15,90));
		sprites.put("Voltage Source", graphics.createSprite(144,4,68,47,3,22));
		sprites.put("Amperage Source", graphics.createSprite(144,132,68,47,3,22));
		sprites.put("Dependant Voltage Source", graphics.createSprite(144,201,68,47,3,22));
		sprites.put("Dependant Amperage Source", graphics.createSprite(144,249,68,47,3,22));
		sprites.put("Ground", graphics.createSprite(27,182,15,26,8,3));
		Resistor.setSprite(sprites.get("Resistor"));
		OpAmp.setSprite(sprites.get("Op Amp"));
		VoltageSource.setSprite(sprites.get("Voltage Source"));
		AmperageSource.setSprite(sprites.get("Amperage Source"));
		DependantVoltageSource.setSprite(sprites.get("Dependant Voltage Source"));
		DependantAmperageSource.setSprite(sprites.get("Dependant Amperage Source"));
		Ground.setSprite(sprites.get("Ground"));
		loading.update(900, "Initializing Display");
		
		CircuitFrame frame =new CircuitFrame(gridSize);
		Component c=new Resistor();
		frame.addAvaliableComponent(c);
		c=new OpAmp();
		frame.addAvaliableComponent(c);
		c=new VoltageSource();
		frame.addAvaliableComponent(c);
		c=new AmperageSource();
		frame.addAvaliableComponent(c);
		c=new DependantVoltageSource();
		frame.addAvaliableComponent(c);
		c=new DependantAmperageSource();
		frame.addAvaliableComponent(c);
		c=new Ground();
		frame.addAvaliableComponent(c);
		
		loading.dispose();

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
	
	
	
}
