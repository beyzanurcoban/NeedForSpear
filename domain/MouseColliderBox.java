package domain;

import util.PosVector;

public class MouseColliderBox extends DomainObject {
    private int MOUSE_WIDTH = 10;
    private int MOUSE_HEIGHT = 10;


    public MouseColliderBox(PosVector pos){
        this.posVector = pos;
        this.width = MOUSE_WIDTH;
        this.height = MOUSE_HEIGHT;

    }

    @Override
    protected void updatePosition(int x, int y) {

    }
}
