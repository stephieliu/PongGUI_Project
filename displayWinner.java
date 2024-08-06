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
import javax.swing.*;

//popup window that shows the winner at the end and ends the program when closed
public class displayWinner extends JFrame{
    public displayWinner(int player, int time, int score){
        this.setTitle("WINNER!!");//title
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(500, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);//set window in middle of screen
        
        if(player == 3){//if there is a tie
            JLabel title = new JLabel("IT'S A TIE!");
            title.setFont(new Font("Helvetica", Font.BOLD, 50));
            JLabel timeTaken = new JLabel("Time taken: "+time+" seconds");
            timeTaken.setFont(new Font("Helvetica", Font.PLAIN, 30));
            JLabel rank = new JLabel("Winner's Rank: PEACEFUL");
            rank.setFont(new Font("Helvetica", Font.BOLD, 30));
            JLabel description = new JLabel("It's a mutual win!");
            rank.setFont(new Font("Helvetica", Font.PLAIN, 20));
            
            Container c = getContentPane();
            c.setLayout(new FlowLayout());

            c.add(title);
            c.add(timeTaken);
            c.add(rank);
            c.add(description);
            this.setVisible(true);
        }
        else{//if it is not a tie, display the winner
            JLabel title = new JLabel("Winner is Player "+player+"!");
            title.setFont(new Font("Helvetica", Font.BOLD, 50));
            JLabel timeTaken = new JLabel("Time taken: "+time+" seconds");
            timeTaken.setFont(new Font("Helvetica", Font.PLAIN, 30));
            JLabel rank = new JLabel();
            JLabel description = new JLabel();
            
            //ranking system based on time taken to beat the other player
            if(time>230){
                rank = new JLabel("Winner's Rank: TURTLE");
                description = new JLabel("Slow and steady wins the race!");
            }
            if(time<=230 && time >120){
                rank = new JLabel("Winner's Rank: NORMAL");
                description = new JLabel("Sometimes being average is perfect!");
            }
            if(time<=120){
                rank = new JLabel("Winner's Rank: I AM SPEED");
                description = new JLabel("You must be a master at this game!");
            }
            
            rank.setFont(new Font("Helvetica", Font.BOLD, 30));
            description.setFont(new Font("Helvetica", Font.PLAIN, 20));

            Container c = getContentPane();
            c.setLayout(new FlowLayout());

            c.add(title);
            c.add(timeTaken);
            c.add(rank);
            c.add(description);
            this.setVisible(true);
        }
    }
}
