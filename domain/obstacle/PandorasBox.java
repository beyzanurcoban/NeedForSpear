package domain.obstacle;

import domain.RemainingPieces;
import domain.strategy.ExplosiveDestroyBehaviour;

import java.util.ArrayList;

public class PandorasBox extends Obstacle {

	public PandorasBox(int xPos, int yPos) {
		super(xPos, yPos);
		this.destroyBehaviour = new ExplosiveDestroyBehaviour();
		this.is_rotating = true;
		this.type = "PandorasBox";
		pieces = new RemainingPieces(xPos, yPos);
	}


	public ArrayList<String> makeList() {

		return null;
	}

	@Override
	protected void updatePosition(int x, int y) {

	}

	@Override
	public String toString() {
		return super.toString() + ", typeVariable=" + getType();
	}
}
