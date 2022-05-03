package testing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.PosVector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PosVectorTest {

    PosVector pv, pv2, pv3;
    private final int FRAME_WIDTH = 1368;
    private final int FRAME_HEIGHT = 766;

    @BeforeEach
    void setUp() {
        pv = new PosVector(300,300);
        pv2 = new PosVector(100,100);
        pv3 = new PosVector(11,7);
    }


    @AfterEach
    void tearDown() {
        pv = null;
        pv2 = null;
    }

    @Test
    void addVectorTrue() {
        pv.addVector(pv2);
        assertEquals(400, pv.getX());
        assertEquals(400, pv.getY());
        assertTrue(pv.repOK());
    }

    @Test
    void addVectorNull() {
        pv2 = null;
        assertThrows(Exception.class, ()-> {
            pv.addVector(pv2);});
        assertTrue(pv.repOK());
    }

    @Test
    void addVectorFalse() {
        int old_x = pv.getX();
        int old_y = pv.getY();
        pv.addVector(pv2);
        assertTrue(pv.repOK());
        assertNotEquals(old_x, pv.getX());
        assertNotEquals(old_y, pv.getY());
    }

    @Test
    void halfVelocityTrue() {
        pv.halfVelocity();
        assertTrue(pv.repOK());
        assertEquals(150,pv.getX());
        assertEquals(150, pv.getY());
    }

    @Test
    void halfVelocityFalse(){
        pv3.halfVelocity();
        assertTrue(pv3.repOK());
        assertNotEquals(5.5, pv3.getX());
        assertNotEquals(3.5, pv3.getY());
    }


    @Test
    void getThetaTrue() {
        pv.getTheta(pv2);
        assertEquals(pv.getTheta(pv2), 0.7853981633974483);
        assertTrue(pv.repOK());
    }

    @Test
    void getThetaFalse() {
        int dx = pv.getX();
        int dy = pv.getY();
        pv.getTheta(pv2);
        assertTrue(pv.repOK());
        assertNotEquals(pv.getTheta(pv2), dy/dx );
    }


    @Test
    void getThetaZeroDivZeroNaN() {
        pv2.setX(300);
        pv2.setY(300);
        assertEquals(Double.NaN,pv.getTheta(pv2));
    }

    @Test
    void getX() {
        assertEquals(pv.getX(),300);
        assertEquals(pv2.getX(),100);
    }

    @Test
    void setXTest(){
        pv2.setX(-45);
        assertEquals(pv2.repOK(),false);

        pv2.setX(FRAME_WIDTH+1);
        assertEquals(pv2.repOK(),false);

        pv.setX(97);
        assertEquals(pv.repOK(),true);

    }

    @Test
    void getY() {
        assertEquals(pv.getY(),300);
        assertEquals(pv2.getY(),100);
    }

    @Test
    void setYTest(){
        pv2.setY(-67);
        assertEquals(pv2.repOK(),false);
        pv.setY(97);
        assertEquals(pv.repOK(),true);
    }

}