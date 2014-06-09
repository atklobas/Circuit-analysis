package Components;

import circuitAnalysis.Main;
import resources.Sprite;

public class AmperageSource extends Component{
	private static Sprite sprite;
	private int[] node1 = new int[]{0,0}, node2 = new int[]{6,0};
	Wire[] wires=new Wire[2];
	
	private void setNode2(){
		if(this.getAngle()==0){
			node2 = new int[]{6,0};
		}else if(this.getAngle()==90){
			node2 = new int[]{0,6};
		}else if(this.getAngle()==180){
			node2 = new int[]{-6,0};
		}else if(this.getAngle()==270){
			node2 = new int[]{0,-6};
		}
	}
	public void rotateClockwise(){
		super.rotateClockwise();
		setNode2();
	}
	public void rotateAntiClockwise(){
		super.rotateAntiClockwise();
		setNode2();
	}
	public int[][] getConnectionLocations() {
		int[][] ret={{0,0},{node2[0]*Main.gridSize,node2[1]*Main.gridSize}};
		return ret;
	}
	
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


	@Override
	public void addWire(int con, Wire w) {
		wires[con]=w;
	}

}
