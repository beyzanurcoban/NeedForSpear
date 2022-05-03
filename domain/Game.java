package domain;
import util.PosVector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;


public class Game implements IRunListener, ILoadListener, ActionListener {
    static final int FRAME_WIDTH = 1368;
    static final int FRAME_HEIGHT = 766;

    public GameState gameState;
    public Saver saver;
    static Game instance;

    private Paddle paddle;
    private Ball ball;
    public boolean isLoad = false;
    private static final int TIMER_SPEED = 5;
    private static final long TOTAL_DEATH_TIME = 4500;
    private long deathInitTime = -100;
    private boolean isWin = false;
    private long initialTime;
    private int score = 0;
    private static int yOffset = 70;
    protected int Ymirfreq;
    protected Double YmirProb1;
    protected Double YmirProb2;
    protected Double YmirProb3;
    private Ymir ymir;
    private String laodgamename;
    public boolean isStarted = false;
    private Thread ymirThread;

    private javax.swing.Timer game_Timer;

    private Game() {
        gameState = new GameState();
        game_Timer = new javax.swing.Timer(TIMER_SPEED, this);
        ymir = new Ymir();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public Timer getTimer() {
        return instance.game_Timer;

    }

    public void loadGame() {
        saver = new Saver();
        saver.loadGame(Game.getInstance().getPaddle(), Game.getInstance().ball,laodgamename);

    }

    public void saveGame() {
        saver = new Saver();
        saver.saveGame(Layout.getObstaclePositions());
    }

    /*EFFECTS: From oldScore value it calculates new value and updates the score attribute.
      MODIFIES: score
     */

    public Integer updateScore(int oldScore){
       long CurrentTime = System.currentTimeMillis();
       int NewScore =(int) (oldScore + 300/(double)((CurrentTime-initialTime)/1000));
       return NewScore;
    }
    public void setScore(int newScore){  this.score = newScore;}

    public int getOldScore() {
        return this.score;
    }

    @Override
    public void onRunEvent(HashMap<String, Integer> startParameters, String username, String id,Integer num, Integer freq,Double prob1, Double prob2, Double prob3) {

        paddle = new Paddle(FRAME_WIDTH,FRAME_HEIGHT);
        Layout.scaleObstaclesPosX();
        Layout.setMovesObstacle();
        this.ball = new Ball();
        this.ball.setisAlive(false);
        Ymirfreq = freq;
        YmirProb1 = prob1;
        YmirProb2 = prob2;
        YmirProb3 = prob3;

        Game.getInstance().gameState.isRunning = true;
        initialTime = System.currentTimeMillis();

        instance.gameState.getPlayer().setId(id);
        instance.gameState.getPlayer().setUserName(username);
        instance.gameState.getPlayer().setGameNum(num);
        instance.gameState.getPlayer().initializeInventory();
        game_Timer.start();

        if(isLoad){
            Game.getInstance().loadGame();
        }
        System.out.println("Ymir freq in game is "+ Game.getInstance().Ymirfreq + "\n");
        ymir.setPeriod(Ymirfreq);
        ymir.setProbabilities(YmirProb1, YmirProb2, YmirProb3);

        ymirThread = new Thread(ymir);
        ymirThread.start();

    }


    public ArrayList<DomainObject> getDomainObjectArr() {
        return this.gameState.getDomainObjectArr();
    }

    public void setDomainObjectArr(ArrayList<DomainObject> list) {
        this.gameState.setDomainList(list);
    }

     /*EFFECTS: If player has no more chance points it stops running, if player cleared whole layout
        game stops running and player has won.
        MODIFIES: this (Game), isWin, game_Timer, isRunning
    */
    public void gameOverCheck(){
        Integer chancePoint = instance.gameState.getPlayer().getChance_points();
        if( chancePoint <= 0 ){
            instance.gameState.isRunning = false;
            game_Timer.stop();
            ymir.terminate();
        }
        else if(instance.getDomainObjectArr().size() == 0){
            this.isWin= true;
            game_Timer.stop();
            ymir.terminate();
        }
    }
    public Paddle getPaddle() {return paddle;}

    public Ball getBall() {return ball;}

    public boolean getIsWin(){ return this.isWin;}


    @Override
    public void onClickEventDo(String n) {
        isLoad = true;
        laodgamename =n;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameOverCheck();

        if(!Game.getInstance().ball.checkAlive()) {

            if (deathInitTime < 0) {
                // if dead but just now dead, initialize deathInitTime
                deathInitTime = System.currentTimeMillis();
                Integer points = Game.getInstance().gameState.getPlayer().getChance_points() - 1;
                Game.getInstance().gameState.setChance(points);
            } else {
                // ball's been dead
                // check how long ball's been dead
                long deathTime = System.currentTimeMillis() - deathInitTime;
                if (deathTime > TOTAL_DEATH_TIME) {
                    // if ball's been dead long enough, call this code
                    Game.getInstance().ball.setOutOfScreen(true);
                    PosVector pos = new PosVector(FRAME_WIDTH/2, yOffset+1);
                    Game.getInstance().ball.setPosVector(pos);
                    Game.getInstance().ball.setyVelocity(0);
                    Game.getInstance().getBall().setXVelocity(1);

                    deathInitTime = -1L;  // and re-initialize deathInitTime
                }
                Game.getInstance().ball.setOutOfScreen(true);
            }
        }

    }
}

