package domain.obstacle;

import domain.*;
import domain.path.CircularPath;
import domain.path.IPathBehaviour;
import domain.path.StraightVerticalBFPath;
import domain.strategy.DestroyBehaviour;
import util.PosVector;

public abstract class Obstacle extends DomainObject {

	static final int FRAME_WIDTH = 1368;


	public boolean is_rotating;
	public boolean is_moving;
	protected int health;
	protected DestroyBehaviour destroyBehaviour;
	protected IPathBehaviour pathBehaviour;
	protected String type;
	private int height, width;
	protected Box box;
	public PosVector pos;
	protected RemainingPieces pieces;
	public boolean isEffectScore;
	protected boolean frozen;
	protected int endLeft = -1;
	protected int endRight = -1;

	public Obstacle(int xPos, int yPos) {
		this.pos = new PosVector(xPos, yPos);
		this.health = 1;
		this.is_rotating = false;
		this.is_moving = false;
		this.width = FRAME_WIDTH / 50;
		this.height = 20;
		this.isEffectScore =true;
		this.frozen = false;
	}

	public String getType() {
		return type;
	}

	public boolean isFrozen() {
		return this.frozen;
	}

	public void setIsFrozen(boolean b) {
		 this.frozen = b;
	}

	public PosVector getPosVector() {
		return this.pos;
	}

	public boolean getHit() {
		this.health -= 1;

		if (this.health <= 0 ) {
			this.destroy();
			int o = Game.getInstance().getOldScore();
			if(isEffectScore && Game.getInstance().isStarted) {
				int neww = Game.getInstance().updateScore(o);
				Game.getInstance().setScore(neww);
			}
			return true;
		} else {
			return false;
		}
	}

	public void setPathBehaviour(IPathBehaviour pb){
		this.pathBehaviour = pb;
		pb.initialPath(this);

		if (pb instanceof StraightVerticalBFPath){
			endLeft = ((StraightVerticalBFPath) pb).getEndLeft();
			endRight = ((StraightVerticalBFPath) pb).getEndRight();

		}

	}

	public IPathBehaviour getPathBehaviour() {
		return pathBehaviour;
	}

	public boolean getHitWhenFrozen(boolean unstoppableBall) {
		if (!unstoppableBall) return false;
		else {
			return getHit();
		}
	}

	public void destroy() {
		this.destroyBehaviour.destroy();
	}

	@Override
	public String toString() {
		return "Obstacle{" +
				"pos=" + pos.toString();
	}

	public PosVector getPos() {
		return pos;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}


	public int getHealth() {
		return health;
	}

	public Box getBox() {
		return this.box;
	}

	public void setPosVector(PosVector pos) {
		this.pos = pos;
	}

	public RemainingPieces getRemains() {
		return this.pieces;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public void move(){
		PosVector pos = pathBehaviour.nextPosition();
		setPosVector(pos);
	}

	public int getEndLeft() {
		return endLeft;
	}

	public int getEndRight() {
		return endRight;
	}
}
