package circuitAnalysis;

import Components.Component;
import Components.Wire;

public interface Model {
	public void addComponent(Component c);
	public void removeComponent(Component c);
	public int getGridSize();
	public void undoLastCommand();
	public void redoLastCommand();
	public Component getComponentAt(int x, int y);
	public void addWire(Wire w);
	public void removeWire(Wire w);
	public Wire getWireAt(int x, int y);
	public String getVoltageAt(Wire temp);
	public int getComponentNumber(Component p);
	public Component getComponentByNumber(int n);
}
