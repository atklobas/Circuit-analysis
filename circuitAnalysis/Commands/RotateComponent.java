package Commands;

import circuitAnalysis.Model;
import Components.Component;

public class RotateComponent implements Command{
	Component c;
	public RotateComponent(Component c){
		this.c=c;
	}
	@Override
	public void execute(Model m) {
		c.rotateClockwise();
		
	}
	@Override
	public void undo(Model m) {
		c.rotateAntiClockwise();
		
	}
	

}
