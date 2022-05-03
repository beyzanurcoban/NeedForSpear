package domain;

import util.PosVector;

import java.util.ArrayList;
import java.util.List;

public class Ball extends DomainObject {
    private final int FRAME_WIDTH = 1368;
    private final int FRAME_HEIGHT = 766;
    public double gravity;
    public int yVelocity, xVelocity;
    private int width = 20;
    private int height = 20;
    private boolean is_unstoppable;
    private List<IGameListener> gameListeners = new ArrayList<>();
    private boolean isAlive;
    private static int yOffset = 70;
    private static int xOffset = 175;
    private final int MAX_BALL_VEL = 45;

    private boolean outOfScreen;


    // Overloaded constructors
    public Ball() {
        this.posVector = new PosVector(600, 600);
        this.gravity = 1;
        this.xVelocity = 3;
        this.yVelocity = -40;
        this.is_unstoppable = false;
        this.isAlive = false;

    }

    public Ball(int xVel, int yVel) {
        this.posVector = new PosVector(100, 100);
        this.gravity = 10;
        this.xVelocity = xVel;
        this.yVelocity = yVel;
        this.is_unstoppable = false;
        this.outOfScreen = false;
    }

    public void updateVelocity() {
        this.yVelocity += this.gravity;
        this.yVelocity = Math.min(((int) (this.yVelocity + this.gravity)), MAX_BALL_VEL);
    }

    public void updatePosition(int x, int y) {
        this.posVector.setX(x);
        this.posVector.setY(y);

    }

    @Override
    public void updatePosition() {
        this.posVector.setX(this.posVector.getX() + this.xVelocity);
        this.posVector.setY(this.posVector.getY() + this.yVelocity);
    }

    // When the ball reflects from a vertical surface, it reverses its velocity in x-direction.
    // When a collision of this type is detected, the handler will command the ball domain.controller to reverse the x-velocity.
    public void reflectFromVertical() {
        if (!this.is_unstoppable) {
            this.xVelocity *= -1;
        }
    }

    // Used when the obstacle is frozen and ball is unstoppable
    public void forceReflectFromVertical() {
            this.xVelocity *= -1;
    }

    // When the ball reflects from a horizontal surface, it reverses its velocity in y-direction.
    // When a collision of this type is detected, the handler will command the ball domain.controller to reverse the y-velocity.
    public void reflectFromHorizontal() {
        if (!this.is_unstoppable) {
            this.yVelocity *= -1.05;
        }
    }

    // Used when the obstacle is frozen and ball is unstoppable
    public void forceReflectFromHorizontal() {
            this.yVelocity *= -1.1;
    }

    public void reflectFromAngle(double alpha) {
        alpha *= -1;
        double new_xVel = Math.cos(alpha) * this.xVelocity - Math.sin(alpha) * this.yVelocity;
        double new_yVel = Math.sin(alpha) * this.xVelocity + Math.cos(alpha) * this.yVelocity;
        this.xVelocity = (int) new_xVel;
        this.yVelocity = (int) (new_yVel * -1.1);
    }


    public void reflectFromPaddle() {
        this.yVelocity *= -1.25;
    }

    public void reflectFromTopWall() {
        this.yVelocity *= -1.10;
    }

    public void reflectFromSideWall() {
        this.xVelocity *= -1;
    }

    public void checkWallCollision() {
        if (this.posVector.getX() < 8) {
            this.reflectFromSideWall();
        } else if (this.posVector.getX() > (FRAME_WIDTH  - 20)) {
            this.reflectFromSideWall();
        }
        else if (this.posVector.getY()<yOffset) {
            this.reflectFromTopWall();
        }
    }

    public Boolean checkAlive() {
        return (this.posVector.getY() <= (FRAME_HEIGHT));
    }

    public Boolean move() {
        // EFFECTS: By looking at the boundary conditions of the setup, it updates the velocity and position of the ball.
        // MODIFIES: the velocity and position of the vall
        if (this.checkAlive() != true) {
            return false;
        }
        if(!this.isAlive){
            return false;
        }
        this.checkWallCollision();
        // check paddle collision here
        this.updateVelocity();
        this.updatePosition();

        return true;

    }

    public void setisAlive(Boolean b){
        this.isAlive = b;
    }

    public void setOutOfScreen(Boolean b){
        this.outOfScreen = b;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setPosVector(PosVector pos) {
        this.posVector = pos;
    }

    public void setyVelocity(Integer a) {
        this.yVelocity = a;
    }

    public void setXVelocity(Integer a) {
        this.xVelocity = a;
    }

    public void setGravity(Integer a) {
        this.gravity = a;
    }

    public void set_is_unstoppable(boolean is_unstoppable) {
        this.is_unstoppable = is_unstoppable;
    }

    public boolean is_unstoppable() {
        return this.is_unstoppable;}
}
