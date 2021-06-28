import java.util.Scanner;
import java.util.Random;

/* 
 * A program that represents the Silver Dollar Game. The game displays the size
 * and number of coins the players want to have. Then, each player takes a turn
 * selecting a coin and moving it a certain amount of spaces to the left. The
 * coins cannot overlap and cannot go out of bounds. The objective is to be
 * the last player to make a legal move.
 *
 */

public class Coinstrip {

  public static int[] gameBoard; // the array of coins in the game board
  public static int gameSize; // total amount of spaces on the game board
  public static Random location = new Random(); // random generator for coin locations
  public static String[] display; // the string that the users see
  public static boolean player1 = true; // determines if player 1 is playing
  public static boolean playing = true; // determines if you can continue playing

  // Gets the size of the game board and the number of coins
  // It will also set up the rules for the coin strip game
  public static void main (String args[]){
    Scanner in = new Scanner(System.in);
    System.out.println("What is the size of your gameboard: ");
    int size = in.nextInt();
    System.out.println("How many coins: ");
    int coins = in.nextInt();

    Coinstrip gameStrip = new Coinstrip(coins, size);

    //Displays the game board at the start of the game
    System.out.println(gameStrip.toString());

    // A while loop that will continue as the coins can be moved
    while(playing){

      //Will display the player that is playing
      if(player1){
        System.out.println("\n" + "Player 1: ");
      } else {
        System.out.println("\n" + "Player 2: ");
      }

      //Instructions for the player of choosing coin to move and how many spaces
      //to move
      System.out.println("\n" + "Choose coin and move coin : cn ns");

      //The user input that represents the coin and the move
      int coin = in.nextInt();
      int move = Math.abs(in.nextInt());

      //If statement that checks the coin
      if((coin >= 0 && coin < gameBoard.length)){

        //Stores the last position of the coin and the new position after making
        //a move
        int lastPosition = gameBoard[coin];
        int newPosition = gameBoard[coin] - move;

        //Will check that the new position of the coin is not out of bounds or
        // does not pass another coin
        if(newPosition >= 0){
          for(int i = 0; i < gameBoard.length; i++){
            if(i != coin){
              if(gameBoard[i] >= newPosition && gameBoard[i] < lastPosition){
                System.out.println("Move Is Not Valid");
                lastPosition = -1;
                break;
              }
            }
          }
          //If the inputs for choosing coin and moving the coin is correct then
          //move the coin, check for the win, and change the player playing
          if(lastPosition != -1){
            gameBoard[coin] = gameBoard[coin] - move;
            player1 = !player1;
          }
        }
        else System.out.println("Out of Bounds Move");
      }
      else System.out.println("Move a Valid Coin");

      //Displays a string of the results after making the moves
      System.out.println(gameStrip.toString());

      gameStrip.verifyWin(coins);
    }
  }

  // Pre: Takes an int for the coins and the size of the game
  // Post: will randomize the location of each coin on the Coinstrip and check
  // that no coins overlap
  public Coinstrip(int coins, int size){

    int maxDistance = 5;
    int initial = 0;

    // initialize the coin array and the size of the game board
    gameBoard = new int[coins];
    gameSize = size;
    display= new String[gameSize];

    int section = gameSize/coins;

    // randomly fills the total number of coins to different locations on the game board
    for(int i = 0; i < coins; i++){

      //Gives a position to each coin
      gameBoard[i] = i * section + location.nextInt(section);



      //Checks that the new position has not been assigned to a coin already
      noOverlap(i);
    }

  }

  //Pre: takes an int representing the number of coins in the game
  //Post: It will do nothing unless a game has been won. If someone won,
  //then display the winner and end the game
  public void verifyWin(int numCoins){
    boolean win = true;

    //checks that the game has no winner depending on the display
    for(int i = 0; i < numCoins; i++){
      if(!display[i].equals("|X|")){
        win = false;
      }
    }

    // if someone has won then you cannot play and display the winner
    if(win){
      playing = false;
      System.out.println("\n" + "Game Over!");
      if(!player1){
        System.out.println("Player 1 Wins!");
      } else {
        System.out.println("Player 2 Wins!");
      }
    }
  }

  //Pre: Takes an integer
  //Post: Will check that the two coins have the same or different positions
  public void noOverlap(int i){
    for(int j = 0; j < i; j++){
      if(gameBoard[i] == gameBoard[j]){
        gameBoard[i] = location.nextInt(gameSize);

        //checks that the new position does not overlap anything
        noOverlap(i);
      }
    }
  }

  //Pre: None
  //Post: will return a string based on the game board and the moves made (if any)
  public String toString(){
    String outcome = "";

    //initializes all spaces on the board array with a "|_|"
    for(int i = 0; i < gameSize; i++){
      display[i] = "|_|";
    }

    // for loop that creates an "|X|" for the spaces that has a coin
    for(int i = 0; i < gameBoard.length; i++){
      display[gameBoard[i]] = "|X|" ;
    }

    // Will update the display based on the filled spaces and empty spaces
    for(int i = 0; i < display.length; i++){
      outcome = outcome + display[i];
    }

    return outcome;
  }
}
