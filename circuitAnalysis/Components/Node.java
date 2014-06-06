package Components;

import java.util.HashSet;

public class Node {
	private double voltage;
	private HashSet<Component> components = new HashSet<Component>();
	
	
	public void addComponent(Component c){
		if(components.contains(c)){
			components.remove(c);
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
}
