package Components;

import circuitAnalysis.Main;
import resources.Sprite;

public class Resistor extends Component{
	private static Sprite sprite;
	private int[] node1 = new int[]{0,0}, node2 = new int[]{5,0};
	public static void setSprite(Sprite sprite){
		Resistor.sprite=sprite;
	}
	private int resistance;
	public Resistor(){
		this(100);
	}
	public Resistor(int resistance) {
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
		return ""+resistance;
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
		int[][] ret={{0,0},{node2[0]*Main.gridSize,node2[1]*Main.gridSize}};
		return ret;
	}

}
