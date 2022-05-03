package domain.path;

import domain.CollisionChecker;
import domain.Layout;
import domain.obstacle.Obstacle;
import domain.obstacle.PandorasBox;
import util.PosVector;

import java.util.HashMap;

public class CircularPath implements IPathBehaviour{

    private double angle;
    private int radius;
    private int centerX,centerY;
    private int currentX, currentY;
    private double angularVelocity;
    private int initialX, initialY;

    public CircularPath(Obstacle obs, double angularVelocity) {

        this.radius = obs.getWidth();
        this.initialX = obs.getPos().getX();
        this.initialY = obs.getPos().getY();
        this.centerX = (initialX + obs.getWidth()) / 2;
        this.centerY = (initialY + obs.getHeight())/ 2 + radius;
        this.angle = 0;
        this.angularVelocity = angularVelocity;
    }

    @Override
    public void initialPath(Obstacle thisObs) {
        HashMap<Obstacle, PosVector> obstacle_positions = Layout.getObstaclePositions();
        CollisionChecker CC = CollisionChecker.getInstance();
        double tempAngle = angle;
        double tempAngularVelocity = angularVelocity * 4;
        boolean isOkay = true;

        while(true){
            for (Obstacle obs: obstacle_positions.keySet()){
                if ( (thisObs.getPos().manhattanDist(obs.getPos())) != 0
                        && CC.checkCollision(thisObs, obs)){
                    isOkay = false;
                    break;
                }
            }
            tempAngle += tempAngularVelocity;
            int currentX = centerX + (int) (radius * Math.cos(tempAngle));
            int currentY = centerY + (int) (radius * Math.sin(tempAngle));
            thisObs.setPosVector(new PosVector(currentX,currentY));

            if (currentX <= 0 ||  currentX >= 1368 ){
                isOkay = false;
            }

            if (currentY <= 60 ||  currentY>=700 ){
                isOkay = false;
            }

            if (isOkay && tempAngle > 360){
                break;
            }

            if(!isOkay){
                thisObs.is_moving = false;
                break;
            }

        }
        // Reset Obstacle
        thisObs.setPosVector(new PosVector(initialX,initialY));
        thisObs.getRemains().setPosVector(new PosVector(initialX,initialY));

    }

    @Override
    public PosVector nextPosition() {
        angle += angularVelocity;
        angle %= 360;

        int currentX = centerX + (int) (radius * Math.cos(angle));
        int currentY = centerY + (int) (radius * Math.sin(angle));

        return new PosVector(currentX, currentY);
    }
}
