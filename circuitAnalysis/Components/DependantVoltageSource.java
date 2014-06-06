package Components;

import resources.Sprite;

public class DependantVoltageSource extends Component{
	private static Sprite sprite;
	public static void setSprite(Sprite sprite){
		DependantVoltageSource.sprite=sprite;
	}
	@Override
	public Sprite getSprite() {
		return DependantVoltageSource.sprite;
	}
	@Override
	public Component Clone() {
		// TODO Auto-generated method stub
		return new DependantVoltageSource();
	}
}
