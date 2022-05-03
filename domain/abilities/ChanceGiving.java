package domain.abilities;

import domain.Game;

public class ChanceGiving {

    public ChanceGiving() {}

    public void activate() {
        int currentChance =  Game.getInstance().gameState.getPlayer().getChance_points();
        Game.getInstance().gameState.getPlayer().setChance_points(currentChance+1);
    }
}
