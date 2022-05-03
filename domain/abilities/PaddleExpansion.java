package domain.abilities;

import domain.Game;

import java.util.concurrent.TimeUnit;

public class PaddleExpansion implements Runnable{

    public PaddleExpansion() {}

    public void run() {
        Game.getInstance().getPaddle().expandPaddle();
        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Game.getInstance().getPaddle().normalizePaddle();
        Game.getInstance().gameState.getPlayer().setIsMagicalAbilityActive(false);
    }
}
