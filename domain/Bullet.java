package domain;

import util.PosVector;

public class Bullet extends DomainObject {

    public int yVelocity =9;
    private int bulletsize =20;
    protected boolean outOfScreen;

    public Bullet(int x, int y){
        this.posVector = new PosVector(x, y);
        this.outOfScreen = false;
        this.width = bulletsize;
        this.height = bulletsize;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight(){
        return this.width;
    }

    @Override
    public void updatePosition(int x, int y) {
        this.posVector.setX(x);
        this.posVector.setY(y);
    }
    public PosVector getPosVector() { return this.posVector; }

    public void setPosVector(PosVector pos) {
        this.posVector = pos;
    }
    @Override
    public void updatePosition() {
        this.posVector.setX(this.posVector.getX());
        this.posVector.setY(this.posVector.getY() - this.yVelocity);
    }

    public void setOutOfScreen(Boolean b){
        this.outOfScreen = b;
    }
}
