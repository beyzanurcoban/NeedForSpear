package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.BitSet;

import javax.swing.*;

import domain.Box;

import domain.controller.KeyboardController;
import domain.* ;
import domain.obstacle.Obstacle;


@SuppressWarnings("serial")
public class RunGameObjects extends JPanel implements ActionListener, KeyListener, IGameListener,IChanceListener,ILoadListener,IBoxListener,IRemainsListener {

    /////////////////////////////////////////////////////////////////////////////////////

    Timer tm = new Timer(TIMER_SPEED, this);
    private static final int TIMER_SPEED = 50;
    private static final int INFO_REFRESH_PERIOD = 3000;
    private static final int infoStringHeight = 30;
    private static int yOffset = 70;
    private static int xOffset = 175;
    public static int frame_width;
    public static int frame_height;

    private BufferedImage img; // background
    private String infoString = "";
    private int infoRefreshCount;
    private KeyboardController kc = new KeyboardController();
    private Boolean stop = false;
    private BitSet keyBits = new BitSet(256);
    private Integer chance =3;
    private JPanel chancePanel;
    private JPanel invPanel;
    private JPanel scorePanel;
    private JLabel scoreNameLabel;
    private JLabel scoreNumLabel;
    private JLabel chanceNumberLabel;
    private JLabel expNumberLabel;
    private JLabel unstopNumberLabel;
    private JLabel rocketNumberLabel;
    private JButton helpButton;


    private  ImageIcon icon;
    private boolean update = false;
    private boolean isBoxDropped = false;
    private boolean isRemainFall= false;
    private double boxX;
    private double boxY;
    private double remainX;
    private double remainY;

    public RunGameObjects(int width, int height) {
        this.frame_width = width;
        this.frame_height = height;

        try {
            initializeRunModeScreen();
            Game.getInstance().gameState.addListener(this);
            CollisionChecker.getInstance().addListener(this);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initializeRunModeScreen() throws IOException {
        this.setFocusable(true);
        scorePanel = initializeScorePanel();
        chancePanel =initializeChancePanel();
        invPanel = initializeInventoryPanel();

        chancePanel.setVisible(true);
        scorePanel.setVisible(true);
        invPanel.setVisible(true);

        this.add(chancePanel);
        this.add(scorePanel);
        this.add(invPanel);

        tm.start();
    }

    private JPanel initializeScorePanel(){
        JPanel scoreP = new JPanel();
        scoreNameLabel = new JLabel("Score: ");
        scoreNumLabel = new JLabel("0");

        scoreP.add(scoreNameLabel);
        scoreP.add(scoreNumLabel);
        return scoreP;
    }
    private JPanel initializeChancePanel(){
        JPanel ChanceP = new JPanel();
        ImageIcon icon = new ImageIcon(new ImageIcon("src/assets/3heart.png").getImage().getScaledInstance(100,35, Image.SCALE_DEFAULT));
        ChanceP.add(new JLabel(icon));

        return ChanceP;
    }

    private JPanel initializeInventoryPanel(){
        JPanel invP = new JPanel();

        ImageIcon icon = new ImageIcon(new ImageIcon("src/assets/heart.png").getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
        invP.add(new JLabel(icon));
        chanceNumberLabel = new JLabel("0");
        invP.add(chanceNumberLabel);

        icon = new ImageIcon(new ImageIcon("src/assets/expansion.png").getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
        invP.add(new JLabel(icon));
        expNumberLabel = new JLabel("0");
        invP.add(expNumberLabel);

        icon = new ImageIcon(new ImageIcon("src/assets/unstoppable.png").getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
        invP.add(new JLabel(icon));
        unstopNumberLabel = new JLabel("0");
        invP.add(unstopNumberLabel);

        icon = new ImageIcon(new ImageIcon("src/assets/rocket.png").getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
        invP.add(new JLabel(icon));
        rocketNumberLabel = new JLabel("0");
        invP.add(rocketNumberLabel);

        icon = new ImageIcon(new ImageIcon("src/assets/empty.png").getImage().getScaledInstance(200,50, Image.SCALE_DEFAULT));
        invP.add(new JLabel(icon));

        icon = new ImageIcon(new ImageIcon("src/assets/help.png").getImage().getScaledInstance(30,45, Image.SCALE_DEFAULT));
        invP.add(new JLabel(icon));

        helpButton = new JButton("Help!");
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                ImageIcon helpIcon = new ImageIcon((new ImageIcon("src/assets/help.png").getImage().getScaledInstance(65,120, Image.SCALE_DEFAULT)));
                JOptionPane.showMessageDialog(PlaygroundScreen.jf,
                        "Need for Spear is an easy and competitive game to play. It combines fun and challenge. \n" +
                                "Two brave warriors are racing to obtain the Spear of Power that will enable its owner to rule the world given its powerful capabilities. \n" +
                                "Its creator, Shazam, the legendary wise magician, has created several obstacles to protect the spear in a way that only a worthy warrior shall be able to reach.\n" +
                                "In this game, two players will compete representing each of the warriors, and try to reach the Spear of Power before the other does.\n" +
                                "There are several types of obstacles that they shall face. The warrior can obtain magical abilities during their quest.\n" +
                                "Some of the magical abilities can be used to enhance one’s attacking power and status (Useful Magical Abilities), \n" +
                                "and others can be used to obstruct the other player from reaching the Spear of Power fast. \n" +
                                "The warrior who finishes all the obstacles first, is deemed the winner and the one worthy of this treasure. \n" +
                                "Each warrior is represented by their noble phantasm and enchanted sphere. A noble phantasm is a paddle-like object\n" +
                                "that is used to deflect the enchanted sphere from falling to the ground.\n" +
                                "The enchanted sphere is the object that is sent around to destroy obstacles, but it is affected by gravity,\n" +
                                "therefore the noble phantasm is used to set its track towards the target obstacles to destroy them.\n" +
                                "If the enchanted sphere falls below the noble phantasm, the warrior loses a chance. Each warrior has 3 chances only.\n" +
                                "Once the warrior runs out of chances, they are considered unworthy and therefore lose the game. \n" +
                                "If a player is deemed unworthy, then the other player automatically wins.",
                        "HELP!",
                        JOptionPane.WARNING_MESSAGE, helpIcon);

                PlaygroundScreen.jf.requestFocusInWindow();
            }
        });

        invP.add(helpButton);

        return invP;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        g2d.drawImage(img, 0, 0, null);

        for (DomainObject domainObject : Game.getInstance().getDomainObjectArr()) {
            try {
                drawComponent(g2d, domainObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
            g2d.setTransform(old);
        }

        try {
            drawPaddle(g2d, Game.getInstance().getPaddle(), frame_width, frame_height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        drawBall(g2d, Game.getInstance().getBall(), frame_width, frame_height);

        g2d.setTransform(old);
        int textWidth = g.getFontMetrics().stringWidth(infoString);
        g2d.drawString(infoString,  textWidth / 2, infoStringHeight);
        g2d.drawLine(0, yOffset, frame_width, yOffset);

        if(Game.getInstance().getPaddle().getHasCannon()){
            try {
                drawCannon(g2d, Game.getInstance().getPaddle().getLeftCannon(), frame_width, frame_height);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                drawCannon(g2d, Game.getInstance().getPaddle().getRightCannon(), frame_width, frame_height);
            } catch (IOException e) {
                e.printStackTrace();
            }
            drawBullet(g2d, Game.getInstance().getPaddle().getLeftCannon().getBullet(), frame_width, frame_height);
            drawBullet(g2d, Game.getInstance().getPaddle().getRightCannon().getBullet(), frame_width, frame_height);
        }
        if(isBoxDropped) {
            isBoxDropped = false;
        }

        if(isRemainFall) {
            isRemainFall = false;
        }
    }

    private void drawPaddle(Graphics2D g2d, Paddle d, int width, int height) throws IOException {
        PaddleView.getInstance().draw(g2d, d, width, height);
    }

    private void drawCannon(Graphics2D g2d, Cannon c, int width, int height) throws IOException {
        CannonView.getInstance().draw(g2d, c, width, height);
    }

    private void drawBall(Graphics2D g2d, Ball b, int width, int height) {
        BallView.getInstance().draw(g2d, b, width, height);

    }
    private void drawBullet(Graphics2D g2d, Bullet b, int width, int height) {
        BulletView.getInstance().draw(g2d, b, width, height);

    }

    private void drawBox(Graphics2D g2d, Box b, int width, int height, boolean isDrop) throws IOException {
        BoxView boxView = new BoxView();
        if(isDrop) {
            boxView.draw(g2d, b, width, height);
        }
    }

    private void drawRemain(Graphics2D g2d, RemainingPieces r, int width, int height, boolean isFall) throws IOException {
        RemainView remainView = new RemainView();
        if(isFall) {
            remainView.draw(g2d, r, width, height);
        }
    }

    private void drawComponent(Graphics2D g2d, DomainObject d) throws IOException {
        if (d instanceof Obstacle) {
            ObstacleView.getInstance().draw(g2d, d, frame_width, frame_height);
        }
        if (d instanceof Box) {
            for (Box b : CollisionChecker.getInstance().getBoxes()) {
                if (b.equals(d)){
                    drawBox(g2d, (Box) d, frame_width, frame_height,true);
                }
            }
        }
        if (d instanceof RemainingPieces) {
            for (RemainingPieces r : CollisionChecker.getInstance().getRemainingPieces()) {
                if (r.equals(d)){
                    drawRemain(g2d, (RemainingPieces) d, frame_width, frame_height,true);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameOverCheck();
        checkDeletedObstacles();
        updateScore();
        updateInventoryLabels();
        if(update) {
            updateChance();
        }
        repaint();
        try {
            ballChance();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        infoRefreshCount += TIMER_SPEED;
        if (infoRefreshCount >= INFO_REFRESH_PERIOD) {
            infoString = "";
            infoRefreshCount = 0;
        }
    }

    public void checkDeletedObstacles() {
        /*EFFECTS: Get rid offs obstacles that has been destroyed.
         MODIFIES: ObstacleArray
        */
        CollisionChecker.getInstance().ChecktoDelete();
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

   /* @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.set(keyCode);

        // Pause (p)
        if (isKeyPressed(80)) {
            pauseGame();
            return;
        }

        // Resume (r)
        if (isKeyPressed(82)) {
            resumeGame();
            return;
        }

        // Save (s)
        if (isKeyPressed(80)) {
            saveGame();
            return;
        }

        // Load (l)
        if (isKeyPressed(76)){
            loadGame();
            return;
        }


        if (kc.processKeys(keyBits)) { // when returns true restart
            tm.restart();
            Game.getInstance().gameState.isRunning = true;

        }

    }
*/
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        kc.released(Game.getInstance().gameState.getPaddle());
        int keyCode = e.getKeyCode();
        keyBits.clear(keyCode);
    }

    private void pauseGame(){
        infoString = "Game Paused.";
        repaint();
        tm.stop();
        Game.getInstance().gameState.isRunning = false;
        kc.processKeys(keyBits);

    }

    private void resumeGame(){
        infoString = "Game Resumed.";
        tm.restart();
        Game.getInstance().gameState.isRunning = true;
    }

    private void saveGame(){
        if (!tm.isRunning()) {
            infoString = "Game Saved.";
            kc.processKeys(keyBits); // only works if game was paused
        } else {
            infoString = "Press \"Pause\" Button before saving.";
        }

    }

    private void loadGame(){
        if (!tm.isRunning()) {
            infoString = "Game Loaded.";
            kc.processKeys(keyBits);
            tm.restart();// only works if game was paused
            Game.getInstance().gameState.isRunning = true;
            updateChance();
            scoreNumLabel.setText(Game.getInstance().getOldScore()+"");
        } else {
            infoString = "Press \"Pause\" Button before loading.";
        }

    }

    public void gameOverCheck() {
        Game.getInstance().gameOverCheck();
        Boolean isWin = Game.getInstance().getIsWin();
        Boolean isRunning =Game.getInstance().gameState.isRunning;
        ImageIcon attilaIcon = new ImageIcon("src/assets/attila.jpeg");

        if (!isRunning){
            tm.stop();

            JOptionPane.showMessageDialog(PlaygroundScreen.jf,
                    "You are out of chance! \n" + "Your Score: "+(int) Game.getInstance().getOldScore(),
                    "LOSER :(",
                    JOptionPane.WARNING_MESSAGE, attilaIcon);
            PlaygroundScreen.jf.dispose();

        }
        else if (isWin){
            tm.stop();
            JOptionPane.showMessageDialog(PlaygroundScreen.jf,
                    "You win the game! \n" + "Your Score: "+(int) Game.getInstance().getOldScore(),
                    "WINNER :)",
                    JOptionPane.WARNING_MESSAGE, attilaIcon);

            PlaygroundScreen.jf.dispose();
        }
    }

    public void ballChance() throws InterruptedException {
        if(stop){
            wait(2000);

        }
    }

    private void updateScore(){
        int score = (int) Game.getInstance().getOldScore();
        scoreNumLabel.setText(score+"");
    }

    private void updateInventoryLabels(){
        chanceNumberLabel.setText(Game.getInstance().gameState.getPlayer().getAbilities().get(1)+"");
        expNumberLabel.setText(Game.getInstance().gameState.getPlayer().getAbilities().get(2)+"");
        unstopNumberLabel.setText(Game.getInstance().gameState.getPlayer().getAbilities().get(3)+"");
        rocketNumberLabel.setText(Game.getInstance().gameState.getPlayer().getAbilities().get(4)+"");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.set(keyCode);

        // Pause (p)
        if (isKeyPressed(80)) {
            pauseGame();
            return;
        }

        // Resume (r)
        if (isKeyPressed(82)) {
            resumeGame();
            return;
        }

        // Save (s)
        if (isKeyPressed(80)) {
            saveGame();
            return;
        }

        // Load (l)
       /* if (isKeyPressed(76)){
            loadGame();
            return;
        }*/


        if (kc.processKeys(keyBits)) { // when returns true restart
            tm.restart();
            Game.getInstance().gameState.isRunning = true;

        }

    }


    @Override
    public void onClickEvent() {
        stop = true;
    }

    @Override
    public void onLoseChance(Integer chance) {
        this.chance = chance;
        update =true;

    }

    public void updateChance(){

        chancePanel.removeAll();
        if(chance == 3){
            chancePanel.removeAll();
            icon = new ImageIcon(new ImageIcon("src/assets/3heart.png").getImage().getScaledInstance(100,35, Image.SCALE_DEFAULT));
            chancePanel.add(new JLabel(icon));
            chancePanel.setVisible(true);
            update =false;
        }
        else if(chance == 2){
            chancePanel.removeAll();
            icon = new ImageIcon(new ImageIcon("src/assets/2heart.png").getImage().getScaledInstance(100,35, Image.SCALE_DEFAULT));
            chancePanel.add(new JLabel(icon));
            chancePanel.setVisible(true);
            update =false;
        }
        else if(chance == 1){
            chancePanel.removeAll();
            icon = new ImageIcon(new ImageIcon("src/assets/1heart.png").getImage().getScaledInstance(100,35, Image.SCALE_DEFAULT));
            chancePanel.add(new JLabel(icon));
            chancePanel.setVisible(true);
            update =false;
        }
        else if(chance == 0){
            chancePanel.removeAll();
            icon = new ImageIcon(new ImageIcon("src/assets/0heart.png").getImage().getScaledInstance(100,35, Image.SCALE_DEFAULT));
            chancePanel.add(new JLabel(icon));
            chancePanel.setVisible(true);
            update =false;
        }


    }

    @Override
    public void onClickEventDo(String n) {
        scoreNumLabel.setText(Game.getInstance().gameState.getPlayer().getScore()+"");
    }

    @Override
    public void dropBox(double x, double y) {
        isBoxDropped =true;
        boxX = x;
        boxY = y;
    }


  /*  @Override
    public void keyReleased(KeyEvent e) {
        kc.released(Game.getInstance().gameState.getPC().getPaddle());
        int keyCode = e.getKeyCode();
        keyBits.clear(keyCode);
    }*/

    public boolean isKeyPressed(int keyCode) {
        return keyBits.get(keyCode);
    }

    @Override
    public void dropRemains(double x, double y) {
        isRemainFall =true;
        remainX = x;
        remainY = y;
    }

}



