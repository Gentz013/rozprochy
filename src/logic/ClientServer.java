package logic;

import com.sun.deploy.util.SessionState;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Jan on 4/22/2015.
 */
public class ClientServer {

    private String nickname;
    private IServerManager serverManager;

    public ClientServer(String nickname){
        this.nickname = nickname;

        try {
            serverManager = (IServerManager) Naming.lookup("rmi://localhost:1099/JohnSnow");

            serverManager.register(new Player(nickname));

            System.out.println("in the client server");
            serverManager.printAllPlayers();

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (PlayerRejectedException e) {
            //player was rejected

            e.printStackTrace();
        }

    }

}
