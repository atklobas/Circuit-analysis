package Components;

import resources.Sprite;

public class OpAmp extends Component{
	private static Sprite sprite;
	public static void setSprite(Sprite sprite){
		OpAmp.sprite=sprite;
	}


	@Override
	public Sprite getSprite() {
		return OpAmp.sprite;
	}
}
