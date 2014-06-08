package Components;

import java.util.HashSet;

public class Wire {
	private static int created = 0;
	private static HashSet<Wire> allWires = new HashSet<Wire>();
	private int ID = ++created;
	private HashSet<Wire> wires = new HashSet<Wire>();
	private HashSet<Component> components = new HashSet<Component>();
	private Node node;
	private boolean populated = false;
	
	
	public Wire(int x1, int y1,int x2, int y2){
		allWires.add(this);
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
	
	public static HashSet<Node> makeNodes(){
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
