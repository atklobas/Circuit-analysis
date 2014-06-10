package Commands;

import java.util.HashMap;

import Components.Component;
import circuitAnalysis.Model;

public class EditComponent implements Command{
	protected HashMap<String, Double> oldFields;
	protected HashMap<String, Double> newFields;
	Component c;
	@SuppressWarnings("unchecked")
	public EditComponent(Component c, HashMap<String, Double> newFields){
		this.c=c;
		oldFields=(HashMap<String, Double>)c.getEditableFields().clone();
		this.newFields=(HashMap<String, Double>)newFields.clone();
	}
	@Override
	public void execute(Model m) {
		HashMap<String, Double> fields= c.getEditableFields();
		for(String s: newFields.keySet()){
			fields.put(s, newFields.get(s));
		}
		
	}

	@Override
	public void undo(Model m) {
		HashMap<String, Double> fields= c.getEditableFields();
		for(String s: oldFields.keySet()){
			fields.put(s, oldFields.get(s));
		}
		
	}

}
