package Commands;

import Components.Wire;
import circuitAnalysis.Model;

public class CreateWire implements Command{
	Wire wire1,wire2;
	public CreateWire(int x1, int y1,int x2,int y2){
		if(Math.abs(x1-x2)>=Math.abs(y1-y2)){
			wire1 = new Wire(x1, y1, x2, y1);
			wire2 = new Wire(x2, y1, x2, y2);
		}else{
			wire1 = new Wire(x1, y1, x1, y2);
			wire2 = new Wire(x1, y2, x2, y2);
		}
	}
	@Override
	public void execute(Model m) {
		m.addComponent(c);
		
	}

	@Override
	public void undo(Model m) {
		// TODO Auto-generated method stub
		
	}

}
