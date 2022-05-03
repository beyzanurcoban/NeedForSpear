package domain;

import util.PosVector;

public class Cannon extends DomainObject{

    public static final int FRAME_WIDTH = 1368;
    public static final int FRAME_HEIGHT = 766;
    private double MIN_ANGLE_LIMIT = -50;
    private double MAX_ANGLE_LIMIT = 50;
    public static final int PADDLE_THICKNESS = 30;


    protected int length;
    private int thickness;
    private double angle;
    private double angleSpeed;
    private int normalSpeed;
    private int fastSpeed;
    private int width;
    private int height = 10;

    protected Bullet bullet;

    public Cannon(int X, int Y){
        this.length = 50;
        this.width = this.length;
        this.thickness = PADDLE_THICKNESS;
        this.posVector = new PosVector(X, Y);
        this.setSpeed(0,0);
        this.bullet = new Bullet(X,Y);
    }

    public Bullet getBullet(){
        return this.bullet;
    }
    public PosVector getPosVector() { return this.posVector; }

    public void setPosVector(PosVector pos) {
        this.posVector = pos;
    }

    public int getNormalSpeed(){ return this.normalSpeed; }
    public int getFastSpeed() { return this.fastSpeed; }


    public void updatePosition(int  x, int  y) {
        this.posVector.setX(posVector.getX() + x);
        this.posVector.setY(posVector.getY() + y);
    }

    public int getLength() { return this.length; }

}
