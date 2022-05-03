package domain.strategy;

import domain.strategy.DestroyBehaviour;

public class RegularDestroyBehaviour implements DestroyBehaviour {

	public void destroy() {
		System.out.println("The regular obstacle has been destroyed");
	}
}
