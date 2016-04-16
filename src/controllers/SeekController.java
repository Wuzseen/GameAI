package controllers;

import engine.Car;
import utility.Vector2;
import engine.Game;
import engine.GameObject;

public class SeekController extends Controller {
	GameObject _tar;
	
	public SeekController(GameObject target)
	{
		this._tar = target;
	}
	
    public void update(Car subject, Game game, double delta_t, double[] controlVariables) {
        controlVariables[VARIABLE_STEERING] = 0;
        controlVariables[VARIABLE_THROTTLE] = 0;
        controlVariables[VARIABLE_BRAKE] = 0;
    }
}
