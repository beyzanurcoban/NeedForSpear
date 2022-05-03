package UI;

import domain.DomainObject;
import domain.Game;
import domain.Paddle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MagicalAbilityView implements DrawableMagical {
    public static final int FRAME_WIDTH = 1368;
    public static final int FRAME_HEIGHT = 766;

    private BufferedImage expansion_img;
    private BufferedImage rocket_img;
    private BufferedImage unstop_img;
    private BufferedImage chance_img;
    private static MagicalAbilityView instance;

    private static int yOffset = 70;
    private static int xOffset = 175;

    private static int dimension = 50;

    private MagicalAbilityView() {
    }

    public static MagicalAbilityView getInstance() {
        if (instance == null) {
            instance = new MagicalAbilityView();
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
            unstop_img = ImageIO.read(this.getClass().getResource("../assets/paddle1.png"));
            rocket_img = ImageIO.read(this.getClass().getResource("../assets/paddle1.png"));
            chance_img = ImageIO.read(this.getClass().getResource("../assets/paddle1.png"));
            expansion_img = ImageIO.read(this.getClass().getResource("../assets/paddle1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // UNSTOPPABLE ABILITY
        Image scaled = unstop_img.getScaledInstance(dimension, dimension,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            unstop_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        BufferedImage new_bimage = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = new_bimage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();

        unstop_img = new_bimage;

        // ROCKET ABILITY
        scaled = rocket_img.getScaledInstance(dimension, dimension,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            rocket_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        new_bimage = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        bGr = new_bimage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();

        rocket_img = new_bimage;

        // CHANCE GIVING ABILITY
        scaled = chance_img.getScaledInstance(dimension, dimension,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            chance_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        new_bimage = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        bGr = new_bimage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();

        chance_img = new_bimage;

        // EXPANSION ABILITY
        scaled = expansion_img.getScaledInstance(dimension, dimension,
                BufferedImage.SCALE_SMOOTH);
        if (scaled instanceof BufferedImage)
            expansion_img = (BufferedImage) scaled;

        // Create a buffered image with transparency
        new_bimage = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        bGr = new_bimage.createGraphics();
        bGr.drawImage(scaled, 0, 0, null);
        bGr.dispose();

        expansion_img = new_bimage;
    }

    @Override
    public void draw(Graphics2D g2d) throws IOException {
        fillImgs();
        int x = FRAME_WIDTH - xOffset + 25;
        int y = yOffset + 50;

        int yoff = 40;

        g2d.drawImage(unstop_img, x, y, null);
        g2d.drawImage(rocket_img, x, y+dimension+yoff, null);
        g2d.drawImage(chance_img, x, y+dimension*2+yoff*2, null);
        g2d.drawImage(expansion_img, x, y+dimension*3+yoff*3, null);
    }
}
