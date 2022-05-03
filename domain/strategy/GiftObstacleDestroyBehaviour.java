package domain.strategy;

import domain.strategy.DestroyBehaviour;

public class GiftObstacleDestroyBehaviour implements DestroyBehaviour{

	public void destroy() {
		System.out.println("Gift is dropped");
	}

}
