package logic;

import logic.Player;
import logic.PlayerRejectedException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Jan on 4/19/2015.
 */
public interface IServerManager extends Remote {

    public void register(Player player) throws RemoteException, PlayerRejectedException;

    public void chooseGame(Player player, int gameType) throws RemoteException, PlayerRejectedException;

    public void doMove(Player player, BoardData gameBoard) throws RemoteException, PlayerRejectedException;
}
