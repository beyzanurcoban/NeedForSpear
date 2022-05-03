
package testing;

import domain.*;
import domain.obstacle.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.PosVector;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CheckToDeleteTest{
    Ball ball;
    Paddle paddle;
    DomainObject wallmaria, steinsgate, giftofuranus, pandorasbox, remainPiece;
    ArrayList<DomainObject> objList;
    ArrayList<Box> boxes;
    ArrayList<RemainingPieces> remains;
    CollisionChecker CS;
    Game game;
    Layout layout;
    HashMap<Obstacle, PosVector> obsList;

    @BeforeEach
    void setUp() {
        System.out.println("Running: Set Up");
        game = Game.getInstance();

        ball = new Ball();
        paddle = new Paddle(1368, 766);
        wallmaria = new WallMaria(500,500);
        steinsgate = new SteinsGate(400,400);
        giftofuranus = new GiftOfUranus(400,400);
        pandorasbox = new PandorasBox(500,500);
        remainPiece = new RemainingPieces(500,500);

        game.setBall(ball);
        game.setPaddle(paddle);

        boxes = new ArrayList<>();
        remains = new ArrayList<RemainingPieces>();

        layout = new Layout();
        Layout.setObstaclePositions(new HashMap<Obstacle, PosVector>());
        obsList = Layout.getObstaclePositions();
        CS = CollisionChecker.getInstance();

        objList = new ArrayList<>();
        objList.add(ball);
        objList.add(paddle);
        objList.add(wallmaria);
        objList.add(steinsgate);
        objList.add(giftofuranus);
        objList.add(remainPiece);



    }

    @AfterEach
    void tearDown() {
        System.out.println("Running: Teardown");
        for (DomainObject obj : objList) {
            obj = null;
        }
        CS = null;
        obsList.clear();
        layout = null;
        game.getDomainObjectArr().clear();
        remains = null;
    }


    @Test
    void removeWallMaria() {
        ball.setPosVector(new PosVector(500, 500));
        wallmaria.setPosVector(new PosVector(500, 500));
        obsList.put((Obstacle) wallmaria,wallmaria.getPosVector());
        CS.ChecktoDelete();
        assertTrue(game.getDomainObjectArr().isEmpty());
    }


    @Test
    void removeGiftOfUranusAddBox() {
        ball.setPosVector(new PosVector(500, 500));
        giftofuranus.setPosVector(new PosVector(500, 500));
        obsList.put((Obstacle) giftofuranus,giftofuranus.getPosVector());
        CS.ChecktoDelete();
        assertEquals(
                new Box(0,0).getClass(),
                game.getDomainObjectArr().get(0).getClass()
                );
        assertEquals(1,game.getDomainObjectArr().size());

    }
    @Test
    void removePandorasBoxAddRemains() {
        ball.setPosVector(new PosVector(500, 500));
        pandorasbox.setPosVector(new PosVector(500, 500));
        obsList.put((Obstacle) pandorasbox,pandorasbox.getPosVector());
        CS.ChecktoDelete();
        assertEquals(
                new RemainingPieces(0,0).getClass(),
                game.getDomainObjectArr().get(0).getClass()
              );
        assertEquals(1,game.getDomainObjectArr().size());

    }

    @Test
    void checkChangeSubsForRemainingPieceCol() {
        paddle.setPosVector(new PosVector(20, 20));
        remainPiece.setPosVector(new PosVector(20, 20));
        CS.getRemainingPieces().add((RemainingPieces) remainPiece);
        CS.ChecktoDelete();
        assertEquals(2,game.gameState.getPlayer().getChance_points());

    }

    @Test
    void ballreflectFromVertical() {
        ball.setPosVector(new PosVector(500, 505));
        wallmaria.setPosVector(new PosVector(500, 500));
        obsList.put((Obstacle) wallmaria,wallmaria.getPosVector());

        int beforeXVelocity = game.getBall().xVelocity;
        int beforeYVelocity = game.getBall().yVelocity;

        CS.ChecktoDelete();

        int afterXVelocity = game.getBall().xVelocity;
        int afterYVelocity = game.getBall().yVelocity;

        assertEquals(beforeXVelocity,afterXVelocity);
        assertEquals(beforeYVelocity,-1 * afterYVelocity);

    }








}

