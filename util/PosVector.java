package util;
import java.beans.beancontext.BeanContext;
import java.lang.Math;

public class PosVector{

	/*
	// The abstraction function is
		// AF(x,y) = {x,y >= 0 |x <= FRAME_WIDTH}

		// The rep invariant is
		// x and y are integers
		// x >= 0 &&
		// y >= 0 &&
		// x <= FRAME_WIDTH
	*/

	public int x;
	public int y;
	private final int FRAME_WIDTH = 1368;
	private final int FRAME_HEIGHT = 766;

	// Constructor
	public PosVector(int x, int y) {
		this.x = x;
		this.y = y;
	}



	// Vector Addition
	public void addVector(PosVector vec) {
		/*
		EFFECTS: Adds another PosVector's components to this
		MODIFIES: this.x, this.y
		 */
		this.setX( this.getX() + vec.getX() );
		this.setY( this.getY() + vec.getY() );
	}

	public void halfVelocity() {
		/*
		EFFECTS: Halves down both the x and y components of the vector
		MODIFIES: this.x, this.y
		 */
		this.setX( this.getX() / 2 );
		this.setY( this.getY() / 2 );
	}

	// To rotate the obstacles, we will need to get the angle between current pos and the center of the circle
	public double getTheta(PosVector center){
		/*
		REQUIRES: center != position
		EFFECTS: calculates the angle between the position of the circle and its center of rotation
		 */
		double dy = center.getY() - this.getY();
		double dx = center.getX() - this.getX();
		double theta = Math.atan(dy/dx);
		return theta;
	}


	public Boolean repOK() {
		/*
		EFFECTS: verifies the accuracy of the invariant
		 */
		if (this.x < 0 || this.x > FRAME_WIDTH) return false;
		if (this.y < 0) return false;
		if (this.x % 1 != 0 || this.y % 1 != 0) return false;

		return true;
	}



	public int getX() {
		/*
		EFFECTS: returns x value
		 */
		return x;
	}

	public void setX(int x) {
		/*
		EFFECTS: changes x value
		MODIFIES: this.x
		 */
		this.x = x;
	}

	public int getY() {
		/*
		EFFECTS: returns y value
		 */
		return y;
	}

	public void setY(int y) {
		/*
		EFFECTS: changes y value
		MODIFIES: this.y
		 */
		this.y = y;
	}

	@Override
	public String toString() {
		return "PosVector{" +
				"x=" + x +
				", y=" + y +
				'}';
	}

	// Manhattan Distance between 2 PosVector's
	public int manhattanDist(PosVector pos){
		return Math.abs(this.x - pos.getX())
				+ Math.abs(this.y - pos.getY());

	}
}
