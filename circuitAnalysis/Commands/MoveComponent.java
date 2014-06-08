package Commands;

import Components.Component;
import circuitAnalysis.Model;

public class MoveComponent implements Command{
	Component c;
	int newX, newY, oldX, oldY;
	public MoveComponent(Component c, int newX, int newY){
		this.c=c;
		this.newX=newX;
		this.oldX=c.getX();
		this.newY=newY;
		this.oldY=c.getY();
	}
	
	@Override
	public void execute(Model m) {
		c.setX(newX);
		c.setY(newY);
	}

	@Override
	public void undo(Model m) {
		c.setX(oldX);
		c.setY(oldY);
		
	}

}
