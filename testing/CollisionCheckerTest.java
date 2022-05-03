package testing;

import domain.Ball;
import domain.CollisionChecker;
import domain.DomainObject;
import domain.Paddle;
import domain.obstacle.GiftOfUranus;
import domain.obstacle.SteinsGate;
import domain.obstacle.WallMaria;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.PosVector;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
// edit
class CollisionCheckerTest {
    Ball ball;
    Paddle paddle;
    DomainObject wallmaria, steinsgate, giftofuranus;
    ArrayList<DomainObject> objList;
    CollisionChecker checker = CollisionChecker.getInstance();



    @BeforeEach
    void setUp() {
        System.out.println("Running: Set Up");
        ball = new Ball();
        paddle = new Paddle(1368, 766);
        wallmaria = new WallMaria(400,400);
        steinsgate = new SteinsGate(400,400);
        giftofuranus = new GiftOfUranus(400,400);
        objList = new ArrayList<>();
        objList.add(ball);
        objList.add(paddle);
        objList.add(wallmaria);
        objList.add(steinsgate);
        objList.add(giftofuranus);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Running: Teardown");
        for (DomainObject obj : objList) {
            obj = null;
        }
    }

    @Test
    void checkPaddleBallCollisionTrue1() {
        ball.setPosVector(new PosVector(368, 1105));
        paddle.setPosVector(new PosVector(360, 1100));
        assertTrue(checker.checkPaddleBallCollision(ball, paddle));
    }

    @Test
    void checkPaddleBallCollisionTrue2() {
        ball.setPosVector(new PosVector(366, 1101));
        paddle.setPosVector(new PosVector(360, 1100));
        assertTrue(checker.checkPaddleBallCollision(ball, paddle));
    }

    @Test
    void checkPaddleBallCollisionTrue3() {
        ball.setPosVector(new PosVector(370, 1010));
        paddle.setPosVector(new PosVector(360, 1000));
        assertTrue(checker.checkPaddleBallCollision(ball, paddle));
    }


    @Test
    void checkPaddleBallCollisionFalse1() {
        ball.setPosVector(new PosVector(370, 800));
        paddle.setPosVector(new PosVector(360, 1000));
        assertFalse(checker.checkPaddleBallCollision(ball, paddle));
    }

    @Test
    void checkPaddleBallCollisionFalse2() {
        ball.setPosVector(new PosVector(100, 1010));
        paddle.setPosVector(new PosVector(360, 1000));
        assertFalse(checker.checkPaddleBallCollision(ball, paddle));
    }

    @Test
    void checkCollisionTrue1() {
        ball.setPosVector(new PosVector(400,400));
        wallmaria.setPosVector(new PosVector(400,400));
        assertTrue(checker.checkCollision(ball,wallmaria));
    }

    @Test
    void checkCollisionFalse1() {
        ball.setPosVector(new PosVector(400,400));
        wallmaria.setPosVector(new PosVector(300,400));
        assertFalse(checker.checkCollision(ball,wallmaria));
    }

    @Test
    void checkCollisionTrue2() {
        ball.setPosVector(new PosVector(400,400));
        steinsgate.setPosVector(new PosVector(400,400));
        assertTrue(checker.checkCollision(ball,steinsgate));
    }

    @Test
    void checkCollisionFalse2() {
        ball.setPosVector(new PosVector(400,400));
        steinsgate.setPosVector(new PosVector(400,500));
        assertFalse(checker.checkCollision(ball,steinsgate));
    }

}