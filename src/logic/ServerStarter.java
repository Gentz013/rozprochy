package logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

/**
 * Created by Jan on 4/19/2015.
 */
public class ServerStarter {

    public static final String PROPERTY_FILE = "serverConf.properties";

    public static String IP_address;
    public static String RMI_port_registry;
    public static String server_name;

    public static IServerManager serverManagerImpl;


    public ServerStarter(){
        initProperties();
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

    public static void main(String[] args){

        String rmiAddress = initProperties();

        try {
            System.out.println("Starting rmiregistry on port " + RMI_port_registry);
            LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            System.err.println("rmiregistry Exception");
            e.printStackTrace();
        }


        System.out.println(rmiAddress);

        try{

            //export object
            serverManagerImpl = new ServerManager();
            IServerManager serverManager = (IServerManager) UnicastRemoteObject.exportObject(serverManagerImpl, 0);
            Naming.rebind(rmiAddress, serverManager);


        } catch(Exception e){
            System.err.println("error occured during server initialization");
            e.printStackTrace();
            if(serverManagerImpl != null){
                try {
                    UnicastRemoteObject.unexportObject(serverManagerImpl, true);
                    serverManagerImpl = null;
                } catch (NoSuchObjectException e1) {
                    //ignore
                }
            }
        }
    }
}

