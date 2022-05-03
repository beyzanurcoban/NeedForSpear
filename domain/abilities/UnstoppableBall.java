package domain.abilities;

import domain.Game;

import java.util.concurrent.TimeUnit;

public class UnstoppableBall implements Runnable{


    public UnstoppableBall() {}

    public void run() {
        Game.getInstance().getBall().set_is_unstoppable(true);
        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Game.getInstance().getBall().set_is_unstoppable(false);
        Game.getInstance().gameState.getPlayer().setIsMagicalAbilityActive(false);

    }

}


