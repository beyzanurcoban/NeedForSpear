package testing;

import domain.Ball;
import domain.Game;
import domain.Paddle;
import domain.obstacle.Obstacle;
import domain.obstacle.WallMaria;
import util.PosVector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameOverTest implements ActionListener {
    Game g;
    Ball ball;
    Paddle paddle;
    WallMaria wm;
    static final int FRAME_WIDTH = 1368;
    static final int FRAME_HEIGHT = 766;
    private static final int TIMER_SPEED = 5;
    private javax.swing.Timer game_Timer = new javax.swing.Timer(TIMER_SPEED,  this);


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        System.out.println("Running: Set Up");
        HashMap<String, Integer> runSettings = new HashMap<String, Integer>();
        HashMap<Obstacle, PosVector> obstacle_positions = new HashMap<Obstacle, PosVector>();
        wm = new WallMaria(400,400);
        obstacle_positions.put(wm,wm.getPosVector());
        runSettings.put("screenWidth",  FRAME_WIDTH);
        runSettings.put("screenHeight",  FRAME_HEIGHT);
        g = Game.getInstance();
        g.onRunEvent(runSettings,"Oya", "99",0,10, 2.0,3.0,4.0);
        g.gameState.getPlayer().setChance_points(3);
        paddle =g.getPaddle();
        ball =g.getBall();



    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.out.println("Running: Tear Down");
        g.getDomainObjectArr().remove(wm);

    }
    //Glass box test
    @org.junit.jupiter.api.Test
    void IsGetChanceWork(){
        assertEquals(g.gameState.getPlayer().getChance_points(), 3);
    }

    //Glass box and blackbox together
    @org.junit.jupiter.api.Test
    void IsGameOver(){
        g.gameState.getPlayer().setChance_points(0);
        g.gameOverCheck();
        assertEquals(g.gameState.getIsRunning(), false);
    }
    //Glass box and blackbox together
    @org.junit.jupiter.api.Test
    void GameNotOver(){
        g.gameState.getPlayer().setChance_points(2);
        g.gameOverCheck();
        assertEquals(g.gameState.getIsRunning(), true);
    }

    //Glass box testing
    @org.junit.jupiter.api.Test
    void GameOverWinCheck(){
        g.gameState.getPlayer().setChance_points(2);
        g.getDomainObjectArr().remove(wm);
        g.gameOverCheck();
        assertEquals(g.getIsWin(), true);
    }
    //Glass box testing
    @org.junit.jupiter.api.Test
    void GameWonIsTimerStopped(){
        g.gameState.getPlayer().setChance_points(2);
        g.gameOverCheck();
        g.getDomainObjectArr().remove(wm);
        assertEquals(g.getTimer().isRunning(), false);
    }
    //Glass box testing
    @org.junit.jupiter.api.Test
    void IsTimerStoppedGameLose(){
        g.gameState.getPlayer().setChance_points(0);
        g.gameOverCheck();
        assertEquals(g.getTimer().isRunning(), false);
    }
    //Glass box testing
    @org.junit.jupiter.api.Test
    void TimerRunningObstacleLeft(){
        g.gameState.getPlayer().setChance_points(2);
        g.getDomainObjectArr().add(wm);
        g.gameOverCheck();
        assertEquals(g.getTimer().isRunning(), true);
    }
    //Glass box testing
    @org.junit.jupiter.api.Test
    void IsObjectArrayNull(){
        g.gameState.getPlayer().setChance_points(2);
        assertEquals(g.getDomainObjectArr().isEmpty(), true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }
}