package Components;

import resources.Sprite;

public class VoltageSource extends Component{
	private static Sprite sprite;
	public static void setSprite(Sprite sprite){
		VoltageSource.sprite=sprite;
	}
	
	//add current variable for node voltage calculation 
	//add line like ncalling-nrecieving = voltage
	Wire one;
	Wire two;
	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return VoltageSource.sprite;
	}
}
