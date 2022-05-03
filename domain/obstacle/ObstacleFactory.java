package domain.obstacle;

import util.PosVector;

public class ObstacleFactory {

    private static ObstacleFactory instance;

    private ObstacleFactory(){}

    public static ObstacleFactory getInstance(){
        if (instance == null){
            instance = new ObstacleFactory();
        }
        return instance;
    }


    public Obstacle getObstacle(int type, PosVector pos){
        Obstacle obs = null;
        switch(type) {
            case 0:
                obs = new WallMaria(pos.getX(),pos.getY());
                break;
            case 1:
                obs = new SteinsGate(pos.getX(),pos.getY());
                break;
            case 2:
                obs = new PandorasBox(pos.getX(),pos.getY());
                break;
            case 3:
                obs = new GiftOfUranus(pos.getX(),pos.getY());
                break;
            case 4:
                obs = new Hollow(pos.getX(),pos.getY());
                break;
            default:
        }

        return obs;


    }


}
