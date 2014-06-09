package Components;

import resources.Sprite;

public class DependantAmperageSource extends Component{
	private static Sprite sprite;
	public static void setSprite(Sprite sprite){
		DependantAmperageSource.sprite=sprite;
	}
	
	@Override
	public Sprite getSprite() {
		return DependantAmperageSource.sprite;
	}

	@Override
	public Component Clone() {
		// TODO Auto-generated method stub
		return new DependantAmperageSource();
	}

	@Override
	public int[][] getConnectionLocations() {
		// TODO Auto-generated method stub
		return null;
	}
}
