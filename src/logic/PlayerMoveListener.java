package logic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Jan on 4/23/2015.
 */
public class PlayerMoveListener extends UnicastRemoteObject implements IMoveListener {

    private GameClient client;

    //cell type -1 for cross, 1 for nough
    private int playerCellType;

    protected PlayerMoveListener(GameClient client) throws RemoteException {
        super();
        this.client = client;

    }


    @Override
    public void setPlayerCell(int cellType) throws RemoteException {
        playerCellType = cellType;
    }

    @Override
    public void onPlayerMove(Player player, BoardData gameBoard) throws RemoteException {
        System.out.println("In Player function");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Player: " + player.getNickname()
                + " moved:\n" + gameBoard.toString());

        System.out.println("Choose your move from 0-8");

        String move = null;
        try {
            move = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            Integer moveValue = Integer.parseInt(move);
            int x_coords = moveValue/3;
            int y_coords = moveValue%3;

            System.out.println("("+ x_coords + "," + y_coords + ")");
            while(!gameBoard.doMoveIfProper(x_coords, y_coords, playerCellType, client.getServerManager(), client.getPlayer())) {
                try {
                    move = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (RemoteException e) {
            System.err.println("Problem with connection to the server");
            finishGame("");
        } catch (PlayerRejectedException e) {
            System.err.println("You are not allowed! Game over");
            finishGame("");
        }
    }

    @Override
    public void onVictory(BoardData gameBoard) throws RemoteException {
        finishGame("You won!");
    }

    @Override
    public void onTie(BoardData gameBoard) throws RemoteException {
        finishGame("Tie!");

    }

    @Override
    public void onLoss(BoardData gameBoard) throws RemoteException {
        finishGame("You lost!");
    }

    @Override
    public void onError(String message) throws RemoteException {
        finishGame(message);
    }

    public void finishGame(String message) throws RemoteException {
        System.out.println(message);
        UnicastRemoteObject.unexportObject(this, true);
        //client.setServerService(null);
    }
}
