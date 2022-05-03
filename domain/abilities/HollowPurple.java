package domain.abilities;
import domain.Game;
import domain.Layout;
import domain.obstacle.Hollow;
import domain.obstacle.ObstacleFactory;
import util.PosVector;

import java.util.Random;

public class HollowPurple {


    private int SEED_NUMBER = 14;
    private static int yOffset = 70;
    private static int xOffset = 175;
    private static int layoutHeightOffset = 350;
    private static final int layoutWidth = 1368;
    private static final int layoutHeight = 766;
    private int obsLen = layoutWidth/50;

    public  HollowPurple() {}

    public void initializeHollow() {
        Random rnd = new Random(SEED_NUMBER);

        for (int i=0; i<=7; i++){

            while (true) {
                PosVector pos = new PosVector(
                        rnd.nextInt(layoutWidth - obsLen - xOffset - 2),
                        rnd.nextInt(layoutHeight - layoutHeightOffset - yOffset) + yOffset);
                Hollow h = (Hollow) ObstacleFactory.getInstance().getObstacle(4, pos);

                if (Layout.isAvailable(h)) {
                    Layout.getObstaclePositions().put(h, pos);
                    Game.getInstance().getDomainObjectArr().add(h);
                    System.out.println("Hollow "+ i+ " "+ h.getPosVector().getX()+ " "+ h.getPosVector().getY());
                    break;
                }
            }
        }
    }
}
