package Components;

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
		String ret="Node";
		for(Component c:components){
			ret+=", "+c.toString();
		}
		ret+=" !!!";
		return ret;
	}
	public HashSet<Component> getComponents() {
		return components;
	}
}
