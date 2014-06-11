package Components;

import java.util.HashMap;

import mathematics.Matrix;
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
		wire=null;
		return new int[][]{{0,0}};
	}
	@Override
	public void addWire(int con, Wire w) {
		wire=w;
		
	}
	@Override
	public int addEquations(Matrix m, int row, int column) {
		int node1=this.wire.getNode().getID();
		//System.err.println("#"+col);
		m.add(node1, column, 1);
		m.add(row, node1, 1);
		return 0;
	}

	public int getAdditionalRows() {
		return 1;
	}public int getAdditionalVariables() {
		return 1;
	}
}
