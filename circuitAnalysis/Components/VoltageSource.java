package Components;

import resources.Sprite;

public class VoltageSource extends Component{
	private static Sprite sprite;
	private int[] node1 = new int[]{0,0}, node2 = new int[]{6,0};
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
	Wire one;
	Wire two;
	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return VoltageSource.sprite;
	}
	@Override
	public Component Clone() {
		// TODO Auto-generated method stub
		return new VoltageSource();
	}
}
