package Components;

import resources.Sprite;

public class AmperageSource extends Component{
	private static Sprite sprite;
	public static void setSprite(Sprite sprite){
		AmperageSource.sprite=sprite;
	}


	@Override
	public Sprite getSprite() {
		return AmperageSource.sprite;
	}


	@Override
	public Component Clone() {
		// TODO Auto-generated method stub
		return new AmperageSource();
	}

}
