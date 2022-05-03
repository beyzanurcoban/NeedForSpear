package UI;

import domain.obstacle.*;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import domain.*;

public class ObstacleView implements Drawable {

    //Obstacle obstacle;
    private BufferedImage simple_obs_img ;
    private BufferedImage explosive_obs_img;
    private BufferedImage gift_obs_img;
    private BufferedImage hollow_purple_img;
    private BufferedImage frozen_obstacle_img;
    static ObstacleView instance;
    private BufferedImage firm_obs_1_img;
    private BufferedImage firm_obs_2_img;
    private BufferedImage firm_obs_3_img;
    private BufferedImage firm_obs_4_img;
    private BufferedImage firm_obs_5_img;


    private ObstacleView() {
    }

    public static ObstacleView getInstance() {
        if (instance == null) {
            instance = new ObstacleView();
            try {
                instance.fillImgs();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private void fillImgs() throws IOException {
        try {
            simple_obs_img = ImageIO.read(this.getClass().getResource("../assets/simple_obstacle.png"));
            firm_obs_1_img = ImageIO.read(this.getClass().getResource("../assets/firm_obstacle_1.png"));
            firm_obs_2_img = ImageIO.read(this.getClass().getResource("../assets/firm_obstacle_2.png"));
            firm_obs_3_img = ImageIO.read(this.getClass().getResource("../assets/firm_obstacle_3.png"));
            firm_obs_4_img = ImageIO.read(this.getClass().getResource("../assets/firm_obstacle_4.png"));
            firm_obs_5_img = ImageIO.read(this.getClass().getResource("../assets/firm_obstacle_5.png"));
            explosive_obs_img = ImageIO.read(this.getClass().getResource("../assets/explosive_obstacle.png"));
            gift_obs_img = ImageIO.read(this.getClass().getResource("../assets/gift_obstacle.png"));
            hollow_purple_img = ImageIO.read(this.getClass().getResource("../assets/purple.png"));
            frozen_obstacle_img = ImageIO.read(this.getClass().getResource("../assets/frozen_obstacle.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Length, thickness, and radius of the obstacles
        int obsLen = BuildModeScreen.FRAME_WIDTH/40;
        int obsThick = 40;
        int circRadius = 16;
        int hollowRadius = BuildModeScreen.FRAME_WIDTH/100;

        // Scale all images
        Image scaled;
        BufferedImage newBuffImage;
        Graphics2D bGr;

        // FROZEN OBSTACLE SCALE
        scaled = frozen_obstacle_img.getScaledInstance((int) (obsLen/1.4), (int) (obsThick/1.4),
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            frozen_obstacle_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        newBuffImage = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        bGr = newBuffImage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();
        frozen_obstacle_img = newBuffImage;


        // SIMPLE OBSTACLE SCALE
        scaled = simple_obs_img.getScaledInstance(obsLen, obsThick,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            simple_obs_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        newBuffImage = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        bGr = newBuffImage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();
        simple_obs_img = newBuffImage;

        // FIRM OBSTACLE SCALE
        scaled = firm_obs_1_img.getScaledInstance(obsLen, obsThick,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            firm_obs_1_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        newBuffImage= new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        bGr = newBuffImage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();
        firm_obs_1_img = newBuffImage;

        // FIRM2 OBSTACLE SCALE
        scaled = firm_obs_2_img.getScaledInstance(obsLen, obsThick,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            firm_obs_2_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        newBuffImage= new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        bGr = newBuffImage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();
        firm_obs_2_img = newBuffImage;

        // FIRM3 OBSTACLE SCALE
        scaled = firm_obs_3_img.getScaledInstance(obsLen, obsThick,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            firm_obs_3_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        newBuffImage= new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        bGr = newBuffImage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();
        firm_obs_3_img = newBuffImage;

        // FIRM4 OBSTACLE SCALE
        scaled = firm_obs_4_img.getScaledInstance(obsLen, obsThick,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            firm_obs_4_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        newBuffImage= new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        bGr = newBuffImage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();
        firm_obs_4_img = newBuffImage;

        // FIRM5 OBSTACLE SCALE
        scaled = firm_obs_5_img.getScaledInstance(obsLen, obsThick,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            firm_obs_5_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        newBuffImage= new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        bGr = newBuffImage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();
        firm_obs_5_img = newBuffImage;

        // EXPLOSIVE OBSTACLE SCALE
        scaled = explosive_obs_img.getScaledInstance(circRadius*2, circRadius*2,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            explosive_obs_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        newBuffImage= new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        bGr = newBuffImage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();
        explosive_obs_img = newBuffImage;

        // GIFT OBSTACLE SCALE
        scaled = gift_obs_img.getScaledInstance(obsLen, obsThick,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            gift_obs_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        newBuffImage= new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        bGr = newBuffImage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();
        gift_obs_img = newBuffImage;

        // HOLLOW PURPLE SCALE
        scaled = hollow_purple_img.getScaledInstance(hollowRadius*2, hollowRadius*2,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            hollow_purple_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        newBuffImage= new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        bGr = newBuffImage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();
        hollow_purple_img = newBuffImage;
    }

    @Override
    public void draw(Graphics2D g2d, DomainObject domainObject, int w, int h) {
        Obstacle obs = (Obstacle) domainObject;

        if (obs.isFrozen()) {
            g2d.drawImage(frozen_obstacle_img, obs.getPosVector().getX(), obs.getPosVector().getY(), null);
        }else if (obs instanceof WallMaria){
            g2d.drawImage(simple_obs_img, obs.getPosVector().getX(), obs.getPosVector().getY(), null);
        } else if (obs instanceof SteinsGate){
            if(obs.getHealth()==2){
                g2d.drawImage(firm_obs_2_img, obs.getPosVector().getX(), obs.getPosVector().getY(), null);
            }
            else if(obs.getHealth()==3){
                g2d.drawImage(firm_obs_3_img, obs.getPosVector().getX(), obs.getPosVector().getY(), null);
            }
            else if(obs.getHealth()==4){
                g2d.drawImage(firm_obs_4_img, obs.getPosVector().getX(), obs.getPosVector().getY(), null);
            }
            else if(obs.getHealth()==5){
                g2d.drawImage(firm_obs_5_img, obs.getPosVector().getX(), obs.getPosVector().getY(), null);
            }
            else{
                g2d.drawImage(firm_obs_1_img, obs.getPosVector().getX(), obs.getPosVector().getY(), null);
            }
            }
        else if (obs instanceof PandorasBox){
            g2d.drawImage(explosive_obs_img, obs.getPosVector().getX(), obs.getPosVector().getY(), null);
        } else if (obs instanceof GiftOfUranus){
            g2d.drawImage(gift_obs_img, obs.getPosVector().getX(), obs.getPosVector().getY(), null);
        } else {
            g2d.drawImage(hollow_purple_img, obs.getPosVector().getX(), obs.getPosVector().getY(), null);
        }

    }
}
