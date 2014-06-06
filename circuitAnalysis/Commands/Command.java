package Commands;

import circuitAnalysis.Model;

public interface Command{
	public void execute(Model m);
	public void undo(Model m);
}
