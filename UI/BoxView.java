package UI;

import domain.Box;
import domain.DomainObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BoxView implements Drawable {

    private BufferedImage box_img;
    private int boxWidth = 30;

    public BoxView() {
        this.box_img = fillImgs();
    }

    private BufferedImage fillImgs() {
        // TODO Auto-generated method stub
        try {
            box_img = ImageIO.read(this.getClass().getResource("../assets/box.png"));
        } catch (Exception e) {
            e.printStackTrace();

        }

        // scale to unit length L
        Image scaled = box_img.getScaledInstance(boxWidth, boxWidth,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            box_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        BufferedImage new_bimage = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = new_bimage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();

        return new_bimage;
    }

    @Override
    public void draw(Graphics2D g2d, DomainObject domainObject, int width, int height) {
        Box box = (Box) domainObject;

        g2d.drawImage(box_img, box.getPosVector().getX(), box.getPosVector().getY(), null);

    }



}
