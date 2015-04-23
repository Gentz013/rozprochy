package logic;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Jan on 4/19/2015.
 */
public class BoardData implements Serializable{

    private int[][] board;
    private Player winner;

    public BoardData(){
        board = new int[3][3];

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                board[i][j] = 0;

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] < 0)
                    builder.append("X");
                else if (board[i][j] > 0)
                    builder.append("O");
                else builder.append(".");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private void debug_board(){
        System.out.println("board debug");
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public boolean doMoveIfProper(int x, int y, int symbol, IServerManager manager, Player player)
            throws RemoteException, PlayerRejectedException {
        try {

            if (x < 0 || x > 3 || y < 0 || y > 3) throw new Exception("Incorrect dimension");

            //debug_board();
            System.out.println(x + " " + y + " s= " + symbol);
            if (board[x][y] == 0) {
                //cell is empty
                board[x][y] = symbol;
                manager.doMove(player, this);
                return true;
            }


        } catch (Exception e) {
            System.err.println("wrong dimensions");
        }

        return false;

    }

    /**
     * returns:
     * 0 if game is still running
     * -1 if there is no winner
     * 1..8 how the game was won for GUI
     * @return
     */
    public int ifGameWon(Player playerOne, Player playerTwo){
        //if (Math.abs(turn) > 9) return -1;

        for(int i = 0; i < 3; i++)
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0){
                setWinner(playerOne, playerTwo, board[0][i]);
                return 1+i;
            }

        for(int i = 0; i < 3; i++)
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0){
                setWinner(playerOne, playerTwo, board[i][0]);
                //System.out.println(board[i][0] + "|" + board[i][1]+ "|" + board[i][2]);
                return 4+i;
            }

        if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0){
            setWinner(playerOne, playerTwo, board[0][0]);
            return 7;
        }

        if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0){
            setWinner(playerOne, playerTwo, board[0][2]);
            return 8;
        }

        boolean flag = false;
        for(int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if(board[i][j] == 0) flag = true;

        if(!flag) return 10;


        return 0;
    }

    public int ifGameWon(){

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


    private void setWinner(Player playerOne, Player playerTwo,
                           int winnnigCell) {
        winner = (winnnigCell < 0) ? playerOne : playerTwo;
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

    public Player getWinner(){
        return winner;
    }

}
