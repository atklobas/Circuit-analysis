package Components;

import resources.Sprite;

public class Ground extends Component{
	private static Sprite sprite;
	public static void setSprite(Sprite sprite){
		Ground.sprite=sprite;
	}
	@Override
	public Sprite getSprite() {
		return Ground.sprite;
	}
	@Override
	public Component Clone() {
		// TODO Auto-generated method stub
		return new Ground();
	}
}
