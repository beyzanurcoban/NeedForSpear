package domain.path;

import domain.CollisionChecker;
import domain.Layout;
import domain.obstacle.Obstacle;
import util.PosVector;

import java.util.HashMap;

public class StraightVerticalBFPath implements IPathBehaviour {
    private int startX, Y, endLeft, endRight;
    private final int END_MOST_DIS_X = 100;
    private int velocity;
    private int currentX;
    private int MIN_PATH_LONG = 50;

    public StraightVerticalBFPath(int startX,
                                  int Y,
                                  int velocity) {
        this.startX = startX;
        this.currentX = startX;
        this.endLeft = startX - END_MOST_DIS_X;
        this.endRight = startX + END_MOST_DIS_X;
        this.Y = Y;
        this.velocity = velocity;
    }

    @Override
    public void initialPath(Obstacle thisObs) {
        // Get game variables for checking collision
        HashMap<Obstacle, PosVector> obstacle_positions = Layout.getObstaclePositions();
        CollisionChecker CC = CollisionChecker.getInstance();
        int tempX = currentX;
        int tempVelocity = velocity;

        // Find the right limit
        while(true){
            for (Obstacle obs: obstacle_positions.keySet()){
                if ( (thisObs.getPos().manhattanDist(obs.getPos())) != 0
                        && CC.checkCollision(thisObs, obs)){
                    endRight = tempX- thisObs.getWidth() / 2;
                    break;
                }
            }
            tempX += tempVelocity/2;
            thisObs.setPosVector(new PosVector(tempX,Y));

            if (tempX >= endRight){
                break;
            }

        }

        // Find the left limit
        tempVelocity *= - 1;
        tempX = startX;
        thisObs.setPosVector(new PosVector(tempX,Y));

        while(true){
            for (Obstacle obs: obstacle_positions.keySet()){
                if ( (thisObs.getPos().manhattanDist(obs.getPos())) != 0
                        && CC.checkCollision(thisObs, obs)){
                    endLeft = tempX + thisObs.getWidth();
                    break;
                }
            }
            tempX += tempVelocity;
            thisObs.setPosVector(new PosVector(tempX,Y));

            if (tempX <= endLeft){
                break;
            }

        }


        // Reset obstacle
        thisObs.setPosVector(new PosVector(startX,Y));

        // Check Boundries
        if( endLeft <= 0 ){
            endLeft = 0;
        }else if (endLeft >= startX) {
            endLeft = startX;
        }

        if(endRight >= 1368 - thisObs.getWidth()){
            endRight = 1368 - thisObs.getWidth();

        }

        // Delete if the path is too small
        if (endRight - endLeft <= MIN_PATH_LONG){
            thisObs.is_moving = false;
        }


    }

    @Override
    public PosVector nextPosition() {
        currentX += velocity;

        if (currentX >= endRight || currentX <= endLeft){
            velocity *= -1;
        }

        return new PosVector(currentX,Y);
    }


    public int getEndLeft() {
        return endLeft;
    }

    public int getEndRight() {
        return endRight;
    }

    public void setEndLeft(int endLeft) {
        this.endLeft = endLeft;
    }

    public void setEndRight(int endRight) {
        this.endRight = endRight;
    }
}
