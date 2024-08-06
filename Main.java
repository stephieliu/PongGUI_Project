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
import java.util.*;//import statement
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);//declare scanner
        int flag = 0;
        System.out.println("WELCOME TO PONG!\n================");
        //try-catch for the choice:
        String choice1 = "";
        while (true) {
            System.out.println("\nPRESS 1 TO START OR 2 TO QUIT:");
            choice1 = sc.nextLine();
            try {
                flag = Integer.parseInt(choice1);//check that it is an integer
                if (flag == 1 || flag == 2)//if the value is 1 or 2, it is a proper value
                {
                    break;
                } else {
                    System.out.println("Please enter a valid option.");
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid option.");
            }
        }
        if(flag == 1){
            //display instructions
            System.out.println("\nPLAYER 1 is on the LEFT. Use W and S to control the paddle.\nPLAYER 2 is on the RIGHT. Use UP and DOWN arrow keys to control the paddle.\nKeep the ball from touching your side of the screen!\nThe game ends when you reach 10 points, or when you reach 300 seconds.\n\nGOOD LUCK!");
            delay(7000);//delay 7s so the player can read
            new GameFrame();//start the game
        }
        //exit the game if the user chooses option 2
        else System.out.println("\nGoodbye! Thank you for playing Pong!");    
        sc.close();//close the scanner
    }
    
    //DELAYS OUTPUT FOR A SET AMOUNT OF SECONDS
    //takes long l to determine how long the delay will be (in milliseconds)
    public static void delay(long l){
        try{
            Thread.sleep(l);
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }
}
