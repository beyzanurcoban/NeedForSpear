package UI;

import domain.Bullet;
import domain.DomainObject;
import domain.Ball;
import domain.obstacle.Obstacle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BulletView implements Drawable {

    private BufferedImage Bullet_img;
    private static BulletView instance;
    private int bulletradius = 20;

    private BulletView() {
    }

    public static BulletView getInstance() {
        if (instance == null) {
            instance = new BulletView();
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
            Bullet_img = ImageIO.read(this.getClass().getResource("../assets/lily.png"));
        } catch (Exception e) {
            e.printStackTrace();

        }

        // scale to unit length L
        Image scaled = Bullet_img.getScaledInstance(bulletradius, bulletradius,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            Bullet_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        BufferedImage new_bimage = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = new_bimage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();

        Bullet_img = new_bimage;
    }

    @Override
    public void draw(Graphics2D g2d, DomainObject domainObject, int width, int height) {
        Bullet bullet = (Bullet) domainObject;

        g2d.drawImage(Bullet_img, bullet.getPosVector().getX(), bullet.getPosVector().getY(), null);

    }


}
