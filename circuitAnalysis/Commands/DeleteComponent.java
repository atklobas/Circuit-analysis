package Commands;

import Components.Component;
import circuitAnalysis.Model;

public class DeleteComponent implements Command{
	Component c;
	public DeleteComponent(Component c){
		this.c=c;
	}
	@Override
	public void execute(Model m) {
		m.removeComponent(c);
		
	}

	@Override
	public void undo(Model m) {
		m.addComponent(c);
		
	}

}
