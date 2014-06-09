package Components;

import java.util.HashSet;

import circuitAnalysis.Main;

public class Wire {
	private HashSet<Wire> wires = new HashSet<Wire>();
	private HashSet<Component> components = new HashSet<Component>();
	private Node node;
	private boolean populated = false;
	private int x1, y1, x2, y2;

	public int getX1() {
		return x1*Main.gridSize;
	}


	public int getY1() {
		return y1*Main.gridSize;
	}


	public int getX2() {
		return x2*Main.gridSize;
	}


	public int getY2() {
		return y2*Main.gridSize;
	}


	public Wire(int x1, int y1,int x2, int y2){
		this.x1=(x1+Main.gridSize/2)/Main.gridSize;
		this.y1=(y1+Main.gridSize/2)/Main.gridSize;
		this.x2=(x2+Main.gridSize/2)/Main.gridSize;
		this.y2=(y2+Main.gridSize/2)/Main.gridSize;
	}

	
	public void addWire(Wire w){
		this.wires.add(w);
		w.wires.add(this);
	}
	
	public void addComponent(Component c){
		this.components.add(c);
	}
	
	
	public void populateNodes(Node n){
		this.node=n;
		populated=true;
		//System.out.println("Wire "+this.ID+" populated! ");
		for(Wire w:wires){
			if(!w.populated){
				w.populateNodes(n);	
			}
		}
	}
	
	
	//this method assumes that a wire can only be vertical, horizontal, or a point.
	public boolean pointInside(int x, int y){
		x/=Main.gridSize;
		y/=Main.gridSize;
		boolean vertical = false, horizontal = false;
		if(this.x1==this.x2){//vertical
			vertical = true;
		}
		if(this.y1==this.y2){//horizontal
			horizontal=true;
		}
		
		if(horizontal&&vertical){//point wire
			if(this.x1==x && this.y1==y){
				return true;
			}
		}else if(horizontal){
			if(this.y1==y){
				return true;
			}
		}else if(vertical){
			if(this.x1==x){
				return true;
			}
		}
		
		return false;
	}
	
	public static HashSet<Node> makeNodes(HashSet<Wire> allWires){
		HashSet<Node> allNodes = new HashSet<Node>();

		for(Wire w1:allWires){
			w1.populated=false;
		}
		
		for(Wire w1:allWires){
			if(w1.node==null){
				w1.populateNodes(new Node());
				allNodes.add(w1.node);
			}
			for(Component c:w1.components){
				w1.node.addComponent(c);
			}
		}
		
		
		return allNodes;
	}
	
}
