package Commands;

import circuitAnalysis.Model;
import Components.Component;

public class PlaceComponent implements Command{
	int x;
	int y;
	Component c;
	public PlaceComponent(Component c) {
		super();
		this.c = c.Clone();
	}
	public Component getComponent(){
		return c;
	}
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	@Override
	public void execute(Model m) {
		c.setX(x);
		c.setY(y);
		m.addComponent(c);
		
	}
	@Override
	public void undo(Model m) {
		m.removeComponent(c);
		
	}


	

}
