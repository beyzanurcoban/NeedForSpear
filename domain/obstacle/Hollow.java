package domain.obstacle;

import domain.strategy.DestroyBehaviour;
import domain.strategy.RegularDestroyBehaviour;

import java.util.ArrayList;

public class Hollow extends Obstacle {

    public Hollow(int xPos, int yPos) {
        super(xPos, yPos);
        this.destroyBehaviour = (DestroyBehaviour) new RegularDestroyBehaviour();
        this.type = "HollowPurple";
        this.isEffectScore = false;
    }


    @Override
    protected void updatePosition(int x, int y) {

    }

    @Override
    public String toString() {
        return super.toString() + ", typeVariable=" + getType();
    }
}


