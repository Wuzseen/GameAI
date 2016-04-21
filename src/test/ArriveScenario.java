package test;

import controllers.ArriveController;

import java.util.*;
import engine.Car;
import engine.Game;
import engine.GameObject;
import engine.GameWindow;
import engine.Marker;
import engine.Obstacle;

import java.awt.Color;

/**
 *
 * @author santi
 */
public class ArriveScenario {
    /*
        Goal of this exercise:
        - Write a controller for "car1" that uses the "Arrive" steerig behavior to arrive to 
          a given marker.
        - To make sure it works, test your controller by placing the marker in different positions
          in the map.
    */
    
    public static void main(String args[]) throws Exception {
    	Random r = new Random();
        Game game = new Game(800,600, 25);
        // set up the outside walls:
        game.add(new Obstacle(0,0,800,25,Color.GRAY));
        game.add(new Obstacle(0,575,800,25,Color.GRAY));
        game.add(new Obstacle(0,0,25,600,Color.GRAY));
        game.add(new Obstacle(775,0,25,600,Color.GRAY));
        // set up the cars and markers:
        double x = 15 + r.nextDouble() * 600;
        double y = 15 + r.nextDouble() * 600;
        GameObject marker = new Marker(x,y,10, Color.green);
        x = 15 + r.nextDouble() * 600;
        y = 15 + r.nextDouble() * 600;
        GameObject car1 = new Car("graphics/redcar.png",x,y,-Math.PI/2, new ArriveController(marker));
        game.add(marker);
        game.add(car1);
        GameWindow.newWindow(game);
    }
}
