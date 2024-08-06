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
public class PongPaddle extends Rectangle{
    public int yVelocity;
    public final int SPEED = 4; //movement speed of paddle
    public static final int PADDLE_WIDTH = 15; //width of paddle
    public static final int PADDLE_HEIGHT = 80;//height of paddle
    public int player;//type of player (left or right)

    //constructor creates paddle at given location with given dimensions
    public PongPaddle(int x, int y, int player) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        this.player = player;
    }
    
    //called from GamePanel when any keyboard input is detected
    //updates the direction of the paddle based on user input
    //if the keyboard input isn't any of the options (w, s, arrow up, arrow down), then nothing happens
    public void keyPressed(KeyEvent e) {
        if(player == 1){//settings for player1
            if (e.getKeyChar() == 'w') {
                setYDirection(SPEED * -1);
                move();
            }

            if (e.getKeyChar() == 's') {
                setYDirection(SPEED);
                move();
            }
        }
        else if(player == 2){//settings for player2
            if(e.getKeyCode() == KeyEvent.VK_UP){
                setYDirection(SPEED * -1);
                move();
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                setYDirection(SPEED);
                move();
            }
        }
    }

    //called from GamePanel when any key is released (no longer being pressed down)
    //Makes the paddle stop moving in that direction
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'w') {
            setYDirection(0);
            move();
        }

        if (e.getKeyChar() == 's') {
            setYDirection(0);
            move();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            setYDirection(0);
            move();
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            setYDirection(0);
            move();
        }
    }

    //called whenever the movement of the paddle changes in the y-direction (up/down)
    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }

    //called frequently from both PlayerBall class and GamePanel class
    //updates the current location of the paddle
    public void move() {
        y = y + yVelocity;//only moves vertically
    }

    //called frequently from the GamePanel class
    //draws the current location of the paddles to the screen
    public void draw(Graphics g) {
        if(player == 1){
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        }
        if(player == 2){
            g.setColor(Color.CYAN);
            g.fillRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        }
    }
}
