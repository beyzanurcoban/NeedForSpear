package domain.controller;

import domain.Layout;
import domain.MouseColliderBox;
import domain.obstacle.Obstacle;
import util.PosVector;

import java.util.BitSet;
import java.util.HashMap;

public class LayoutController {

    /////////////////////////////////////////////////////////////////////////////////////

    private Layout layout;
    private int FRAME_WIDTH;
    private int FRAME_HEIGHT;
    private int OBS_OFFSET = 35;

    private int PANEL_WIDTH;
    private int PANEL_HEIGHT;
    private double C_PANEL_WIDTH = 0.6;

    private double C_PADDLE_OFFSET_HEIGHT_LINE = 0.8;

    private HashMap<String, Integer> obstacleSettings;

    private PosVector mousePos;
    private BitSet keyBits;

    private Obstacle dragObstacle;


    /////////////////////////////////////////////////////////////////////////////////////

    public LayoutController() {
        mousePos = new PosVector(0,0);
    }

    // Deletes an obstacle in (X,Y) if its present
    public void deleteObs(int x, int y) {
        Obstacle obsCol = collideWithMouse(x,y);
        if (obsCol != null) {
            layout.removeObstacle(obsCol);
        }
    }

    // Add an obstacle in (X,Y) if its not present
    // Change type of an obstacle in (X,Y) if its present
    public void addOrChangeObstacle(int x, int y){
        Obstacle obsCol = collideWithMouse(x,y);

        if ( isInRange(x, y) ){
            if (obsCol == null) {
                PosVector pos = new PosVector(x, y);
                layout.addNewObstacle(pos);
            }else{
                layout.changeTypeObstacle(obsCol);
            }
        }

    }

    // First part of dragging operator which holds an obstacle
    public void holdObstacle(int x, int y){
        Obstacle obsCol = collideWithMouse(x,y);

        if (obsCol != null){
            dragObstacle = obsCol;
        }else{
            dragObstacle = null;
        }
    }

    // Second part of dragging operator which relases the hold obstacle
    public void releaseObstacle(int x, int y){
        Obstacle obsCol = collideWithMouse(x,y);

        if (obsCol == null && isDrag() && isInRange(x,y)){
            dragObstacle.getPosVector().setX(x);
            dragObstacle.getPosVector().setY(y);
        }else{
            dragObstacle = null;
        }

    }


    // Gets random Layout after the obstacle settings
    public void craftRandomLayout(){
        if (layout!=null) layout.cleanLayout();

        layout = new Layout(
                obstacleSettings.get("simpleObstacleCount"),
                obstacleSettings.get("firmObstacleCount"),
                obstacleSettings.get("explosiveObstacleCount"),
                obstacleSettings.get("giftObstacleCount"),
                PANEL_WIDTH,
                PANEL_HEIGHT,
                C_PANEL_WIDTH
        );
        layout.setLayout();

    }

    public void craftEmptyLayout(){

        layout = new Layout(0,0,0,0,
                PANEL_WIDTH,
                PANEL_HEIGHT,
                C_PANEL_WIDTH
        );

    }

    public Layout getLayout(){
        return layout;
    }

    public void setObstacleSettings(HashMap<String, Integer> obstacleSettings){
        checkObstacleSettings(obstacleSettings);
        this.obstacleSettings = obstacleSettings;

    }

    public void setFramePanelWidthHeight(int FRAME_WIDTH, int FRAME_HEIGHT) {
        this.FRAME_WIDTH = FRAME_WIDTH;
        this.FRAME_HEIGHT = FRAME_HEIGHT;

        PANEL_WIDTH = (int) (FRAME_WIDTH * C_PANEL_WIDTH);
        PANEL_HEIGHT = FRAME_HEIGHT;

    }

    // Is minimum criteria satisfies
    public boolean isLayoutSatisfies(){
        return Layout.isLayoutSatisfies();
    }

    // Prints layout
    private void printLayout(){
        System.out.println(obstacleSettings);
        for (Obstacle obs : layout.getObstaclePositions().keySet()){
            System.out.println(obs.toString());
        }

    }

    // Set mousePos for mouseCollider
    private void setMouseInput(int mouseX, int mouseY) {
        mousePos.setX(mouseX);
        mousePos.setY(mouseY);

    }

    // if location is in the panel view
    private boolean isInRange(int x, int y){
        return  x > 0
                && x < PANEL_WIDTH
                && y > 0
                && y < (PANEL_HEIGHT * C_PADDLE_OFFSET_HEIGHT_LINE - OBS_OFFSET);
    }


    // Returns an obstacle which is present in mouses location
    private Obstacle collideWithMouse(int x, int y){
        setMouseInput(x, y);
        MouseColliderBox mouseColliderBox = new MouseColliderBox(mousePos);
        return layout.getCollideObstacle(mouseColliderBox);

    }

    // Is some obstacle being draged.
    private boolean isDrag(){
        return dragObstacle != null;
    }

    // Check Obstacle Settings
    private void checkObstacleSettings(HashMap<String, Integer> obstacleSettings){
        int simpleObstacleCount = obstacleSettings.get("simpleObstacleCount");
        int firmObstacleCount = obstacleSettings.get("firmObstacleCount");
        int explosiveObstacleCount = obstacleSettings.get("explosiveObstacleCount");
        int giftObstacleCount = obstacleSettings.get("giftObstacleCount");

         // Simple obstacle limits
        if (simpleObstacleCount < 0){
            obstacleSettings.replace("simpleObstacleCount",simpleObstacleCount,0);
        }else if (simpleObstacleCount > 100){
            obstacleSettings.replace("simpleObstacleCount",simpleObstacleCount,100);
        }

        // Firm obstacle limits
        if (firmObstacleCount < 0){
            obstacleSettings.replace("firmObstacleCount",firmObstacleCount,0);
        }else if (firmObstacleCount > 50){
            obstacleSettings.replace("firmObstacleCount",firmObstacleCount,50);
        }

        // Explosive Obstacle limits
        if (explosiveObstacleCount < 0){
            obstacleSettings.replace("explosiveObstacleCount",explosiveObstacleCount,0);
        } else if (explosiveObstacleCount > 50){
            obstacleSettings.replace("explosiveObstacleCount",explosiveObstacleCount,50);
        }

        // Gift Obstacle limilts
        if (giftObstacleCount < 0){
            obstacleSettings.replace("giftObstacleCount",explosiveObstacleCount,0);
        } else if (giftObstacleCount > 20){
            obstacleSettings.replace("giftObstacleCount",explosiveObstacleCount,20);
        }

    }

}


