package domain;

import domain.obstacle.Obstacle;
import domain.obstacle.WallMaria;
import util.PosVector;

import java.util.ArrayList;
import java.util.Arrays;

public class CollisionChecker {
    static CollisionChecker instance;
    private ArrayList<IBoxListener> BoxListeners = new ArrayList<>();
    private ArrayList<IRemainsListener> RemainListeners = new ArrayList<>();
    private  ArrayList<Box> boxes = new ArrayList<>();
    private  ArrayList<RemainingPieces> remains = new ArrayList<>();
    boolean bulletsHit;


    private CollisionChecker(){

    }

    public static CollisionChecker getInstance() {
        if (instance == null) {
            instance = new CollisionChecker();
        }
        return instance;
    }

    // Method used to check collision between the ball and the paddle
    public Boolean checkPaddleBallCollision(Ball ball, Paddle paddle) {
        int ball_x = ball.getPosVector().getX();
        int paddle_x = paddle.getPosVector().getX();
        int paddle_y = paddle.getPosVector().getY();
        int paddle_length = paddle.getLength();
        int paddle_right = paddle_x + paddle_length;


        if (ball.getPosVector().getY() > (paddle_y -37)) {
            if ((ball_x > paddle_x) && (ball_x < paddle_right)) {
                return true;
            }
        }
        return false;
    }

    // This is the new paddle ball collision method that takes the rotation of the paddle into account.
    // It applies a linear transformation and measures dy, the distance between the ball and the closest point of
    // the paddle.
    public Boolean checkPaddleBallCollisionAlt(Ball ball, Paddle paddle) {
        int ball_x = ball.getPosVector().getX();
        int ball_y = ball.getPosVector().getY();
        int paddle_x = paddle.getPosVector().getX();
        int paddle_y = paddle.getPosVector().getY() - 37;
        int paddle_length = paddle.getLength();
        int gx = paddle_x + paddle_length/2;
        int gy = paddle_y;
        double alpha = paddle.getAngle();
        alpha = Math.toRadians(alpha);
        int paddle_right = (int) (paddle_x + (paddle_length * Math.abs(Math.cos(alpha) ) ));
        int paddle_left = (int) (paddle_x - (paddle_length * Math.abs(Math.cos(alpha) ) ));


        if ((ball_x > paddle_left) && (ball_x < paddle_right)) {
            int dy = ball_y - gy;
            int dx = ball_x - gx;
            double tranformed_dy = Math.cos(alpha) * dy - Math.sin(alpha) * dx;
            double tranformed_dx = Math.sin(alpha) * dy + Math.cos(alpha) * dx;

            if (tranformed_dy >= 0) {
                return true;
            }

        }

        return false;
    }


    // Collision checker for domain objects
    // Mostly used for obstacle-ball collisions
    public Boolean checkCollision(DomainObject object1, DomainObject object2){
        try {
            int object1_x = object1.getPosVector().getX();
            int object1_y = object1.getPosVector().getY();
            int object1_width = object1.getWidth();
            int object1_height = object1.getHeight();

            int object2_x = object2.getPosVector().getX();
            int object2_y = object2.getPosVector().getY();
            int object2_width = object2.getWidth();
            int object2_height = object2.getHeight();

            return (object1_y < object2_y + object2_height) &&
                    (object2_y < object1_y + object1_height) &&
                    (object1_x < object2_x + object2_width) &&
                    (object2_x < object1_x + object1_width);

        }
        catch (NullPointerException e) {
            System.out.println("Null object in checkCollision");
        }

        return null;

    }


    // Returns true if collision is vertical, false otherwise
    public Boolean findCollisionDirection(DomainObject object1, DomainObject object2) {
        int object1_x = object1.getPosVector().getX();
        int object1_y = object1.getPosVector().getY();
        int object1_width = object1.getWidth();
        int object1_height = object1.getHeight();
        int obj1_left = object1_x;
        int obj1_right = object1_x + object1_width;
        int obj1_top = object1_y;
        int obj1_bottom = object1_y + object1_height;

        int object2_x = object2.getPosVector().getX();
        int object2_y = object2.getPosVector().getY();
        int object2_width = object2.getWidth();
        int object2_height = object2.getHeight();
        int obj2_left = object2_x;
        int obj2_right = object2_x + object2_width;
        int obj2_top = object2_y;
        int obj2_bottom = object2_y + object2_height;

        int[] intersections = new int[4];
        intersections[0] = Math.abs(obj1_right - obj2_left);
        intersections[1] = Math.abs(obj1_left - obj2_right);
        intersections[2] = Math.abs(obj1_top - obj2_bottom);
        intersections[3] = Math.abs(obj1_bottom - obj2_top);
        int min_pos = Arrays.stream(intersections).min().getAsInt();

        return (min_pos <= 1);





    }


    public void ChecktoDelete() {
        /*
            EFFECTS: if game instance has boxes or remaining pieces or obstacles, this checks collisions,
                     deletes or adds corresponding object.

                     if ball hits an obstacle, evokes destroy behavior, is reflected accordingly and
                     obstacle is deleted from Game.DomainObjectArr, Layout.obstacle_positions.
                     If object is instance of GiftOfUranus, a box is dropped which is added to
                     Game.DomainObjectArr. If  object is instance of PandorasBox, a RemainingPieces
                     is dropped which is added to Game.DomainObjectArr.


            MODIFIES: Game, Player, Ball, Game.DomainObjectArr, Layout.obstacle_positions,
                      Box, Obstacle, Remaining Pieces, this(CollisionChecker)

         */
        Obstacle toBeDeleted = null;
        Box boxToBeDeleted = null;
        RemainingPieces remainsToBeDeleted = null;
        Game.getInstance().getPaddle().updatePosition(0, 0);
        Game.getInstance().getBall().move();

        if(Game.getInstance().getPaddle().getHasCannon()){

            updateCannonAndBullets();
            if(Game.getInstance().getPaddle().getLeftCannon().getBullet().getPosVector().getY()<=70){
                Game.getInstance().getPaddle().getRightCannon().getBullet().setOutOfScreen(true);
                Game.getInstance().getPaddle().getLeftCannon().getBullet().setOutOfScreen(true);
            }
            if(Game.getInstance().getPaddle().getLeftCannon().getBullet().outOfScreen){
                bringBackBullets();
            }
        }
        if (boxes != null) {
            for (Box box : boxes) {
                box.updatePosition();
                if(checkCollision(box,Game.getInstance().getPaddle())){
                    box.updateAbility();
                    Game.getInstance().getDomainObjectArr().remove(box);
                    boxToBeDeleted = box;
                }

            }
        }
        if (remains != null) {
            for (RemainingPieces r : remains) {
                r.updatePosition();
                if(checkCollision(r,Game.getInstance().getPaddle())){
                    int chance = Game.getInstance().gameState.getPlayer().getChance_points();
                    Game.getInstance().gameState.setChance(chance-1);
                    Game.getInstance().getDomainObjectArr().remove(r);
                    remainsToBeDeleted = r;
                }

            }
        }
        if (instance.checkPaddleBallCollisionAlt(Game.getInstance().getBall(), Game.getInstance().getPaddle())) {
            double alpha = Math.toRadians(Game.getInstance().getPaddle().getAngle());
            Game.getInstance().getBall().reflectFromAngle(alpha);
        }

        for (Obstacle obs : Layout.getObstaclePositions().keySet()) {
            bulletsHit = false;
            if(obs.is_moving)
                obs.move();
            if (Game.getInstance().getPaddle().getHasCannon()) {
                if (checkCollision(Game.getInstance().getPaddle().getLeftCannon().getBullet(), obs)) {
                    bulletsHit = true;
                    Game.getInstance().getPaddle().getLeftCannon().getBullet().updatePosition(Game.getInstance().getPaddle().getLeftCannon().getPosVector().getX(),
                            Game.getInstance().getPaddle().getLeftCannon().getPosVector().getY());
                }
                else if (checkCollision(Game.getInstance().getPaddle().getRightCannon().getBullet(), obs)) {
                    bulletsHit = true;
                    Game.getInstance().getPaddle().getRightCannon().getBullet().updatePosition(Game.getInstance().getPaddle().getRightCannon().getPosVector().getX(),
                            Game.getInstance().getPaddle().getRightCannon().getPosVector().getY());
                }
            }

            if (instance.checkCollision(Game.getInstance().getBall(), obs) || bulletsHit) {

                // if obs is not frozen
                if (!obs.isFrozen()) {
                    if (obs.getHit()) {
                        String typeCheck = obs.getType();

                        if (typeCheck.equals("GiftOfUranus")) {
                            Game.getInstance().getDomainObjectArr().add(obs.getBox());
                            boxes.add(obs.getBox());
                            for (IBoxListener listener : BoxListeners) {
                                listener.dropBox(obs.getPosVector().getX(), obs.getPosVector().getY());
                            }
                        }
                        if (typeCheck.equals("PandorasBox")) {
                            Game.getInstance().getDomainObjectArr().add(obs.getRemains());
                            remains.add(obs.getRemains());
                            for (IRemainsListener listener : RemainListeners) {
                                listener.dropRemains(obs.getPosVector().getX(), obs.getPosVector().getY());
                            }
                        }
                        Game.getInstance().getDomainObjectArr().remove(obs);
                        toBeDeleted = obs;

                        if (instance.findCollisionDirection(Game.getInstance().getBall(), obs)) {
                            Game.getInstance().getBall().reflectFromVertical();
                        } else {
                            Game.getInstance().getBall().reflectFromHorizontal();
                        }
                    }
                } // if obstacle is frozen
                else {
                    if (obs.getHitWhenFrozen(Game.getInstance().getBall().is_unstoppable())) {
                        String typeCheck = obs.getType();
                        if (typeCheck.equals("GiftOfUranus")) {
                            Game.getInstance().getDomainObjectArr().add(obs.getBox());
                            boxes.add(obs.getBox());
                            for (IBoxListener listener : BoxListeners) {
                                listener.dropBox(obs.getPosVector().getX(), obs.getPosVector().getY());
                            }
                        }
                        if (typeCheck.equals("PandorasBox")) {
                            Game.getInstance().getDomainObjectArr().add(obs.getRemains());
                            remains.add(obs.getRemains());
                            for (IRemainsListener listener : RemainListeners) {
                                listener.dropRemains(obs.getPosVector().getX(), obs.getPosVector().getY());
                            }
                        }
                        Game.getInstance().getDomainObjectArr().remove(obs);
                        toBeDeleted = obs;
                    }

                    if (instance.findCollisionDirection(Game.getInstance().getBall(), obs)) {
                        Game.getInstance().getBall().forceReflectFromVertical();
                    } else {
                        Game.getInstance().getBall().forceReflectFromHorizontal();
                    }
                }

            }
        }
        if (toBeDeleted != null) Layout.getObstaclePositions().remove(toBeDeleted);
        if (boxToBeDeleted != null) {
            boxes.remove(boxToBeDeleted);
            Game.getInstance().getDomainObjectArr().remove(boxToBeDeleted);
        }
        if (remainsToBeDeleted != null) {
            remains.remove(remainsToBeDeleted);
            Game.getInstance().getDomainObjectArr().remove(remainsToBeDeleted);
        }

    }



    public void addListener( IBoxListener listener) {
        BoxListeners.add(listener);
    }

    public ArrayList<Box> getBoxes() {
        return this.boxes;
    }

    public ArrayList<RemainingPieces> getRemainingPieces() {
        return this.remains;
    }

    private void updateCannonAndBullets(){
        Game.getInstance().getPaddle().getLeftCannon().updatePosition(0,0);
        Game.getInstance().getPaddle().getRightCannon().updatePosition(0,0);
        Game.getInstance().getPaddle().getLeftCannon().getBullet().updatePosition();
        Game.getInstance().getPaddle().getRightCannon().getBullet().updatePosition();
    }

    private void bringBackBullets(){
        Integer X =  Game.getInstance().getPaddle().getPosVector().getX();
        Integer Y =  Game.getInstance().getPaddle().getPosVector().getY();
        PosVector pos = new PosVector(X,Y);
        PosVector pos2 = new PosVector(X-Game.getInstance().getPaddle().getLength(),Y);
        Game.getInstance().getPaddle().getLeftCannon().getBullet().setPosVector(pos);
        Game.getInstance().getPaddle().getRightCannon().getBullet().setPosVector(pos2);
        Game.getInstance().getPaddle().getLeftCannon().getBullet().outOfScreen =false;
    }
}
