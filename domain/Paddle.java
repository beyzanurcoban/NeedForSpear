package domain;

import util.PosVector;

public class Paddle extends DomainObject{

	/////////////////////////////////////////////////////////////////////////////////////

	public static final int FRAME_WIDTH = 1368;
	public static final int FRAME_HEIGHT = 766;
	public static final int PADDLE_THICKNESS = 20;

	// angle constants
	private double MIN_ANGLE_LIMIT = -50;
	private double MAX_ANGLE_LIMIT = 50;

	// move constants
	private int GO_LEFT_DIC = 1;
	private int GO_RIGHT_DIC = 2;
	private int ROTATE_CCLOKC_DIC = 3;
	private int ROTATE_CLOCK_DIC = 4;
	private int GO_FAST_LEFT_DIC = 5;
	private int GO_FAST_RIGHTH_DIC = 6;

	protected int length;
	private int thickness;
	private double angle;
	private double angleSpeed;
	private int normalSpeed;
	private int fastSpeed;
	private int width;
	private int cannonofSetLeft =37;
	private int cannonofSetRight =12;
	private int height = PADDLE_THICKNESS;
	private boolean hasCannon =false;
	protected Cannon leftCannon;
	protected Cannon rightCannon;

	/////////////////////////////////////////////////////////////////////////////////////

	public Paddle(int fWidth, int fHeight) {
		this.length = fWidth/10;
		this.width = this.length;
		this.thickness = PADDLE_THICKNESS;
		this.posVector = new PosVector((fWidth - length)/2, fHeight - (this.thickness * 4));
		this.angle = 0;
		this.angleSpeed = 10;
		normalSpeed = length/2;
		fastSpeed = 2*length;
		this.setSpeed(0,0);
	}

	public int getLength() { return this.length; }

	public int getThickness() { return this.thickness; }

	public double getAngle() { return this.angle; }

	public void setAngle(double angle) { this.angle = angle; }

	public double getAngleSpeed() { return this.angleSpeed; }

	public void setAngleSpeed(double angleSpeed) {this.angleSpeed = angleSpeed; }

	public PosVector getPosVector() { return this.posVector; }

	public void setPosVector(PosVector pos) {
		this.posVector = pos;
	}

	public int getNormalSpeed(){ return this.normalSpeed; }
	public int getFastSpeed() { return this.fastSpeed; }

	// Used for Paddle Expansion ability
	public void expandPaddle() {
		this.length *= 2;
		this.width *= 2;
	}

	// Normalize paddle after ability
	public void normalizePaddle() {
		this.length /=2;
		this.width *=2;
	}



	public void move(int direction) {
		// MODIFIES: The position and the velocity of the paddle.
		/* EFFECTS: by checking at the boundary conditions of the setup, it updates the position and velocity
		of the paddle.
		*/

		// Paddle goes left
		if (direction == GO_LEFT_DIC) { goLeft(); }
		// Paddle goes right
		else if (direction == GO_RIGHT_DIC) { goRight(); }
		// Paddle rotates counterclockwise (A)
		else if (direction == ROTATE_CCLOKC_DIC) { rotateCClockwise(); }
		// Paddle rotates clockwise (D)
		else if (direction == ROTATE_CLOCK_DIC) { rotateClockwise(); }
		// Paddle goes faster left
		else if (direction == GO_FAST_LEFT_DIC) { goFastLeft(); }
		// Paddle goes faster right
		else if (direction == GO_FAST_RIGHTH_DIC) { goFastRight(); }

	}


	//update the positions
	public void updatePosition(int  x, int  y) {
		this.posVector.setX(posVector.getX() + x);
		this.posVector.setY(posVector.getY() + y);
	}

	//make paddle go left
	private void goLeft(){
		setSpeed(-normalSpeed, 0);
		if ((getPosVector().getX() >= normalSpeed) && (getPosVector().getX()  <= FRAME_WIDTH - getLength())) {
			updatePosition(getDx(), getDy());
		} else {
			updatePosition(0, getDy());
		}
	}

	//make paddle go right
	private void goRight(){
		setSpeed(normalSpeed, 0);
		if ((getPosVector().getX() <= FRAME_WIDTH - getLength() - normalSpeed) && (getPosVector().getX() >= 0)) {
			updatePosition(getDx(), getDy());
		} else {
			updatePosition(0, getDy());
		}
	}

	//rotate paddle counterclockwise between | -50 --- 50 |
	private void rotateCClockwise(){
		// | -50 --- 50 |

		if (angle > MIN_ANGLE_LIMIT + angleSpeed){
			setAngle(angle - angleSpeed);
		}else{
			setAngle(MIN_ANGLE_LIMIT);
		}


	}
	//rotate paddle clockwise between | -50 --- 50 |
	private void rotateClockwise(){
		// | -50 --- 50 |

		if (angle < MAX_ANGLE_LIMIT - angleSpeed){
			setAngle(angle + angleSpeed);
		}else{
			setAngle(MAX_ANGLE_LIMIT);
		}



	}

	//make paddle go left with fast speed
	private void goFastLeft(){
		setSpeed(-fastSpeed, 0);
		if ((getPosVector().getX()  >= fastSpeed) && (getPosVector().getX() <= FRAME_WIDTH - getLength())) {
			updatePosition(getDx(), getDy());
		} else {
			updatePosition(0, getDy());
		}
	}

	//make paddle go right with fast speed
	private void goFastRight(){
		setSpeed(fastSpeed, 0);
		if ((getPosVector().getX()  <= FRAME_WIDTH - getLength() - fastSpeed) && (getPosVector().getX()  >= 0)) {
			updatePosition(getDx(), getDy());
		} else {
			updatePosition(0, getDy());
		}
	}

	public boolean getHasCannon(){
		return this.hasCannon;
	}

	public void setHasCannon(boolean b){
		this.hasCannon =b;
	}

	public void setCannons(Cannon c, Cannon cc){
		this.leftCannon =c;
		this.rightCannon =cc;
	}

	public Cannon getLeftCannon(){
		return this.leftCannon;

	}

	public Cannon getRightCannon(){
		return this.rightCannon;

	}

	public double getCenterX() {
		return this.getPosVector().getX() + this.length/2;
	}

	//modify PosVector left corner position
	public PosVector getLeftCornerPos() {
		int x_coordinate = (int) (this.getCenterX() - (this.length/2 * Math.cos((Math.toRadians(this.angle)))));
		int y_coordinate = (int) (this.getPosVector().getY() - (this.length/2 * Math.sin(Math.toRadians(this.angle))));
		return new PosVector(x_coordinate - cannonofSetLeft, y_coordinate);
	}
	//modify PosVector right corner position
	public PosVector getRightCornerPos() {
		int x_coordinate = (int) (this.getCenterX() + (this.length/2 * Math.cos((Math.toRadians(this.angle)))));
		int y_coordinate = (int) (this.getPosVector().getY() + (this.length/2 * Math.sin(Math.toRadians(this.angle))));
		return new PosVector(x_coordinate-cannonofSetRight , y_coordinate);
	}


	// Needed for collision check calculations
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
}