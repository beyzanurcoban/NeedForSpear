package domain.obstacle;

import domain.strategy.RegularDestroyBehaviour;

import java.util.ArrayList;
import java.util.Random;

public class SteinsGate extends Obstacle {

	private int MAX_HEALTH = 5;
	public SteinsGate(int xPos, int yPos) {
		super(xPos, yPos);
		this.destroyBehaviour = new RegularDestroyBehaviour();
		this.type = "SteinsGate";
		setRandomHealth();

	}

	public ArrayList<String> makeList() {
		return null;
	}

	@Override
	protected void updatePosition(int x, int y) {

	}


	private void setRandomHealth(){
		Random rnd = new Random();
		this.health = rnd.nextInt(MAX_HEALTH);

	}

	@Override
	public String toString() {
		return super.toString() + ", typeVariable=" + getType();
	}
}
