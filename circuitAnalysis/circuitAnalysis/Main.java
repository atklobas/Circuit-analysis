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

import mathematics.Matrix;
import Commands.Command;
import Commands.CommandListener;
import Commands.CreateWire;
import Commands.PlaceComponent;
import Components.AmperageSource;
import Components.Component;
import Components.DependantAmperageSource;
import Components.DependantVoltageSource;
import Components.Ground;
import Components.Node;
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
	

	private LinkedList<Renderable> components = new LinkedList<Renderable>();
	private Stack<Command> commands=new Stack<Command>();
	private Stack<Command> undone=new Stack<Command>();
	private Command currentCommand;
	private void addSimpleCircuit(){
		this.performCommand(new CreateWire(10,10,10,100));
		VoltageSource vs=new VoltageSource();
		Command c =new PlaceComponent(vs);
		((PlaceComponent)c).setLocation(10, 10);
		this.performCommand(c);
		Resistor r=new Resistor();
		 c =new PlaceComponent(r);
		((PlaceComponent)c).setLocation(10, 100);
		this.performCommand(c);
		this.performCommand(new CreateWire(70,10,60,100));
		 c =new PlaceComponent(new Ground());
			((PlaceComponent)c).setLocation(10, 100);
			this.performCommand(c);
		
	}
	
	public Main() throws IOException {
	/*double[][] m={
			{0.01, 0.0, 0.0, -0.01, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0},
			{0.0, 0.02, -0.01, 0.0, 0.0, -0.01, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
			{0.0, -0.01, 0.02, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.01, 0.0, 0.0, 0.0, 0.0},
			{-0.01, 0.0, 0.0, 0.02, 0.0, 0.0, 0.0, 0.0, 0.0, -0.01, 0.0, 0.0, 0.0, 0.0, 0.0},
			{0.0, 0.0, 0.0, 0.0, 0.01, -0.01, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0, 0.0},
			{0.0, -0.01, 0.0, 0.0, -0.01, 0.04, 0.0, 0.0, -0.02, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
			{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.02, 0.0, 0.0, 0.0, -0.01, 0.0, -0.01, 0.0, 0.0},
			{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.02, 0.0, 0.0, 0.0, -0.01, -0.01, 0.0, 0.0},
			{0.0, 0.0, 0.0, 0.0, 0.0, -0.02, 0.0, 0.0, 0.02, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
			{0.0, 0.0, 0.0, -0.01, 0.0, 0.0, 0.0, 0.0, 0.0, 0.02, 0.0, -0.01, 0.0, 0.0, 0.0},
			{0.0, 0.0, -0.01, 0.0, 0.0, 0.0, -0.01, 0.0, 0.0, 0.0, 0.02, 0.0, 0.0, 0.0, 0.0},
			{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.01, 0.0, -0.01, 0.0, 0.02, 0.0, 0.0, 0.0},
			{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.01, -0.01, 0.0, 0.0, 0.0, 0.0, 0.02, 0.0, 0.0},
			{-1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 40.0},
			{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
	};
	Matrix mat=new Matrix(m);
	System.out.println(mat+"\n");
		mat=mat.rref2();
		System.out.println(mat+"\n");
		System.exit(0);/**/
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
		frame.setRenderingList(this.components);;
		frame.setWireList(this.allWires);
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
		addSimpleCircuit();

	}
	public void addComponent(Component c){
		this.components.add(c);
	}
	public void removeComponent(Component c){
		System.out.println("remoing "+c);
		this.components.remove(c);
	}
	public int getGridSize(){
		return Main.gridSize;
	}
	public void addWire(Wire w){
		this.allWires.add(w);
	}
	public void removeWire(Wire w){
		this.allWires.remove(w);
	}
	public Wire getWireAt(int x, int y){
		//snaps to grid;
		x=((x+this.gridSize/2)/gridSize)*gridSize;
		y=((y+this.gridSize/2)/gridSize)*gridSize;
		for(Wire w:allWires){
			if(w.pointInside(x, y)){
				
				return w;
			}
		}
		return null;
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
		return null;
	}
	@Override
	public double getVoltageAt(Wire of) {
		HashSet<Wire> tempWires= new HashSet<Wire>();
		for(Wire w:allWires){
			w.reset();
		}
		
		for(Renderable r:this.components){
			Component c=(Component)r;
			int i=0;
			for(int[] pnt:c.getConnectionLocations()){
				Wire temp=this.getWireAt(pnt[0]+c.getX(), pnt[1]+c.getY());
				if(temp==null){
					temp=new Wire(pnt[0]+c.getX(), pnt[1]+c.getY(),pnt[0]+c.getX(), pnt[1]+c.getY());
					tempWires.add(temp);
				}
				c.addWire(i, temp);
				temp.addComponent(c);
				i++;
			}
		}
		allWires.addAll(tempWires);
		for(Wire w2: allWires){
			if(w2.getX1()==15&&w2.getX2()==17&&w2.getY1()==15){
				System.out.println("its here");
			}
			for(Wire w: allWires){
				if(w!=w2&&w.intersects(w2)){
					w.addWire(w2);
				}
			}
		}
		
		
		
		HashSet<Node> nodes=Wire.makeNodes(allWires);
		allWires.removeAll(tempWires);
		Node[] nO=new Node[nodes.size()];
		for(Node n:nodes)nO[n.getID()]=n;
		
		int columns=nodes.size()+1;
		int rows=nodes.size();
		for(Renderable r:this.components){
			Component c=(Component)r;
			columns+=c.getAdditionalVariables();
			rows+=c.getAdditionalRows();
		}
		
		for(Node n:nO){
			System.out.println(n);
			for(Component c:n.getComponents()){
				System.out.println("\t"+c);
				
			}
		}
		
		Matrix m=new Matrix(rows, columns);
		int row=nodes.size();
		int column=row;
		for(Renderable r:this.components){
			Component c=(Component)r;
			c.addEquations(m, row, column);
			column+=c.getAdditionalVariables();
			row+=c.getAdditionalRows();
		}
		System.out.println("numNodes="+nodes.size());
		System.out.println(m+"\n");
		m=m.rref2();
		//mp.
		System.out.println("final:\n"+m);
		System.out.println("requesting"+of.getNode().getID());
		return m.getValue(of.getNode().getID(), m.getColumns()-1);
		//return "min arraySize: "+rows+"X"+columns+" selected node: "+of.getNode().getID();
	}

	@Override
	public int getComponentNumber(Component p) {
		return this.components.indexOf(p);
	}

	@Override
	public Component getComponentByNumber(int n) {
		if(n<1||n>components.size()-1) return null;
		return (Component)components.get(n);
	}

	
	
	
}
