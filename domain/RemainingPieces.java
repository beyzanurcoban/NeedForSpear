package domain;

import util.PosVector;

public class RemainingPieces extends DomainObject {

    private int yVelocity = 10;
    private PosVector posVector;
    protected int height = 30;
    protected int width = 30;

    public RemainingPieces(double x, double y) {
        this.posVector = new PosVector((int) x, (int) y);
        this.setSpeed(0, 0);

    }

    public PosVector getPosVector() { return this.posVector; }

    public int getHeight() { return this.height; }

    public int getWidth() { return this.width; }

    public void setPosVector(PosVector pos) {
        this.posVector = pos;
    }

    @Override
    public void updatePosition() {
        this.posVector.setX(this.posVector.getX());
        this.posVector.setY(this.posVector.getY() + this.yVelocity);
    }
    public void updatePosition(int x, int y) {
        this.posVector.setX(x);
        this.posVector.setY(y);

    }

    @Override
    public String toString() {
        return "RemainingPieces{" +
                "posVector=" + posVector +
                ", dx=" + dx +
                ", dy=" + dy +
                ", isWidthHeightTaken=" + isWidthHeightTaken +
                ", yVelocity=" + yVelocity +
                ", posVector=" + posVector +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}