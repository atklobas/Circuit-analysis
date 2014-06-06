package Commands;

public interface Command extends Cloneable{
	public void execute();
	public void undo();
	public void setStart();
	public Command clone();
}
