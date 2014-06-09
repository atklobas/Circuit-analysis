package Components;

import mathematics.Matrix;
import circuitAnalysis.Main;
import resources.Sprite;

public class OpAmp extends Component{
	private static Sprite sprite;
	private int[] node1 = new int[]{0,0}, node2 = new int[]{0,4}, node3 = new int[]{8,2};
	Wire[] wires=new Wire[3];
	
	private void setNodes(){
		if(this.getAngle()==0){
			node2 = new int[]{0,4};
			node3 = new int[]{8,2};
		}else if(this.getAngle()==90){
			node2 = new int[]{-4,0};
			node3 = new int[]{-2,8};
		}else if(this.getAngle()==180){
			node2 = new int[]{0,-4};
			node3 = new int[]{-8,-2};
		}else if(this.getAngle()==270){
			node2 = new int[]{4,0};
			node3 = new int[]{2,-8};
		}
	}
	public void rotateClockwise(){
		super.rotateClockwise();
		setNodes();
	}
	public void rotateAntiClockwise(){
		super.rotateAntiClockwise();
		setNodes();
	}
	
	
	
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
		int[][] ret={{0,0},{node2[0]*Main.gridSize,node2[1]*Main.gridSize},{node3[0]*Main.gridSize,node3[1]*Main.gridSize}};
		return ret;
	}


	@Override
	public void addWire(int con, Wire w) {
		wires[con]=w;
	}
	@Override
	public int addEquations(Matrix m, int row, int column) {
		// TODO Auto-generated method stub
		return 0;
	}

}
