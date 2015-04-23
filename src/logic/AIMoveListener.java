package logic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;

/**
 * Created by Jan on 4/23/2015.
 */
public class AIMoveListener extends UnicastRemoteObject implements IMoveListener{

    private int playerCellType;
    private IServerManager manager;
    private Player player;

    protected AIMoveListener(IServerManager manager, Player player) throws RemoteException {
        super();

        this.manager = manager;
        this.player = player;

    }

    @Override
    public void setPlayerCell(int cellType) throws RemoteException {
        playerCellType = cellType;
    }

    @Override
    public void onPlayerMove(Player player, BoardData gameBoard) throws RemoteException {
        //generate random move

        System.out.println("In AI function");

        SecureRandom random = new SecureRandom();

        int x_coord, y_coord = 0;
        int numOfTries = 0;

        try {
            do {

                int value = Math.abs(random.nextInt() % 9);

                x_coord = value / 3;
                y_coord = value % 3;
                numOfTries++;

            } while (!gameBoard.doMoveIfProper(x_coord, y_coord, playerCellType, manager, this.player));
        } catch (PlayerRejectedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVictory(BoardData gameBoard) throws RemoteException {
        finishGame();
    }

    @Override
    public void onTie(BoardData gameBoard) throws RemoteException {
        finishGame();

    }

    @Override
    public void onLoss(BoardData gameBoard) throws RemoteException {
        finishGame();
    }

    @Override
    public void onError(String message) throws RemoteException {
        finishGame();
    }

    public void finishGame() throws RemoteException {
        UnicastRemoteObject.unexportObject(this, false);
        //client.setServerService(null);
    }
}
