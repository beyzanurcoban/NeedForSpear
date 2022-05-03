package domain.strategy;

public class ExplosiveDestroyBehaviour implements DestroyBehaviour {
	public void destroy() {
		System.out.println("Obstacle has exploded, the pieces are falling down.");
	}
}
