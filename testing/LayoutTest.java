package testing;

import domain.Box;
import domain.DomainObject;
import domain.obstacle.Obstacle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import domain.Layout;
import domain.Game;
import domain.RemainingPieces;

import static org.junit.jupiter.api.Assertions.*;

public class LayoutTest {
    private Integer[] obsCounts;
    private Obstacle obstacle;
    private Obstacle obstacle2;
    private static Game gameInstance;
    private Layout layout;
    private Box box;
    private RemainingPieces remain;
    private double scaleToRunMode = 1;
    public static int layoutWidth;
    public static int layoutHeight;

    @BeforeAll
    static void init(){
        gameInstance = Game.getInstance();
        layoutWidth = 1368;
        layoutHeight = 766;
    }

    @BeforeEach
    void setUp(){
        System.out.println("Running: Set Up");
        gameInstance = Game.getInstance();
        gameInstance.setDomainObjectArr(new ArrayList<>());
    }

    @AfterEach
    void tearDown(){
        System.out.println("Running: Tear Down");
        obsCounts = new Integer[]{};
        layout = new Layout();
    }

    // Black Box Test
    @Test
    void testSetLayout_validObsCounts(){
        obsCounts = new Integer[]{3, 4, 5, 6};
        layout = new Layout(obsCounts[0], obsCounts[1], obsCounts[2], obsCounts[3], layoutWidth, layoutHeight,scaleToRunMode);
        layout.setLayout();
        assertEquals(18,layout.getObstaclePositions().size());
    }

    // Black Box Test
    @Test
    void testSetLayout_invalidObsCounts(){
        obsCounts = new Integer[]{3, 4, 5, -4};
        layout = new Layout(obsCounts[0], obsCounts[1], obsCounts[2], obsCounts[3], layoutWidth, layoutHeight,scaleToRunMode);
        layout.setLayout();
        assertEquals(12,layout.getObstaclePositions().size());
    }

    // Black Box Test
    @Test
    void testSetLayout_emptyLayoutAttributes(){
        layout = new Layout();
        layout.setLayout();
        assertTrue(gameInstance.getDomainObjectArr().isEmpty());
    }

    // Glass Box - 0 iteration
    @Test
    void testSetLayout_emptyObsCounts_emptyDomainObjArr(){
        obsCounts = new Integer[]{0,0,0,0};
        layout = new Layout(obsCounts[0], obsCounts[1], obsCounts[2], obsCounts[3], layoutWidth, layoutHeight,scaleToRunMode);
        layout.setLayout();
        assertTrue(gameInstance.getDomainObjectArr().isEmpty());
    }

    // Glass Box - 1 iteration
    @Test
    void testSetLayout_oneObs(){
        obsCounts = new Integer[]{0,0,0,1}; // gift obstacle only
        layout = new Layout(obsCounts[0], obsCounts[1], obsCounts[2], obsCounts[3], layoutWidth, layoutHeight,scaleToRunMode);
        layout.setLayout();
        // Visual After SetLayout: DomainObjArr = [giftObs, box, null]
        obstacle = (Obstacle) gameInstance.getDomainObjectArr().get(0);
        box = new Box(obstacle.getPosVector().x, obstacle.getPosVector().y);
        assertEquals(box.getPosVector().x, gameInstance.getDomainObjectArr().get(1).getPosVector().x);
    }

    // Glass Box - 2 iteration
    @Test
    void testSetLayout_twoObs(){
        obsCounts = new Integer[]{0,0,1,1}; // 1 explosive and 1 gift obstacle
        layout = new Layout(obsCounts[0], obsCounts[1], obsCounts[2], obsCounts[3], layoutWidth, layoutHeight,1);
        layout.setLayout();
        // Visual After SetLayout: DomainObjArr = [pandoraObs, null, remain, giftObs, box, null]
        obstacle = (Obstacle) gameInstance.getDomainObjectArr().get(0);
        remain = new RemainingPieces(obstacle.getPosVector().x, obstacle.getPosVector().y);
        obstacle2 = (Obstacle) gameInstance.getDomainObjectArr().get(3);
        box = new Box(obstacle2.getPosVector().x, obstacle.getPosVector().y);

        assertEquals(remain.getPosVector().y, gameInstance.getDomainObjectArr().get(2).getPosVector().y);
        assertEquals(box.getPosVector().x, gameInstance.getDomainObjectArr().get(4).getPosVector().x);
    }

}
