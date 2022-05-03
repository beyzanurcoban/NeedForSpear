package UI;

import domain.*;
import domain.controller.KeyboardController;
import domain.controller.LayoutController;
import domain.obstacle.Obstacle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.BitSet;

@SuppressWarnings("serial")
public class LayoutPanel extends JPanel implements ActionListener,MouseListener, KeyListener,MouseMotionListener{

    BufferedImage img; // background
    String infoString = "";
    KeyboardController kc = new KeyboardController();


    // Timer
    private static final int TIMER_SPEED = 50;
    private static final int INFO_REFRESH_PERIOD = 3000;
    private Timer tm = new Timer(TIMER_SPEED,  this);

    // LayoutController
    private LayoutController LC;
    private Layout layout;

    private int PANEL_WIDTH;
    private int PANEL_HEIGHT;
    private double C_PANEL_WIDTH = 0.6;

    private double C_PADDLE_OFFSET_HEIGHT_LINE = 0.8;
    private int PADDLE_OFFSET_HEIGHT_LINE;

    // Ketbits
    private BitSet keyBits = new BitSet(256);


    /////////////////////////////////////////////////////////////////////////////////////

    public LayoutPanel(Layout layout, int frame_width, int frame_height) {
        super();
        this.setVisible(true);
        this.PANEL_WIDTH = frame_width;
        this.PANEL_HEIGHT = frame_height;
        try {
            initializeLayoutPanel();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.setBackground(Color.BLACK);

    }

    public LayoutPanel(LayoutController LC, int frame_width, int frame_height) {

        this.LC = LC;
        this.PANEL_WIDTH = (int) (frame_width * C_PANEL_WIDTH);
        this.PANEL_HEIGHT = frame_height;
        this.PADDLE_OFFSET_HEIGHT_LINE = (int) (C_PADDLE_OFFSET_HEIGHT_LINE * PANEL_HEIGHT);

        try {
            initializeLayoutPanel();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.setBackground(Color.LIGHT_GRAY);
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();

        for (DomainObject domainObject : Game.getInstance().getDomainObjectArr()) {
            try {
                drawComponent(g2d, domainObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
            g2d.setTransform(old);

        }

        g2d.drawLine(0,PADDLE_OFFSET_HEIGHT_LINE,PANEL_WIDTH,PADDLE_OFFSET_HEIGHT_LINE);

        g2d.setTransform(old);

    }

    private void drawComponent(Graphics2D g2d, DomainObject d) throws IOException {
        // TODO Auto-generated method stub
        if (d instanceof Obstacle) {
            ObstacleView.getInstance().draw(g2d, d, PANEL_WIDTH, PANEL_HEIGHT);
        }

    }

    public void initializeLayoutPanel() throws IOException {
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setMaximumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        tm.start();
        addMouseListener(this);
        addKeyListener(this);
        //this.setSize(frame_width,frame_height);
    }

    // Mouse Events
    @Override
    public void mouseClicked(MouseEvent e) {
        // Invoked when the mouse button has been clicked (pressed and released) on a component

        // Deletes obstacle if its present in (X,Y)
        if (SwingUtilities.isRightMouseButton(e)){
            LC.deleteObs(e.getX(),e.getY());
        }

        // Add an obstacle in (X,Y) if its not present
        // Change type of an obstacle in (X,Y) if its present
        if (SwingUtilities.isLeftMouseButton(e)){
            LC.addOrChangeObstacle(e.getX(),e.getY());
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Invoked when a mouse button has been pressed on a component
        if (SwingUtilities.isLeftMouseButton(e)){
            LC.holdObstacle(e.getX(),e.getY());
        }


    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Invoked when a mouse button has been released on a component
        if (SwingUtilities.isLeftMouseButton(e)){
            LC.releaseObstacle(e.getX(),e.getY());
        }


    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }




    // Key Events
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();

    }


}

