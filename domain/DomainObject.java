package domain;

import util.PosVector;

public abstract class DomainObject {

    protected PosVector posVector;
    protected int dx;
    protected int dy;
    protected boolean isWidthHeightTaken = false;
    protected int width;
    protected int height;
    protected double angle;

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void updateAngle() {
        this.angle += Math.toRadians(1);
    }

    protected abstract void updatePosition(int x, int y);
    public void updatePosition() {

    }

    public PosVector getPosVector() {
        return posVector;
    }

    public void setLoc(PosVector pos) {
        this.posVector = pos;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setSpeed(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public String toString() {
        return String.format("Domain Object with Loc: %s%n", this.getPosVector().toString());
    }

    public boolean isWidthHeightTaken() {
        return isWidthHeightTaken;
    }

    public void setWidthHeight(int width, int height) {
        if (!this.isWidthHeightTaken()) {
            this.setWidth(width);
            this.setHeight(height);
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPosVector(PosVector posVector) {
        this.posVector = posVector;
    }
}
