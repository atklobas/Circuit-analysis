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


	@Override
	public Component Clone() {
		// TODO Auto-generated method stub
		return new OpAmp();
	}


	@Override
	public int[][] getConnectionLocations() {
		// TODO Auto-generated method stub
		return null;
	}
}
