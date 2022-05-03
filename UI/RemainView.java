package UI;

import domain.Box;
import domain.DomainObject;
import domain.RemainingPieces;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RemainView implements Drawable  {
    private BufferedImage remain_img;
    private int remainwidth = 30;
    protected boolean isDrop = false;

    public RemainView() throws IOException {
        this.remain_img = fillImgs();

    }


    private BufferedImage fillImgs() throws IOException{
        try {
            remain_img = ImageIO.read(this.getClass().getResource("../assets/piece.png"));
        } catch (Exception e) {
            e.printStackTrace();

        }

        // scale to unit length L
        Image scaled = remain_img.getScaledInstance(remainwidth, remainwidth,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            remain_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        BufferedImage new_bimage = new BufferedImage(50, 40,
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = new_bimage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();


        return new_bimage;
    }

    @Override
    public void draw(Graphics2D g2d, DomainObject domainObject, int width, int height) {
        // TODO Auto-generated method stub
        RemainingPieces remains = (RemainingPieces) domainObject;

        g2d.drawImage(remain_img, remains.getPosVector().getX(), remains.getPosVector().getY(), null);

    }



}

