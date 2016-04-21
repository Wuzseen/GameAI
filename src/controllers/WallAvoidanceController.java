package controllers;

import engine.Car;
import utility.*;
import engine.Game;
import engine.GameObject;
import engine.RotatedRectangle;

public class WallAvoidanceController extends Controller {
	GameObject _tar;
	double steer = 0;
	double throttle = 0;
	double brake = 0;
	
	double steerVal = 9;
	double maxThrottle = 2;
	double maxAcceleration = 200;
	double distForMax = 200;
	double breakDistance = 20;
	
	double currentAlpha = 0;
	double targetAlpha = 0;
	
	double lastDelta = 0;
	
	double raycastDistance = 80;
	
	public WallAvoidanceController(GameObject target)
	{
		this._tar = target;
	}
	
	Vector2 Seek(GameObject character, GameObject E) {
        Vector2 thisPosition = character.position();
        Vector2 targetPosition = E.position();
        Vector2 D = targetPosition.subtract(thisPosition);
        Vector2 dnorm = D.normal(); // direction we want to steer towards
        
        return dnorm.scalarMultiply(maxAcceleration * MathUtil.InverseLerp(targetPosition.distance(thisPosition), 0, distForMax));
	}
	
    public void update(Car subject, Game game, double delta_t, double[] controlVariables) {
        Vector2 seek = Seek(subject, this._tar); // A
        
        RotatedRectangle startRectangle = subject.getCollisionBox();
        
        Vector2 forward = subject.direction();
        Vector2 right = Vector2.rotateVector(forward, MathUtil.deg2Rad(-45));
        Vector2 left = Vector2.rotateVector(forward, MathUtil.deg2Rad(45));
        
        Raycast forwardRay = Raycast.doRaycast(game, subject, forward, raycastDistance, startRectangle);
        Raycast rightRay = Raycast.doRaycast(game, subject, right, raycastDistance, startRectangle);
        Raycast leftRay = Raycast.doRaycast(game, subject, left, raycastDistance, startRectangle);
        if(forwardRay.isHit() || rightRay.isHit() || leftRay.isHit()) {
        	// break a little to avoid a wall
        	this.brake += .1 * delta_t;
            if(rightRay.isHit()) {
            	// don't turn right if the right is occupied
            	steerLeft(delta_t);
            } else {
            	// if the right is hit, turn left. If both are hit, we have to 180 so we might as well turn left.
            	steerRight(delta_t);
                //Raycast leftRay = Raycast.doRaycast(game, subject, left, raycastDistance, startRectangle);
            }
        }
        else {
        
	        double distance = subject.position().distance(this._tar.position());
	        if(distance < breakDistance) {
	        	this.brake += .1 * delta_t;
	        	this.throttle = 0;
	        } else if(distance >= breakDistance) {
	        	this.throttle = maxThrottle * MathUtil.InverseLerp(distance, 0, this.distForMax);
	        }
	        
	        // Convert Rad 2 Deg for mental help!
	        targetAlpha = seek.alpha() * 180 / Math.PI;
	        currentAlpha = subject.getAngle() * 180 / Math.PI;
	        
	        double alphaDelta = targetAlpha - currentAlpha; // if 0 -> moving in correction direction
	        if(alphaDelta < -180) {
	        	alphaDelta += 360;
	        }
	        if(alphaDelta > 180) {
	        	alphaDelta -= 360;
	        }
	        if(Math.abs(alphaDelta) < 1) { // close enough...
	        	this.steer = 0;
	        }
	        else if(alphaDelta > 0) {
	        	steerRight(delta_t);
	        }
	        else {
	        	steerLeft(delta_t);
	        }
	        
	        lastDelta = alphaDelta;
    	
        }
        controlVariables[VARIABLE_STEERING] = this.steer;
        controlVariables[VARIABLE_THROTTLE] = this.throttle;
        controlVariables[VARIABLE_BRAKE] = this.brake;
    }
    
    void steerRight(double delta_t) {
    	if(this.steer < 0) {
    		this.steer = 0;
    	}
    	this.steer += steerVal * delta_t;	
    }
    
    void steerLeft(double delta_t) {
    	if(this.steer > 0) {
    		this.steer = 0;
    	}
    	this.steer -= steerVal * delta_t;	
    }
}
