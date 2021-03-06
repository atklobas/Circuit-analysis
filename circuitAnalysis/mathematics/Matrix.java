package mathematics;

import java.util.Arrays;

/**
 * 
 * @author Anthony Klobas
 * 
 * This class is immutable and may reduce performance if too large
 * all operations (assuming square matrices) have at least a C n^2
 * 
 */
public class Matrix {
	
	
	
	
	
	
	public static Matrix createOrthonormal(Vector v){
		return createOrthoganol(v.getUnitVector());
	}
	public static Matrix createOrthoganol(Vector v){
		Matrix ret=new Matrix(v.getSize());
		int size=v.vector.length;
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				ret.matrix[i][j]=v.vector[(j+i)%size];
			}
		}
		if(size==2){
			ret.matrix[0][0]=v.vector[0];
			ret.matrix[1][0]=v.vector[1];
			ret.matrix[0][1]=-v.vector[1];
			ret.matrix[1][1]=v.vector[0];
		}
		ret.isOrthogonal=true;
		return ret;
		
	}
	//note dimention 1
	private boolean isOrthogonal;
	private double[][] matrix;
	private int rows, columns;
	/*
	 * matrixes are stored as it relates to standard notation m[row][column];
	 * example:
	 * new double[][]
	 * {
	 *  {1,2,3},
	 *  {4,5,6},
	 *  {7,8,9}
	 * }
	 */
	/**
	 * create new identity matrix that is nxn
	 */
	public Matrix(int n){
		matrix=new double[n][n];
		for(int i=0;i<n;i++){
			matrix[i][i]=1;
		}
		this.rows=n;
		this.columns=n;
	}
	public Matrix(int rows, int columns){
		this.matrix=new double[rows][columns];
		this.rows=rows;
		this.columns=columns;
	}
	
	/**
	 * 
	 * @param matrix a prebuild matrix to be copied
	 */
	public Matrix(double [][] matrix){
		this.matrix=new double[matrix.length][matrix[0].length];
		columns=matrix[0].length;
		rows=matrix.length;
		for(int i=0;i<rows;i++){
			System.arraycopy(matrix[i], 0, this.matrix[i], 0, columns);
		}
	}
	/**
	 * 
	 * @param vectors a number of column vectors
	 */
	public Matrix(Vector... vectors){
		columns=vectors.length;
		rows=vectors[0].getSize();
		for(int row=0;row<rows;row++){
			for(int column=0;column<columns;column++){
				matrix[row][column]=vectors[column].vector[row];
			}
		}
		
	}
	public double[][] getMatrix(){
		return this.matrix;
	}
	/**
	 * applies this matrix as a function to a vector and returns the result
	 *  Vector a, matrix M, return aM
	 * 
	 * @param v
	 * @return
	 */
	public Vector apply(Vector v){
		double[] ret=new double[columns];
		
		for(int row=0;row<rows;row++){
			ret[row]=0;//can never be too safe
			
			for(int column=0;column<columns;column++){
				
				ret[row]+=v.vector[column]*matrix[column][row];
			}
		}
		Vector retr=new Vector(ret);
		return retr;
	}
	public Matrix transpose(){
		Matrix ret=new Matrix(this.columns);
		for(int row=0;row<rows;row++){
			for(int column=0;column<columns;column++){
				ret.matrix[row][column]=this.matrix[column][row];
			}
		}
		return ret;
		
		
	}
	/**
	 * returns an inverse of the matrix, only works with orthoganol matracies 
	 * @return an inverse of this matrix
	 */
	@SuppressWarnings("unused")
	public Matrix invert(){
		if(columns!=rows){
			throw new UnsupportedOperationException("inverse of a "+columns+"by"+rows+"matrix is not defined");
		}else if(this.isOrthogonal||true){
			return this.transpose();
		}/**/else if (columns==2){
			double a=matrix[1][1];
			double b=matrix[1][2];
			double c=matrix[2][1];
			double d=matrix[2][2];
			double det=a*d-b*c;
			if(det==0){
				throw new UnsupportedOperationException("non invertable matrix");
			}
			det=1/det;
			return new Matrix(new double[][]{
					{d*det  , (0-b)*det},
					{0-c*det, a*det    }
			});
		}else{
			throw new UnsupportedOperationException("this cannot do non orthoganol or non 2x2 matracies(yet)");
		}
	}
	@SuppressWarnings("unused")
	private void scaleRow(int row, double scalar){
		for(int i=0; i<columns; i++){
			this.matrix[row][i] *= scalar;
			
		}
	}
	@SuppressWarnings("unused")
	private void scaleAndAdd(int from, int to, double scalar){
		for(int i=0; i<columns; i++){
			this.matrix[to][i] += this.matrix[from][i] * scalar;
		}
	}
	@SuppressWarnings("unused")
	private void swapRows(int a, int b){
		double[] row = this.matrix[a];
		this.matrix[a]=this.matrix[b];
		this.matrix[b]=row;
	}
	
	
	public Matrix rref(){
		Matrix m = new Matrix(this.matrix);
		int pivots = Math.min(m.columns, m.rows);
		
		for(int j=0; j<pivots; j++){//for each column
			for(int i=j; i<rows; i++){//finds nonzero in column to be pivot
				if(m.matrix[i][j]!=0){
					m.swapRows(j, i);
					break;
				}
			}
			if(m.matrix[j][j]!=0){//if pivot is non-zero
				if(m.matrix[j][j]!=1 && m.matrix[j][j]!=0){//scales pivot to 1
					m.scaleRow(j, 1.0/m.matrix[j][j]);
				}
				for(int i=j+1; i<rows; i++){//for each row under pivot, eliminate
					if(m.matrix[i][j]!=0){
						m.scaleAndAdd(j, i, -m.matrix[i][j]);
					}
				}
			}
		}
		for(int i=0; i<pivots; i++){//for each row, back-eliminate
			for(int j=i+1; j<pivots; j++){
				if(m.matrix[i][j]!=0){
					if(m.matrix[j][j]==1){
						m.scaleAndAdd(j, i, -m.matrix[i][j]);
					}
				}
			}
		}
		
		return m;
	}
	public Matrix rref2(){
		Matrix m = new Matrix(this.matrix);
		java.util.LinkedList<int[]> pivots=new java.util.LinkedList<int[]>();
		int row=0;
		for(int i=0;i<columns;i++ ){
			if(refWorker(m,row,i)){
				pivots.push(new int[]{row,i});
				row++;
			};
			System.out.println("column="+i);
			System.out.println(m);
			
		}
		//clear floating point error remainders
		
		for(int[] p: pivots){
			backSubstitute(m,p[0],p[1]);
		}
		for(int x=0;x<rows;x++){
			for(int y=0;y<columns;y++){
				//if(Math.abs(m.matrix[x][y])<.0000000001){
					m.matrix[x][y]=Math.round(m.matrix[x][y]*1000000000)/1000000000.;
				//}
			}	
		}
		
		return m;
		
	}
	private void backSubstitute(Matrix m, int row, int column) {
		for(int i=row-1;i>=0;i--){
			m.scaleAndAdd(row, i, -m.matrix[i][column]);
		}
		
	}
	private boolean refWorker(Matrix m,int row, int column){
		if(!findnonzero(m,row,column)){
			return false;
		}
			
		for(int i=row+1;i<rows;i++){
			m.scaleAndAdd(row, i, -m.matrix[i][column]);
		}
		return true;
		
	}
	private boolean findnonzero(Matrix m,int row, int column){
		for(int i=row;i<rows;i++){
			if(m.matrix[i][column]!=0){
				if(Math.abs(m.matrix[i][column])<.0000000000001){
					m.matrix[i][column]=0;
					continue;
				}
				m.swapRows(row, i);
				m.scaleRow(row, 1./m.matrix[row][column]);
				return true;
			}
		}
		return false;
	}
		
	
	/*
	public static void main (String[] args){
		double[][] derp =  new double[][]
				  {
			   {1,1,5},
			   {1,2,6},
			   {2,5,7}
			  };
		Matrix m = new Matrix(derp);
		System.out.println(m+"\n");
		System.out.println(m.rref());
		
	}
	*/
	public String toString(){
		String ret="";
		for(double[] str: this.matrix){
			ret+=Arrays.toString(str)+"\n";
		}
		
		return ret.substring(0,ret.length()-1);
	}
	public void add(int row, int column, double value){
		matrix[row][column]+=value;
	}
	public double getValue(int row, int column){
		return matrix[row][column];
	}
	public int getRows(){
		return this.rows;
	}
	public int getColumns(){
		return this.columns;
	}
	public Matrix subtract(Matrix m){
		Matrix ret=new Matrix(rows,columns);
			for(int x=0;x<rows;x++){
				for(int y=0;y<columns;y++){
					ret.matrix[x][y]=matrix[x][y]-m.matrix[x][y];
				}	
			}
		
		return ret;
		
	}
	

}
