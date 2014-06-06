package Commands;

import java.util.EnumMap;
import java.util.HashMap;


public class InputBinder {
	EnumMap<Panel,HashMap<Integer,Command>> map= new 	EnumMap<Panel,HashMap<Integer,Command>>(Panel.class);
	public InputBinder(){
		for(Panel p:Panel.values()){
			map.put(p, new HashMap<Integer,Command>());
		}
	}
	public Command getCommand(Panel p,int i){
		return map.get(p).get(i).clone();
	}
	public void bind(Panel p,int i, Command c){
		map.get(p).put(i, c);
	}

}
