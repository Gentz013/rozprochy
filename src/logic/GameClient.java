package logic;

import GUI.HelloScreen;
import GUI.NameInputScreen;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

/**
 * Created by Jan on 4/22/2015.
 */
public class GameClient {

    private IServerManager serverManager;
    private Player player;
    private IMoveListener moveListener;

    public static final String PROPERTY_FILE = "serverConf.properties";

    public static String IP_address;
    public static String RMI_port_registry;
    public static String server_name;

    public IServerManager getServerManager(){
        return serverManager;
    }

    public void setServerService(IServerManager serverManager){
        this.serverManager = serverManager;
    }

    public Player getPlayer(){
        return this.player;
    }

    public static String initProperties(){
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(PROPERTY_FILE));

            IP_address = properties.getProperty("IP_address");
            RMI_port_registry = properties.getProperty("RMI_port_registry");
            server_name = properties.getProperty("server_name");


        } catch (IOException e){
            e.printStackTrace();
        }

        StringBuilder builder = new StringBuilder();
        builder.append("rmi://");
        builder.append(IP_address);
        builder.append(":");
        builder.append(RMI_port_registry);
        builder.append("/");
        builder.append(server_name);
        return builder.toString();
    }


    public GameClient(String nickname) throws IOException {
        player = new Player(nickname);
        moveListener = new PlayerMoveListener(this);
        player.setMoveListener(moveListener);

        String rmiAddress = initProperties();
        try {
            try {

                serverManager = (IServerManager) Naming.lookup(rmiAddress);
                serverManager.register(new Player(nickname));

                //now choose gamemode
                new HelloScreen(player, serverManager);

            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (PlayerRejectedException e) {
                e.printStackTrace();
            }

        } catch (Throwable e){
            if(this != null){
                try {
                    UnicastRemoteObject.unexportObject(this.moveListener, true);
                    this.serverManager = null;
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){

        new NameInputScreen("Please enter your nickname");
    }

}
