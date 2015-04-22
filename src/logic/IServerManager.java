package logic;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Jan on 4/19/2015.
 */
public interface IServerManager extends Remote {

    int SINGLE_PLAYER = 25342;
    int MULTI_PLAYER = 45364;

    void register(Player player) throws RemoteException, PlayerRejectedException;
    void chooseGame(Player player, int gametype) throws RemoteException, PlayerRejectedException;
    void printAllPlayers() throws RemoteException;
}
