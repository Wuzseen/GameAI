package utility;

import engine.Game;
import engine.GameObject;
import engine.RotatedRectangle;

public class Raycast {
	static double raycastCrawlDistance = .1; // larger number = faster performance = less quality raycast
	private double distance;
	private GameObject other;
	
	public Raycast(Game game, GameObject srcObj, Vector2 direction, double distance, RotatedRectangle source, GameObject ignore) {
		this.distance = raycast(game, srcObj, direction, distance, source, ignore);
	}
	
	public Boolean isHit() {
		return getDistance() >= 0;
	}
	
	public static Raycast doRaycast(Game game, GameObject srcObj, Vector2 direction, double distance, RotatedRectangle source, GameObject ignoreCar) {		
		return new Raycast(game, srcObj, direction, distance, source, ignoreCar);
	}
	
	
	// -1 is sentinel value
	public double raycast(Game game, GameObject srcObj, Vector2 direction, double distance, RotatedRectangle source, GameObject ignoreCar) {
		RotatedRectangle srcCpy = new RotatedRectangle(source);
		Vector2 startPosition = srcObj.position();
		
		double distTraveled = 0;
		do {
			Vector2 newPosition = startPosition.add(direction.scalarMultiply(distTraveled));
			srcCpy.setPosition(newPosition.get_x(), newPosition.get_y());
			GameObject coll = game.collision(srcCpy, srcObj, ignoreCar);
			if(coll != null) {
				//System.out.println(distTraveled);
				this.other = coll;
				return distTraveled;
			}
			distTraveled += raycastCrawlDistance;
		} while(distTraveled <= distance);
		
		return -1;
	}
	
	public GameObject getOther() {
		return this.other;
	}

	public double getDistance() {
		return this.distance;
	}
}
