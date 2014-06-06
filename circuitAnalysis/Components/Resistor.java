package Components;

import resources.Sprite;

public class Resistor extends Component{
	private static Sprite sprite;
	public static void setSprite(Sprite sprite){
		Resistor.sprite=sprite;
	}
	private int resistance;
	public Resistor(){
		this(100);
	}
	public Resistor(int resistance) {
		this.resistance=resistance;
	}

	@Override
	public Sprite getSprite() {
		return Resistor.sprite;
	}
	
	public Resistor Clone(){
		return new Resistor(resistance);
	}
	public String toString(){
		return ""+resistance;
	}

}
