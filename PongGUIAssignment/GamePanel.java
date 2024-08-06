/*
 * Stephie Liu
 * 2022-05-29
 * A GUI program to simulate Pong. There is a timer for tracking how
 * long it takes for the user to finish the game, and a ranking system based
 * on the time taken. Also includes paddle physics (if your paddle is in motion during contact, the ball speed increases too).
 */
package pongguiassignment;

//import statements
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GamePanel extends JPanel implements Runnable, KeyListener{
    //dimensions of window
    public static final int GAME_WIDTH = 1500;
    public static final int GAME_HEIGHT = 1000;
    
    //declare variables
    public Thread gameThread;
    public Image image;
    public Graphics graphics;
    public PongBall ball;
    public PongPaddle player1;
    public PongPaddle player2;
    public int scoreP1;
    public int scoreP2;
    public int velocity = 2;
    public Stopwatch stopwatch = new Stopwatch();
    public boolean stop = false;
    
    public GamePanel() {
        player1 = new PongPaddle(0, GAME_HEIGHT / 2, 1); //create a player controlled paddle, set start location to left of screen
        this.setFocusable(true); //make everything in this class appear on the screen
        this.addKeyListener(this); //start listening for keyboard input
        
        player2 = new PongPaddle(GAME_WIDTH-15, GAME_HEIGHT / 2, 2); //create a second player controlled paddle, set start location to right of screen
        this.setFocusable(true); //make everything in this class appear on the screen
        this.addKeyListener(this); //start listening for keyboard input
        
        //display the ball
        ball = new PongBall(GAME_WIDTH/2);//create a ball at the middle top of screen
        this.setFocusable(true);

        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        //make this class run at the same time as other classes
        gameThread = new Thread(this);
        gameThread.start();
        stopwatch.start();//start the timer
    }

    //paint is a method in java.awt library that is being overridden
    public void paint(Graphics g) {
        //double buffering
        image = createImage(GAME_WIDTH, GAME_HEIGHT); //draw off screen
        graphics = image.getGraphics();
        draw(graphics);//update the positions of everything on the screen 
        g.drawImage(image, 0, 0, this); //move the image on the screen
    }

    //call the draw methods in each class to update positions as things move
    public void draw(Graphics g) {
        //middle line
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(747, 0, 6, GAME_HEIGHT);
        //other visuals
        ball.draw(g);
        player1.draw(g);
        player2.draw(g);
        scoreDraw(g);
    }
    
    //draws the score on the window
    public void scoreDraw(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Times New Roman", Font.BOLD, 40));
        g.drawString(""+scoreP1, 375, 60);//centering the positions of both scores for each player
        g.drawString(""+scoreP2, 1125, 60);
    }

    //call the move methods in other classes to update positions
    //this method is constantly called from run()
    public void move() {
        ball.move();
        player1.move();
        player2.move();
    }

    //handles all collision detection and responds accordingly
    public void checkCollision() {
        //force players to remain on screen
        if (player1.y <= 0) {//top of screen
            player1.y = 0;
        }
        if (player1.y >= GAME_HEIGHT - PongPaddle.PADDLE_HEIGHT) {//bottom of screen
            player1.y = GAME_HEIGHT - PongPaddle.PADDLE_HEIGHT;
        }
        if (player2.y <= 0) {//top of screen
            player2.y = 0;
        }
        if (player2.y >= GAME_HEIGHT - PongPaddle.PADDLE_HEIGHT) {//bottom of screen
            player2.y = GAME_HEIGHT - PongPaddle.PADDLE_HEIGHT;
        }
        
        //forces ball to remain on screen
        if (ball.x <= 0) {//hits left side of screen
            ball.setXDirection(velocity);
            scoreP2++;//update points
        }
        if (ball.x + PongBall.BALL_DIAMETER >= GAME_WIDTH) {//hits right side of screen
            ball.setXDirection(-1*velocity);
            scoreP1++;//update points
        }
        if(ball.y >=GAME_HEIGHT - PongBall.BALL_DIAMETER){//hits bottom of screen
            ball.setYDirection(-1*velocity);
        }
        if(ball.y<=0){//hits top of screen
            ball.setYDirection(velocity);
        }
        
        //increases speed if your velocity is high when you hit the ball
        if (ball.intersects(player1)) {
            ball.setXDirection(velocity);
            if(player1.yVelocity>0){//if your paddle is moving when you hit the velocity increases
                ball.xVelocity++;
                ball.yVelocity++;
                velocity++;
            }
        }
        if(ball.intersects(player2)){//same steps for player 2
            ball.setXDirection(-1*velocity);
            if(player2.yVelocity>0){
                ball.xVelocity--;
                ball.yVelocity--;
                velocity++;
            }
        }
        
        displayWinner winner;//create a new displayWinner
        double seconds = stopwatch.getElapsedTimeSeconds();//take in the amount of time passed so far
        //check if the game is already over (score is 10 or time is >300s)
        if(scoreP1 ==10){//player1 wins
            winner = new displayWinner(1, (int)seconds, scoreP1);//displays the winner
            stop = true;
        }
        if(scoreP2 == 10){//player 2 wins
            winner = new displayWinner(2, (int)seconds, scoreP2);
            stop = true;
        }
        if((int)seconds>300){
            stop = true;
            if(scoreP1>scoreP2){//player 1 wins
                winner = new displayWinner(1, (int)seconds, scoreP1);
            }
            else if(scoreP1<scoreP2){//player 2 wins
                winner = new displayWinner(2, (int)seconds, scoreP2);
            }
            else{//tie
                winner = new displayWinner(3, (int)seconds, scoreP1);
            }
        }
    }

    //run() method is what makes the game continue running without end
    public void run() {
        //force the computer to get stuck in a loop for short intervals between calling other methods to update the screen
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long now;

        while (!stop) { //this is the infinite game loop until stop is true
            now = System.nanoTime();
            delta = delta + (now - lastTime) / ns;
            lastTime = now;

            //only move objects around and update screen if enough time has passed
            if (delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    //if a key is pressed, send it over to the PongPaddle class for processing
    public void keyPressed(KeyEvent e) {
        player1.keyPressed(e);
        player2.keyPressed(e);
    }

    //if a key is released, send it over to the PongPaddle class for processing
    public void keyReleased(KeyEvent e) {
        player1.keyReleased(e);
        player2.keyReleased(e);
    }

    //left empty because it is not needed, but used for overriding
    public void keyTyped(KeyEvent e) {

    }
}

//CLASS STOPWATCH
//class used to track the time it takes for the user to finish the game
class Stopwatch{
    private long startTime;
    public void start(){//starts the stopwatch
        startTime = System.currentTimeMillis();
    }
    public float getElapsedTimeSeconds(){//returns how much time has passed
        return (System.currentTimeMillis()-startTime)/1000f;
    }
}
