package domain.abilities;

import domain.Game;
import domain.Layout;
import domain.Ymir;
import domain.obstacle.Obstacle;

import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class InfiniteVoid implements Runnable{
    private ArrayList<Integer> indexes;
    private ArrayList<Obstacle> obstacles;



    public InfiniteVoid() {

         indexes = new ArrayList<>();
         obstacles = new ArrayList<Obstacle>();
    }


    public void chooseAndFreezeObstacles() {
        ArrayList<Obstacle> layoutObsList = new ArrayList<Obstacle>(Layout.getObstaclePositions().keySet());
        int numberOfObstaclesToChoose = Math.min(8, layoutObsList.size());
        int index;
        for (int i=0; i<numberOfObstaclesToChoose; i++) {
            index = (int)(Math.random() * layoutObsList.size());

            while (indexes.contains(index) || !(Game.getInstance().getDomainObjectArr().get(index) instanceof Obstacle)) {
                index = (int) (Math.random() * layoutObsList.size());
            }

            indexes.add(index);
            obstacles.add((Obstacle) Game.getInstance().getDomainObjectArr().get(index));
            ((Obstacle) Game.getInstance().getDomainObjectArr().get(index)).setFrozen(true);

        }
    }

    public void unfreezeObstacles() {
        int i;
        for (Obstacle obs : obstacles) {
            if (Game.getInstance().getDomainObjectArr().contains(obs)) {
                i = Game.getInstance().getDomainObjectArr().indexOf(obs);
                ((Obstacle) Game.getInstance().getDomainObjectArr().get(i)).setFrozen(false);
            }
        }
    }


    @Override
    public void run() {
        chooseAndFreezeObstacles();

            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            unfreezeObstacles();
        }
    }

