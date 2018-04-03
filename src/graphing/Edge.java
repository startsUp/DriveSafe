package graphing;


public class Edge {

	private int i;
	private int w;

	public Edge(int i, int w) {
		this.i = i;
		this.w = i;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.i + " " + this.w;
	}

}
