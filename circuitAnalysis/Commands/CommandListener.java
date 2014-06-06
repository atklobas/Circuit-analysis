package Commands;

public interface CommandListener {
	public void performCommand(Command c);
	public void undoLastCommand();
}
