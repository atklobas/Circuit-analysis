package Components;

import resources.Sprite;

public class Ground extends Component{
	private static Sprite sprite;
	Wire wire;
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
	@Override
	public int[][] getConnectionLocations() {
		return new int[][]{{0,0}};
	}
	@Override
	public void addWire(int con, Wire w) {
		wire=w;
		
	}
}
