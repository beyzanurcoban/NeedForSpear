package domain.abilities;

import domain.Game;

import java.util.concurrent.TimeUnit;

public class DoubleAccel implements Runnable{

    public DoubleAccel() { }

    public void run() {
        Game.getInstance().getBall().xVelocity /= 2;
        int yvel = Game.getInstance().getBall().yVelocity;
        Game.getInstance().getBall().yVelocity /= 2;
        try {
            TimeUnit.SECONDS.sleep(15);
            Game.getInstance().getBall().xVelocity *= 2;
            Game.getInstance().getBall().yVelocity = yvel;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
