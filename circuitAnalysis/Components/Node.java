package Components;

import java.util.ArrayList;
import java.util.HashSet;

public class Node {
	private int ID;
	private double voltage;
	private HashSet<Component> components = new HashSet<Component>();
	
	public Node(int ID){
		this.ID=ID;
	}
	
	public void addComponent(Component c){
		if(components.contains(c)){
			//components.remove(c);
		}else{
			components.add(c);
		}
	}
	public String toString(){
		String ret="Node"+ID;
		ret+=wires;
		return ret;
	}
	public HashSet<Component> getComponents() {
		return components;
	}

	public int getID() {
		return ID;
	}
	private ArrayList<Wire> wires= new ArrayList<Wire>();
	public void addWire(Wire wire) {
		wires.add(wire);
		
	}
}
