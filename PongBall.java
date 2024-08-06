/*
 * Stephie Liu
 * 2022-05-29
 * A GUI program to simulate Pong. There is a timer for tracking how
 * long it takes for the user to finish the game, and a ranking system based
 * on the time taken. Also includes paddle physics (if your paddle is in motion during contact, the ball speed increases too).
 */
package pongguiassignment;

/**
 *
 * @author steph
 */
//import statements
import java.awt.*;
import java.awt.event.*;
public class PongBall extends Rectangle{
    public int yVelocity;//falling speed
    public int xVelocity; //horizontal movement
    public static int BALL_DIAMETER = 20; //size of ball
    
    //constructor creates ball at given location with given dimensions
    public PongBall(int x) {
        super(x, -1*BALL_DIAMETER, BALL_DIAMETER, BALL_DIAMETER);
        yVelocity = 2;
        
        //randomizing the ball direction at the start of the game
        double rand = Math.random();
        if(rand>0.5){//50% chance that this value will be greater than 0.5
            xVelocity = 2;
        }
        else{//50% chance that this value will be less than 0.5
            xVelocity = -2;
        }
    }
    public void move() {
        y = y + yVelocity;
        x = x + xVelocity;
    }
    
    //called whenever the movement of the ball changes in the y-direction (up/down)
    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }

    //called whenever the movement of the ball changes in the x-direction (left/right)
    public void setXDirection(int xDirection) {
        xVelocity = xDirection;
    }
    
    //ball design
    public void draw(Graphics g){
        g.setColor(Color.PINK);
        g.fillOval(x, y, BALL_DIAMETER, BALL_DIAMETER);
    }
}
