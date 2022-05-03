package domain.obstacle;

import domain.Box;
import domain.strategy.GiftObstacleDestroyBehaviour;

import java.util.ArrayList;

public class GiftOfUranus extends Obstacle {

	public GiftOfUranus(int xPos, int yPos) {
		super(xPos, yPos);
		this.destroyBehaviour = new GiftObstacleDestroyBehaviour();
		this.type = "GiftOfUranus";
		box = new Box(xPos, yPos);
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
