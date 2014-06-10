package Components;

import mathematics.Matrix;
import circuitAnalysis.Main;
import resources.Sprite;

public class Resistor extends Component{
	private static Sprite sprite;
	private int[] node1 = new int[]{0,0}, node2 = new int[]{5,0};
	Wire[] wires=new Wire[2];
	public static void setSprite(Sprite sprite){
		Resistor.sprite=sprite;
	}
	private double resistance;
	public Resistor(){
		this(100);
	}
	public Resistor(double resistance) {
		this.resistance=resistance;
	}

	@Override
	public Sprite getSprite() {
		return Resistor.sprite;
	}
	
	public Resistor Clone(){
		return new Resistor(resistance);
	}
	public String toString(){
		//int node1=this.wires[0].getNode().getID();
		//int node2=this.wires[1].getNode().getID();
		return node1+"->"+resistance+" ohms ->"+node2;
	}
	private void setNode2(){
		if(this.getAngle()==0){
			node2 = new int[]{5,0};
		}else if(this.getAngle()==90){
			node2 = new int[]{0,5};
		}else if(this.getAngle()==180){
			node2 = new int[]{-5,0};
		}else if(this.getAngle()==270){
			node2 = new int[]{0,-5};
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
	@Override
	public int[][] getConnectionLocations() {
		this.wires=new Wire[2];
		int[][] ret={{0,0},{node2[0]*Main.gridSize,node2[1]*Main.gridSize}};
		return ret;
	}
	@Override
	public void addWire(int con, Wire w) {
		wires[con]=w;
		
	}
	@Override
	public int addEquations(Matrix m, int row, int column) {
		int node1=this.wires[0].getNode().getID();
		int node2=this.wires[1].getNode().getID();
		//System.err.println("#"+node1+","+node2);
		m.add(node1, node1, 1./this.resistance);
		m.add(node1, node2, -1./this.resistance);
		m.add(node2, node1, -1./this.resistance);
		m.add(node2, node2, 1./this.resistance);
		return 1;
	}
	public void setResistance(double r){
		this.resistance=r;
	}

}
