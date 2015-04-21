package logic;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Jan on 4/19/2015.
 */
public interface IBoardData extends Remote {

    public void setBoardValue(int x, int y) throws RemoteException;
    public int getBoardValue(int x, int y) throws RemoteException;

}
