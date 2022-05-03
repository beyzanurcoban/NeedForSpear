package UI;

import java.awt.*;
import java.awt.event.KeyListener;
import java.util.HashMap;
import javax.swing.*;

import domain.*;


public class PlaygroundScreen implements IRunListener, IAuthorizeListener {
    protected static JFrame jf;
    protected BuildModeScreen bd;

    RunGameObjects screen;


    public void onRunEvent(HashMap<String, Integer> runSettings, String username, String id, Integer num, Integer freq,Double prob1, Double prob2, Double prob3) {
        int screenWidth = runSettings.get("screenWidth").intValue();
        int screenHeight = runSettings.get("screenHeight").intValue();
        initializeOuterFrameSettings(screenWidth, screenHeight);
        openRunModeScreen(screenWidth, screenHeight);

    }

    public PlaygroundScreen() {


    }

    private void initializeOuterFrameSettings(int screenWidth, int screenHeight) {
        jf = new JFrame();
        jf.setTitle("Need For Spear");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(new BorderLayout(5, 5));
        jf.setSize(screenWidth, screenHeight);
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setFocusable(true);
        jf.setLocationRelativeTo(null);
    }

    private void openRunModeScreen(int width, int height) {
        screen = new RunGameObjects(width, height);
        this.bd.addLoadListener(screen);
        screen.setVisible(true);
        jf.add(screen);
        jf.addKeyListener(screen);
    }


    @Override
    public void onClickEvent(PlaygroundScreen nfs, String username, String id,Integer gameNum) {
        nfs.bd = new BuildModeScreen();
        nfs.bd.setVisible(true);
        nfs.bd.addListener(nfs);
        nfs.bd.addListener(Game.getInstance());
        nfs.bd.addLoadListener(Game.getInstance());
        nfs.bd.setUserName(username);
        nfs.bd.setID(id);
        nfs.bd.setGameNum(gameNum);
        
    }

}
