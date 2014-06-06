package circuitAnalysis;

import Components.Component;

public interface Model {
	public void addComponent(Component c);
	public void removeComponent(Component c);
	public int getGridSize();
	public void undoLastCommand();
	public void redoLastCommand();
}
