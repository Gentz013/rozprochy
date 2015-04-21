package logic;

import java.rmi.RemoteException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Jan on 4/19/2015.
 */
public class BoardData{

    private int[][] board;
    private int turn = 1;
    private int playerTurn = 0;

    public BoardData(){
        board = new int[3][3];

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                board[i][j] = 0;

        Random rand = new Random();

        playerTurn = rand.nextDouble()-.5 < 0 ? -1 : 1;

    }

    public int setBoardValue(int x, int y) {
        try {

            if (x < 0 || x > 3 || y < 0 || y > 3) throw new Exception("Incorrect dimension");

            if(board[x][y] != 0) { return -1; }


            board[x][y] = turn;
            //turn = turn < 0 ? turn-1 : turn+1;
            turn*=(-1);

        } catch (Exception e){
            System.err.println("wrong arguments");
        }

        return 0;
    }

    public int getBoardValue(int x, int y)  {
        try {

            if (x < 0 || x > 3 || y < 0 || y > 3) throw new Exception("Incorrect dimension");

            return board[x][y];

        } catch (Exception e){
            System.err.println("wrong dimensions");
            return 0;
        }
    }

    /**
     * returns:
     * 0 if game is still running
     * -1 if there is no winner
     * 1..8 how the game was won for GUI
     * @return
     */
    public int ifGameWon(){
        //if (Math.abs(turn) > 9) return -1;

        for(int i = 0; i < 3; i++)
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0){
                return 1+i;
            }

        for(int i = 0; i < 3; i++)
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0){
                return 4+i;
            }

        if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0){
            return 7;
        }

        if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0){
            return 8;
        }

        return 0;
    }

    public void makeAIMove(){
        SecureRandom random = new SecureRandom();

        int x_coord, y_coord;
        int numOfTries = 0;

        do {

            int value = Math.abs(random.nextInt() % 9);

            x_coord = value / 3;
            y_coord = value % 3;
            numOfTries++;

        } while (setBoardValue(x_coord, y_coord) < 0);

        System.out.println("AI moves chooses position " + "(" + x_coord + "," + y_coord + ")" + " after " + numOfTries + " tries");

    }

    public boolean isPlayerTurn(){
        return (turn < 0 && playerTurn < 0) || (turn > 0 && playerTurn > 0) ? true : false;
    }

}
