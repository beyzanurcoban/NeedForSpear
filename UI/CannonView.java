package UI;

import domain.Cannon;
import domain.DomainObject;
import domain.Game;
import domain.Paddle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CannonView implements Drawable {

    private BufferedImage paddle_img;
    private static CannonView instance;

    private CannonView() {
    }

    public static CannonView getInstance() {
        if (instance == null) {
            instance = new CannonView();
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
            paddle_img = ImageIO.read(this.getClass().getResource("../assets/balba.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // scale to unit length L
        //int paddleLen = BuildModeScreen.FRAME_WIDTH/10;
        int cannonLen = 50;
        int cannonThick = 30;
        Image scaled = paddle_img.getScaledInstance(cannonLen, cannonThick,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            paddle_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        BufferedImage new_bimage = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = new_bimage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();

        paddle_img = new_bimage;
    }

    @Override
    public void draw(Graphics2D g2d, DomainObject domainObject, int width, int height) throws IOException {
        fillImgs();
        Cannon cannon = (Cannon) domainObject;
        int x = cannon.getPosVector().getX();
        int y = cannon.getPosVector().getY();
        int length = cannon.getLength();
        int h = cannon.getHeight();
        double angle = cannon.getAngle();

        AffineTransform at = AffineTransform.getTranslateInstance(x,y);
        at.rotate(Math.toRadians(angle),length/2,h /2);

        g2d.drawImage(paddle_img, at, null);

    }


}
