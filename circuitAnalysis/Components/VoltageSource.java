package Components;

import mathematics.Matrix;
import circuitAnalysis.Main;
import resources.Sprite;

public class VoltageSource extends Component{
	private static Sprite sprite;
	private int[] node1 = new int[]{0,0}, node2 = new int[]{6,0};
	Wire[] wires=new Wire[2];
	private double voltage=40;
	public static void setSprite(Sprite sprite){
		VoltageSource.sprite=sprite;
	}
	
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
	
	//add current variable for node voltage calculation 
	//add line like ncalling-nrecieving = voltage
	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return VoltageSource.sprite;
	}
	@Override
	public Component Clone() {
		return new VoltageSource();
	}

	@Override
	public int[][] getConnectionLocations() {
		//this.wires=new Wire[2];
		int[][] ret={{0,0},{node2[0]*Main.gridSize,node2[1]*Main.gridSize}};
		return ret;
	}

	@Override
	public void addWire(int con, Wire w) {
		wires[con]=w;
	}
	public String toString(){
		return this.voltage+" volts";
	}

	@Override
	public int addEquations(Matrix m, int row, int column) {
		int node1=this.wires[0].getNode().getID();
		int node2=this.wires[1].getNode().getID();
		//System.err.println("##"+node1+","+node2);
		m.add(node1, column, 1);
		m.add(node2, column, -1);
		m.add(row, node2, 1);
		m.add(row, node1, -1);
		m.add(row, m.getColumns()-1, this.voltage);
		return 1;
	}
	public int getAdditionalVariables() {
		return 1;
	}
	public int getAdditionalRows() {
		return 1;
	}
}
