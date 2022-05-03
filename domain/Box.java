package domain;

import util.PosVector;
import java.util.Random;

public class Box extends DomainObject {

    /////////////////////////////////////////////////////////////////////////////////////
    /// This is the artifact dropped from the gift obstacle and can be collected by the player
    ////////////////////////////////////////////////////////////////////////////////////
    private int yVelocity = 10;
    private int abilityType;
    private PosVector posVector;
    protected int height = 30;
    protected int width = 30;
    private boolean isBoxCatched =true;


    /////////////////////////////////////////////////////////////////////////////////////

    public Box(double x, double y) {
        Random rnd = new Random();
        this.posVector = new PosVector((int)x,(int)y);
        this.setSpeed(0, 0);
        this.abilityType = rnd.nextInt(4) + 1;

    }

    //update position.
    @Override
    public void updatePosition() {
        this.posVector.setX(this.posVector.getX());
        this.posVector.setY(this.posVector.getY() + this.yVelocity);
    }
    public void updatePosition(int x, int y) {
        this.posVector.setX(x);
        this.posVector.setY(y);

    }
    public PosVector getPosVector() { return this.posVector; }

    public int getHeight() { return this.height; }

    public int getWidth() { return this.width; }

    public void setPosVector(PosVector pos) {
        this.posVector = pos;
    }


    // MODIFIES: The current ability of the box.
		/* EFFECTS: by checking at the box cough of the setup, it updates the currentAbility.
		*/
    public void updateAbility(){
        //update the current ability if the box is cough by player(paddle), otherwise false.
        if(isBoxCatched){
            int currentAbilityNum = Game.getInstance().gameState.getPlayer().getAbilities().get(abilityType);
            currentAbilityNum = currentAbilityNum+1;
            Game.getInstance().gameState.getPlayer().getAbilities().put(abilityType,currentAbilityNum);
            isBoxCatched =false;
        }


    }

}