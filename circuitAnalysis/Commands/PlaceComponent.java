package Commands;

import circuitAnalysis.Model;
import Components.Component;

public class PlaceComponent implements Command{
	int x;
	int y;
	Component c;
	public PlaceComponent(int x, int y, Component c) {
		super();
		this.x = x;
		this.y = y;
		this.c = c.Clone();
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
