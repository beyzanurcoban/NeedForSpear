package UI;

import domain.DomainObject;
import domain.Ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BallView implements Drawable {

    private BufferedImage Ball_img;
    private static BallView instance;
    private int ballRadius = 20;

    private BallView() {
    }

    public static BallView getInstance() {
        if (instance == null) {
            instance = new BallView();
            try {
                instance.fillImgs();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }


    private void fillImgs() throws IOException{
        try {
            Ball_img = ImageIO.read(this.getClass().getResource("../assets/Pokeball.png"));
        } catch (Exception e) {
            e.printStackTrace();

        }

        // scale to unit length L
        Image scaled = Ball_img.getScaledInstance(ballRadius, ballRadius,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            Ball_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        BufferedImage new_bimage = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = new_bimage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();

        Ball_img = new_bimage;
    }

    @Override
    public void draw(Graphics2D g2d, DomainObject domainObject, int width, int height) {
        Ball Ball = (Ball) domainObject;

        g2d.drawImage(Ball_img, Ball.getPosVector().getX(), Ball.getPosVector().getY(), null);

    }


}
