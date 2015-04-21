package logic;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jan on 4/19/2015.
 */
public class ServerManager implements IServerManager {

    List<Player> players = new LinkedList<Player>();
    List<Player> awaitingPlayers = new LinkedList<Player>();
    Map<Player, Player> gameSessions = new HashMap<>();



    @Override
    public void register(Player player) throws RemoteException, PlayerRejectedException {

        if(players.contains(player)){
            throw(new PlayerRejectedException("Player with the same nickname already exists!"));
        } else {
            players.add(player);
        }

    }

    @Override
    public void chooseGame(Player player, int gametype) throws RemoteException, PlayerRejectedException {
        if(gametype > 0){
            //vs AI
        } else {
            //vs Player
            awaitingPlayers.add(player);

            if(awaitingPlayers.size()%2 == 0 && awaitingPlayers.size() >= 2) {
                Player player1 = awaitingPlayers.remove(0);
                Player player2 = awaitingPlayers.remove(0);
                gameSessions.put(player1, player2);
            }

        }
    }
}
