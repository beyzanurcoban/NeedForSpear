package domain;

import domain.obstacle.ObstacleFactory;
import domain.obstacle.*;
import domain.path.CircularPath;
import domain.path.StraightVerticalBFPath;
import util.PosVector;
//import javafx.geometry.Pos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Layout {
    // Represents the locations and types of the objects in the game.

    /////////////////////////////////////////////////////////////////////////////////////
    private static int yOffset = 70;
    // Total number of Simple Obstacles(Wall Maria)
    private static int wallMariaCount;

    // Total number of Firm Obstacles(Steins Gate)
    private static int steinsGateCount;

    // Total number of Explosive Obstacles(Pandora’s Box)
    private static int pandoraBoxCount;

    // Total number of Gift Obstacles(Gift of Uranus)
    private static int uranusCount;

    // Minimum criteria limits
    private static int WALLMARIA_LIMIT= 75, STEINGSGATE_LIMIT = 10, PANDORABOX_LIMIT = 5, URANUS_LIMIT = 10;

    // Holds positions of all obstacles
    private static HashMap<Obstacle, PosVector> obstacle_positions;

    // Holds the center of circles of the paths that some obstacles move in
    private static HashMap<Obstacle, PosVector> obstacle_centers;

    // Moving obstacle map
    private static HashMap<Obstacle, PosVector> obstacle_moving;

    // Layout width
    private int layoutWidth;

    // Layout heigth
    private int layoutHeight;

    // Random Seed number
    private int SEED_NUMBER =14;

    // Collision Checker for Obstacles
    private static CollisionChecker CC;

    // Offset to place the obstacles
    private int obsLen;
    private int layoutHeightOffset = 350;

    // Obstacle type constants
    private int WALLMARIA_TYPE = 0;
    private int STEINSGATE_TYPE = 1;
    private int PANDORASBOX_TYPE = 2;
    private int GIFTOFURANUS_TYPE = 3;

    // Probs of WallMaria and SteinsGate moving
    private static double MOVING_PROBS = 0.2;

    // Scale factor for run mode
    private static double SCALE_TO_RUN_MODE;


    /////////////////////////////////////////////////////////////////////////////////////

    // Constructor
    public Layout(int wallMariaCount, int steinsGateCount, int pandoraBoxCount, int uranusCount,
            int layoutWidth, int layoutHeight, double scaleToRunMode){
        this.wallMariaCount = wallMariaCount;
        this.steinsGateCount = steinsGateCount;
        this.pandoraBoxCount = pandoraBoxCount;
        this.uranusCount = uranusCount;

        this.CC = CollisionChecker.getInstance();
        obstacle_positions = new HashMap<>();
        obstacle_centers = new HashMap<>();
        obstacle_moving = new HashMap<>();

        this.layoutWidth = layoutWidth;
        this.layoutHeight = layoutHeight;
        obsLen = layoutWidth/50;
        SCALE_TO_RUN_MODE = 1/scaleToRunMode - 0.4;
        //setLayout();
    }

    // Constructor
    public Layout(){

    }

    // Getters
    public static int getWallMariaCount() {
        return wallMariaCount;
    }

    public static int getSteinsGateCount() {
        return steinsGateCount;
    }

    public static int getPandoraBoxCount() {
        return pandoraBoxCount;
    }

    public static int getUranusCount() {
        return uranusCount;
    }

    // This method is called once the map is build to save the centers of the obstacles.
    public void buildCenterMap() {
        for (Obstacle obs : obstacle_positions.keySet()){
            if (obs.is_rotating) {
                obstacle_positions.put(obs, obstacle_positions.get(obs));
            }
        }
    }

    // Creates obstacles
    public void setLayout(){
        /*EFFECTS: It creates obstacles at some locations by looking at the numbers
        of the obstacle types. It generates the game layout.
        MODIFIES: HashMap<Obstacle, PosVector> obstacle_positions
        */
        Random rnd = new Random(SEED_NUMBER);
        Integer[] obsCounts = {wallMariaCount, steinsGateCount, pandoraBoxCount, uranusCount};
        Obstacle obs;

        for(int type = 0; type < obsCounts.length; type++) {
            for (int i = 0; i < obsCounts[type]; i++) {
                while (true) {
                    PosVector pos = new PosVector(
                            rnd.nextInt(layoutWidth - obsLen - 2),
                            rnd.nextInt(layoutHeight - layoutHeightOffset - yOffset) + yOffset);
                    obs = ObstacleFactory.getInstance().getObstacle(type, pos);

                    if (isAvailable(obs)) {
                        obstacle_positions.put(obs, pos);
                        // domainObjArr = [obs, box if exists, remain if exist]
                        Game.getInstance().getDomainObjectArr().add(obs);
                        Game.getInstance().getDomainObjectArr().add(obs.getBox());
                        Game.getInstance().getDomainObjectArr().add(obs.getRemains());
                        break;
                    }
                }
            }
        }
    }


    // Collider check for creating Layout
    public static boolean isAvailable(Obstacle newObs){
        /*
        EFFECTS: It compares the newcoming obstacle positions with all the existing obstacles' positions
                If there is an overlapping, it returns false. Otherwise, it returns true.
        */
        for (Obstacle obs: obstacle_positions.keySet()){
            if (CollisionChecker.getInstance().checkCollision(newObs, obs)){
                return false;
            }
        }

        return true;
    }

    // Sets the movements of the obstacles
    public static void setMovesObstacle(){
        Random rnd = new Random(6);

        for (Obstacle obs: obstacle_positions.keySet()) {
            String type = obs.getType();
            PosVector pos = obs.getPos();

            // Horizantal Movement
            if (type.equals("WallMaria") || type.equals("SteinsGate")) {
                double probs = rnd.nextDouble();
                if (probs <= MOVING_PROBS) {
                    obs.is_moving = true;
                    obs.setPathBehaviour(
                            new StraightVerticalBFPath(pos.getX(), pos.getY(), 5));
                }

                // Circular Movement
            } else if (type.equals("PandorasBox")) {
                double probs = rnd.nextDouble();
                if (probs <= 1) {
                    obs.is_moving = true;
                    obs.setPathBehaviour(
                            new CircularPath(obs, 0.2));
                }
            }

        }
    }

    // Getter method for obstacle_positions
    public static HashMap<Obstacle, PosVector> getObstaclePositions() {
        /*
        EFFECTS: returns a hashmap that keeps the obstacle positions
        */
        return obstacle_positions;
    }

    // Setter method for obstacle_positions
    public static void setObstaclePositions(HashMap<Obstacle, PosVector> newMap) {
         /*
        EFFECTS: assign a new obstacle position array
        MODIFIES: this.obstacle_positions
        */
        obstacle_positions = newMap;
    }

    // Returns center of circles of the paths that some obstacles move in.
    public HashMap<Obstacle, PosVector> getObstacleCenters() {
        /*
        EFFECTS: returns a hashmap that keeps the obstacle centers
        */
        return obstacle_centers;
    }

    // Getter method for obstacle_positions
    public static HashMap<Obstacle, PosVector> getObstacleMoving() {
        /*
        EFFECTS: returns a hashmap that keeps the obstacle positions
        */
        return obstacle_moving;
    }

    // Scales object x for run mode screen
    public static void scaleObstaclesPosX(){
        for (Obstacle obs: obstacle_positions.keySet()){
            obs.setPosVector(new PosVector((int) (obs.getPos().getX() * SCALE_TO_RUN_MODE),obs.getPos().getY()));
            RemainingPieces remainingPieces = obs.getRemains();
            Box box = obs.getBox();

            if(remainingPieces!=null){
                remainingPieces.setPosVector(
                        new PosVector(
                                (int) (remainingPieces.getPosVector().getX() * SCALE_TO_RUN_MODE),
                                remainingPieces.getPosVector().getY()));
            }

            if(box!=null){
                box.setPosVector(
                        new PosVector(
                                (int) (box.getPosVector().getX() * SCALE_TO_RUN_MODE),
                                box.getPosVector().getY()));
            }

        }

        for (DomainObject domainObject: Game.getInstance().getDomainObjectArr()) {
            if (domainObject instanceof Obstacle){
                Obstacle obs = (Obstacle) domainObject;
                obs.setPosVector(new PosVector((int) (obs.getPos().getX() * SCALE_TO_RUN_MODE), obs.getPos().getY()));
                RemainingPieces remainingPieces = obs.getRemains();
                Box box = obs.getBox();

                if (remainingPieces != null) {
                    remainingPieces.setPosVector(
                            new PosVector(
                                    (int) (remainingPieces.getPosVector().getX() * SCALE_TO_RUN_MODE),
                                    remainingPieces.getPosVector().getY()));
                }

                if (box != null) {
                    box.setPosVector(
                            new PosVector(
                                    (int) (box.getPosVector().getX() * SCALE_TO_RUN_MODE),
                                    box.getPosVector().getY()));
                }

            }
        }

    }

    // Is minimum criteria satisfies
    public static boolean isLayoutSatisfies(){

        return (wallMariaCount >= WALLMARIA_LIMIT)
                && (steinsGateCount >= STEINGSGATE_LIMIT)
                && (pandoraBoxCount >= PANDORABOX_LIMIT)
                && (uranusCount >= URANUS_LIMIT);
    }

    // Deletes all Layout
    public void cleanLayout(){
        obstacle_positions.clear();
        Game.getInstance().getDomainObjectArr().clear();
    }

    // Returns an obstacle which is present in mouse (X,Y)
    public Obstacle getCollideObstacle(DomainObject mouse){
        for (Obstacle obs: obstacle_positions.keySet()){
            if ( CC.checkCollision(mouse, obs)){
                return obs;
            }
        }

        return null;
    }

    // Changes type of the obstacle.
    public void changeTypeObstacle(Obstacle obs){
        Obstacle changeObs;
        String type = obs.getType();
        PosVector pos = obs.getPos();
        removeObstacle(obs);

        if (type.equals("WallMaria")){
            this.wallMariaCount--;
            changeObs = ObstacleFactory.getInstance().getObstacle(STEINSGATE_TYPE,pos);
            this.steinsGateCount++;
        }else if (type.equals("SteinsGate")){
            this.steinsGateCount--;
            changeObs = ObstacleFactory.getInstance().getObstacle(PANDORASBOX_TYPE,pos);
            this.pandoraBoxCount++;
        }else if (type.equals("PandorasBox")){
            this.pandoraBoxCount--;
            changeObs = ObstacleFactory.getInstance().getObstacle(GIFTOFURANUS_TYPE,pos);
            this.uranusCount++;
        }else {
            this.uranusCount--;
            changeObs = ObstacleFactory.getInstance().getObstacle(WALLMARIA_TYPE,pos);
            this.wallMariaCount++;
        }
        obstacle_positions.put(changeObs,changeObs.getPosVector());
        Game.getInstance().getDomainObjectArr().add(changeObs);
        Game.getInstance().getDomainObjectArr().add(changeObs.getBox());
        Game.getInstance().getDomainObjectArr().add(changeObs.getRemains());
        System.out.println("CHANGE");
    }


    // Adds new obstacle to the Layout with given type and location.
    public void addNewObstacle(PosVector pos){
        Obstacle obs = ObstacleFactory.getInstance().getObstacle(WALLMARIA_TYPE,pos);
        this.wallMariaCount++;
        obstacle_positions.put(obs,obs.getPosVector());
        Game.getInstance().getDomainObjectArr().add(obs);
        Game.getInstance().getDomainObjectArr().add(obs.getBox());
        Game.getInstance().getDomainObjectArr().add(obs.getRemains());
        System.out.println("ADD");
    }


    // Removes an obstacle from Layout.
    public void removeObstacle(Obstacle obs){
        Game.getInstance().getDomainObjectArr().remove(obs.getRemains());
        Game.getInstance().getDomainObjectArr().remove(obs.getBox());
        Game.getInstance().getDomainObjectArr().remove(obs);
        obstacle_positions.remove(obs);


        String type = obs.getType();
        if (type.equals("WallMaria")){
            this.wallMariaCount--;
        }else if (type.equals("SteinsGate")){
            this.steinsGateCount--;
        }else if (type.equals("PandorasBox")){
            this.pandoraBoxCount--;
        }else {
            this.uranusCount--;
        }

        System.out.println("REMOVE");
    }




}
