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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Commands.Command;
import Commands.CommandListener;
import Components.AmperageSource;
import Components.Component;
import Components.DependantAmperageSource;
import Components.DependantVoltageSource;
import Components.Ground;
import Components.OpAmp;
import Components.Resistor;
import Components.VoltageSource;
import Components.Wire;
import resources.ImageResource;
import resources.Renderable;
import resources.ResourceLoader;
import resources.Sprite;
import view.CircuitFrame;
import view.GridDrawingPanel;
import view.LoadingWindow;

public class Main implements Model, CommandListener{
	public static final int gridSize = 10;
	int x, y;//location of the viewport;
	//GridDrawingPanel panel = new GridDrawingPanel( gridSize);
	//JFrame frame;
	
	private HashSet<Wire> allWires = new HashSet<Wire>();
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
		sprites.put("Op Amp", graphics.createSprite(24,76,90,70,4,15));
		sprites.put("Voltage Source", graphics.createSprite(144,4,68,45,3,22));
		sprites.put("Amperage Source", graphics.createSprite(144,132,68,45,3,22));
		sprites.put("Dependant Voltage Source", graphics.createSprite(144,201,68,35,3,17));
		sprites.put("Dependant Amperage Source", graphics.createSprite(144,249,68,35,3,17));
		sprites.put("Ground", graphics.createSprite(27,182,15,26,8,3));
		Resistor.setSprite(sprites.get("Resistor"));
		OpAmp.setSprite(sprites.get("Op Amp"));
		VoltageSource.setSprite(sprites.get("Voltage Source"));
		AmperageSource.setSprite(sprites.get("Amperage Source"));
		DependantVoltageSource.setSprite(sprites.get("Dependant Voltage Source"));
		DependantAmperageSource.setSprite(sprites.get("Dependant Amperage Source"));
		Ground.setSprite(sprites.get("Ground"));
		loading.update(900, "Initializing Display");
		
		CircuitFrame frame =new CircuitFrame(this);
		frame.setRenderingList(this.components);
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
		frame.addCommandListener(this);
		loading.dispose();

	}
	public void addComponent(Component c){
		this.components.add(c);
	}
	public void removeComponent(Component c){
		this.components.remove(c);
	}
	public int getGridSize(){
		return Main.gridSize;
	}
	public void addWire(Wire w){
		this.allWires.add(w);
	}
	public Wire getWireAt(int x, int y){
		Wire ret = null;
		for(Wire w:allWires){
			ret=w;
		}
		return ret;
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
	public void performCommand(Command c) {
		c.execute(this);
		commands.push(c);
		undone.clear();
	}
	@Override
	public void undoLastCommand() {
		if(!commands.isEmpty()){
			Command temp= commands.pop();
			temp.undo(this);
			this.undone.push(temp);
		}
		
	}
	@Override
	public void redoLastCommand() {
		if(!undone.isEmpty()){
			Command temp= undone.pop();
			temp.execute(this);;
			this.commands.push(temp);
		}
		
	}
	@Override
	public Component getComponentAt(int x, int y) {
		
		for(Renderable r:this.components){
			if(r instanceof Component){
				if(((Component) r).isInBounds(x, y)){
					return (Component)r;
				}
			}
		}
		System.out.println("found nothing at ("+x+","+y+")");
		return null;
	}
	
	
	
}
