package controllers;

import engine.Car;
import utility.*;
import engine.Game;
import engine.GameObject;

public class ArriveController extends Controller {
	GameObject _tar;
	double steer = 0;
	double throttle = 0;
	double brake = 0;
	
	double steerVal = 5;
	double maxThrottle = 2;
	double maxSpeed = 200;
	double maxAcceleration = 50;
	double distForMax = 200;
	double targetRadius = 20;
	double slowRadius = 200;
	
	double maxBrake = 22;
	
	double currentAlpha = 0;
	double targetAlpha = 0;
	
	public ArriveController(GameObject target)
	{
		this._tar = target;
	}
	
	Vector2 arrive(Car character, GameObject E, double delta_t) {
        Vector2 thisPosition = character.position();
        Vector2 targetPosition = E.position();
        Vector2 D = targetPosition.subtract(thisPosition);
        double dist = D.magnitude();
        double targetSpeed = maxSpeed;
        if(dist < targetRadius) {
        	return new Vector2(0,0);
        }
        if(dist <= slowRadius) {
        	targetSpeed = maxSpeed * dist / slowRadius;
        }
        Vector2 dnorm = D.normal(); // direction we want to steer towards
        Vector2 targetVelocity = dnorm.scalarMultiply(targetSpeed);
        Vector2 A = (targetVelocity.subtract(character.velocity())).scalarMultiply(1 / delta_t);
        if(A.magnitude() > maxAcceleration) {
        	A = A.normal().scalarMultiply(maxAcceleration);
        }
        return A;
	}
	
	Boolean arrived = false;
	
    public void update(Car subject, Game game, double delta_t, double[] controlVariables) {
        Vector2 arrive = arrive(subject, this._tar, delta_t); // A
        double dist = subject.position().distance(this._tar.position());
        double mag = arrive.magnitude();
        
        if(mag == 0) { // stop!
        	double carSpeed = subject.getSpeed();
        	double acceleration = carSpeed / delta_t;
        	// speed = acceleration * delta_t; acceleration = 100 * throttle - brake * 250
        	this.throttle = -acceleration / 100;
        	this.brake = 0;
        }
        else if(dist < this.slowRadius) {
        	this.brake = (1 - (dist / this.slowRadius)) * maxBrake;
        	this.throttle = mag;
        }
        else {
        	this.throttle = mag;
        	this.brake = 0;
        }
        
        // Convert Rad 2 Deg for mental help!
        targetAlpha = MathUtil.rad2Deg(arrive.alpha());//arrive.alpha() * 180 / Math.PI;
        currentAlpha = MathUtil.rad2Deg(subject.getAngle());//subject.getAngle() * 180 / Math.PI;
        
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
        	if(this.steer < 0) {
        		this.steer = 0;
        	}
        	this.steer += steerVal * delta_t;
        }
        else {
        	if(this.steer > 0) {
        		this.steer = 0;
        	}
        	this.steer -= steerVal * delta_t;
        }

        controlVariables[VARIABLE_STEERING] = this.steer;
        controlVariables[VARIABLE_THROTTLE] = this.throttle;
        controlVariables[VARIABLE_BRAKE] = this.brake;
    }
}
