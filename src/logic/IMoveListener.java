package logic;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Jan on 4/23/2015.
 */
public interface IMoveListener extends Remote {

    public void setPlayerCell(int cellType) throws RemoteException;

    public void onPlayerMove(Player player, BoardData gameBoard) throws RemoteException;

    public void onVictory(BoardData gameBoard) throws RemoteException;

    public void onTie(BoardData gameBoard) throws RemoteException;

    public void onLoss(BoardData gameBoard) throws RemoteException;

    public void onError(String message) throws RemoteException;

}
